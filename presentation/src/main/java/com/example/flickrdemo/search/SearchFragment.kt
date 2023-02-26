package com.example.flickrdemo.search

import android.os.Bundle
import androidx.fragment.app.activityViewModels
import androidx.leanback.app.SearchSupportFragment
import androidx.leanback.widget.ArrayObjectAdapter
import androidx.leanback.widget.ListRowPresenter
import androidx.leanback.widget.ObjectAdapter
import com.example.flickrdemo.MainActivity
import com.example.flickrdemo.MainViewModel
import com.example.flickrdemo.VerticalGridFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : SearchSupportFragment(), SearchSupportFragment.SearchResultProvider {

    private val viewModel: MainViewModel by activityViewModels()
    private val rowsAdapter = ArrayObjectAdapter(ListRowPresenter())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSearchResultProvider(this)
    }

    override fun getResultsAdapter(): ObjectAdapter {
        return rowsAdapter
    }

    override fun onQueryTextChange(newQuery: String?): Boolean {
        return true
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        query?.let(viewModel::searchPhotos)
        val mainActivity = activity as? MainActivity
        mainActivity?.openFragment(VerticalGridFragment())
        return true
    }
}