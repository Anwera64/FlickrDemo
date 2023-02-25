package com.example.flickrdemo

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.fragment.app.FragmentActivity
import com.example.domain.entities.PhotoCollection
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : FragmentActivity() {

    companion object {
        private const val TAG = "Main Activity"
    }

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel.photoCollection.observe(this, this::onPhotoCollection)

        viewModel.requestLatestPhotos()
    }

    private fun onPhotoCollection(photoCollection: PhotoCollection) {
        // TODO show photo placeholder in UI
        Log.d(TAG, "page ${photoCollection.page} of ${photoCollection.pages}")
    }
}