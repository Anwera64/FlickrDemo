package com.example.flickrdemo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entities.DataResponse
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

    fun requestLatestPhotos() = viewModelScope.launch(Dispatchers.IO) {
        val dataResponse: DataResponse<PhotoCollection> = photoUseCase.getLatestPhotos()
        handlePhotoCollectionResponse(dataResponse)
    }

    /**
     * Requests the next page of the collection that is being shown. If the collection belongs to a
     * search query then the appropriate use case is used.
     */
    fun requestNextPage() = viewModelScope.launch(Dispatchers.IO) {
        val dataResponse: DataResponse<PhotoCollection> =
            if (_photoCollection.value?.isSearch == true) {
                searchSearchUseCase.requestNexPage()
            } else {
                photoUseCase.requestNexPage()
            }
        handlePhotoCollectionResponse(dataResponse)
    }

    private fun handlePhotoCollectionResponse(dataResponse: DataResponse<PhotoCollection>) {
        if (!dataResponse.isSuccessFul) {
            // TODO Show error message
            return
        }
        _photoCollection.postValue(dataResponse.response!!) // TODO handle null state
    }

    fun searchPhotos(searchTerm: String) = viewModelScope.launch(Dispatchers.IO) {
        val dataResponse: DataResponse<PhotoCollection> =
            searchSearchUseCase.searchPhotos(searchTerm)
        handlePhotoCollectionResponse(dataResponse)
    }

    /**
     * Clears the search state. If the reset was successful, requests the featured photos to show.
     */
    fun clearSearch(): Boolean {
        val resetDone = searchSearchUseCase.clearSearch()
        if (resetDone) {
            requestLatestPhotos()
        }
        return resetDone
    }
}