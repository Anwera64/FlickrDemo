package com.example.flickrdemo

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.leanback.app.VerticalGridSupportFragment
import androidx.leanback.widget.VerticalGridPresenter
import com.example.domain.entities.PhotoCollection
import com.example.flickrdemo.adapters.PhotoAdapter
import com.example.flickrdemo.adapters.PhotoPresenter
import com.example.flickrdemo.search.SearchFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VerticalGridFragment : VerticalGridSupportFragment() {

    companion object {
        private const val NUM_COLUMNS = 3
        private const val TAG = "Main fragment"
    }

    private val viewModel: MainViewModel by activityViewModels()
    private val photoAdapter: PhotoAdapter = PhotoAdapter(PhotoPresenter())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = getString(R.string.title)

        adapter = photoAdapter

        val gridPresenter = VerticalGridPresenter()
        gridPresenter.numberOfColumns = NUM_COLUMNS
        setGridPresenter(gridPresenter)

        setOnSearchClickedListener {
            openSearchFragment()
        }
    }

    private fun openSearchFragment() {
        val mainActivity: MainActivity = activity as? MainActivity ?: return
        mainActivity.openFragment(SearchFragment())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.photoCollection.observe(viewLifecycleOwner, this::onPhotoCollection)
    }

    private fun onPhotoCollection(photoCollection: PhotoCollection) {
        Log.d(TAG, "onPhotoCollection: loading ${photoCollection.photos.size} photos")
        photoAdapter.setItems(photoCollection.photos, null)
    }
}