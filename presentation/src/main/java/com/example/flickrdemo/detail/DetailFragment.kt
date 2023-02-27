package com.example.flickrdemo.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.domain.entities.PhotoCollection
import com.example.flickrdemo.MainViewModel
import com.example.flickrdemo.databinding.FragmentDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {

    companion object {

        private const val INDEX_OF_IMAGE = "index of image"

        fun newInstance(index: Int): DetailFragment {
            return DetailFragment().apply {
                this.arguments = bundleOf(
                    INDEX_OF_IMAGE to index
                )
            }
        }
    }

    private val viewmodel: MainViewModel by activityViewModels()
    private var binding: FragmentDetailBinding? = null
    private val detailAdapter = DetailAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.rvDetail?.layoutManager = LinearLayoutManager(view.context).apply {
            orientation = LinearLayoutManager.HORIZONTAL
        }
        binding?.rvDetail?.adapter = detailAdapter

        viewmodel.photoCollection.observe(viewLifecycleOwner, this::loadImages)
    }

    private fun loadImages(photoCollection: PhotoCollection) {
        detailAdapter.itemList = photoCollection.photos
        val scrollIndex = arguments?.getInt(INDEX_OF_IMAGE) ?: 0
        binding?.rvDetail?.scrollToPosition(scrollIndex)
    }
}