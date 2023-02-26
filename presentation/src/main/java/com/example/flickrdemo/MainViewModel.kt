package com.example.flickrdemo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entities.NetworkResponse
import com.example.domain.entities.PhotoCollection
import com.example.domain.usecases.PhotoUseCase
import com.example.domain.usecases.SearchUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val photoUseCase: PhotoUseCase,
    private val searchSearchUseCase: SearchUseCase
) : ViewModel() {

    private val _photoCollection = MutableLiveData<PhotoCollection>()
    val photoCollection: LiveData<PhotoCollection> = _photoCollection

    // TODO Connect to custom header
    /**
     * Observable for the search term. A null value indicates that no search is being done.
     */
    private val _searchTerm = MutableLiveData<String?>()
    val searchTerm: LiveData<String?> = _searchTerm

    fun requestLatestPhotos() = viewModelScope.launch(Dispatchers.IO) {
        val networkResponse: NetworkResponse<PhotoCollection> = photoUseCase.getLatestPhotos()
        handlePhotoCollectionResponse(networkResponse)
    }

    fun requestNextPage() = viewModelScope.launch(Dispatchers.IO) {
        val networkResponse: NetworkResponse<PhotoCollection> =
            if (_searchTerm.value.isNullOrEmpty() || _searchTerm.value.isNullOrBlank()) {
                photoUseCase.requestNexPage()
            } else {
                searchSearchUseCase.requestNexPage()
            }
        handlePhotoCollectionResponse(networkResponse)
    }

    private fun handlePhotoCollectionResponse(networkResponse: NetworkResponse<PhotoCollection>) {
        if (!networkResponse.isSuccessFul) {
            // TODO Show error message
            return
        }
        _photoCollection.postValue(networkResponse.response!!) // TODO handle null state
    }

    fun searchPhotos(searchTerm: String) = viewModelScope.launch(Dispatchers.IO) {
        val networkResponse: NetworkResponse<PhotoCollection> =
            searchSearchUseCase.searchPhotos(searchTerm)
        handlePhotoCollectionResponse(networkResponse)
        if (networkResponse.isSuccessFul) {
            _searchTerm.postValue(searchTerm)
        }
    }

    fun clearSearch(): Boolean {
        val resetDone = searchSearchUseCase.clearSearch()
        _searchTerm.postValue(null)
        if (resetDone) {
            requestLatestPhotos()
        }
        return resetDone
    }
}