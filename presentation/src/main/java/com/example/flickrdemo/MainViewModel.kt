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

    fun requestLatestPhotos(page: Int = 1) = viewModelScope.launch(Dispatchers.IO) {
        val networkResponse: NetworkResponse<PhotoCollection> = photoUseCase.getLatestPhotos()
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
        val networkResponse: NetworkResponse<PhotoCollection> = searchSearchUseCase.searchPhotos(searchTerm)
        handlePhotoCollectionResponse(networkResponse)
    }
}