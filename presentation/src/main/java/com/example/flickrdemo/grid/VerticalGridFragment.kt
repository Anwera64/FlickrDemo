package com.example.flickrdemo.grid

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.activityViewModels
import androidx.leanback.app.VerticalGridSupportFragment
import androidx.leanback.widget.ArrayObjectAdapter
import androidx.leanback.widget.VerticalGridPresenter
import com.example.domain.entities.PhotoCollection
import com.example.flickrdemo.MainActivity
import com.example.flickrdemo.MainViewModel
import com.example.flickrdemo.R
import com.example.flickrdemo.grid.adapters.PhotoPresenter
import com.example.flickrdemo.search.SearchFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VerticalGridFragment : VerticalGridSupportFragment() {

    companion object {
        private const val NUM_COLUMNS = 3
        private const val TAG = "Main fragment"
    }

    private val viewModel: MainViewModel by activityViewModels()
    private val photoAdapter: ArrayObjectAdapter = ArrayObjectAdapter(PhotoPresenter())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = getString(R.string.title)
        badgeDrawable =
            ResourcesCompat.getDrawable(resources, R.drawable.app_logo, null)
        adapter = photoAdapter

        val gridPresenter = VerticalGridPresenter()
        gridPresenter.numberOfColumns = NUM_COLUMNS
        setGridPresenter(gridPresenter)

        setOnSearchClickedListener {
            openSearchFragment()
        }

        setOnItemViewSelectedListener { _, item, _, _ ->
            onSelectedPositionChanged(item)
        }
    }

    private fun onSelectedPositionChanged(item: Any?) {
        val itemList = photoAdapter.unmodifiableList<Any>()
        val index = itemList.indexOfLast { any -> any == item }
        if (index == -1) return
        if (index == itemList.lastIndex) {
            viewModel.requestNextPage()
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
        if (photoCollection.page == 1) {
            photoAdapter.setItems(photoCollection.photos, null)
            title = selectTitleForResult(photoCollection)
            return
        }

        photoAdapter.addAll(photoAdapter.unmodifiableList<Any>().size, photoCollection.photos)
    }

    private fun selectTitleForResult(photoCollection: PhotoCollection) =
        when {
            photoCollection.isSearch && photoCollection.photos.isEmpty() -> {
                getString(R.string.title_search_empty, photoCollection.searchTerm)
            }
            photoCollection.isSearch && photoCollection.photos.isNotEmpty() -> {
                getString(R.string.title_search, photoCollection.searchTerm)
            }
            else -> getString(R.string.title)
        }
}