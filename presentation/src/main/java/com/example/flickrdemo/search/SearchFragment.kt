package com.example.flickrdemo.search

import android.os.Bundle
import androidx.fragment.app.activityViewModels
import androidx.leanback.app.SearchSupportFragment
import androidx.leanback.widget.ArrayObjectAdapter
import androidx.leanback.widget.ListRowPresenter
import androidx.leanback.widget.ObjectAdapter
import com.example.flickrdemo.MainActivity
import com.example.flickrdemo.MainViewModel
import com.example.flickrdemo.grid.VerticalGridFragment
import dagger.hilt.android.AndroidEntryPoint

/**
 * This fragment is only used to accept search queries. Once the query is submitted it's queried
 * through the View Model and the fragment is replaced with the VerticalGridFragment
 */
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