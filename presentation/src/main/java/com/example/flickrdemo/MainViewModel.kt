package com.example.flickrdemo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entities.NetworkResponse
import com.example.domain.entities.PhotoCollection
import com.example.domain.usecases.PhotoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val photoUseCase: PhotoUseCase) : ViewModel() {

    private val _photoCollection = MutableLiveData<PhotoCollection>()
    val photoCollection: LiveData<PhotoCollection> = _photoCollection

    fun requestLatestPhotos(page: Int = 1) = viewModelScope.launch {
        val networkResponse: NetworkResponse<PhotoCollection> = photoUseCase.getLatestPhotos()
        if (!networkResponse.isSuccessFul) {
            // TODO Show error message
            return@launch
        }
        _photoCollection.postValue(networkResponse.response!!) // TODO handle null state
    }
}