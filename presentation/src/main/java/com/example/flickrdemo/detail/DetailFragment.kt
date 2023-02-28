package com.example.flickrdemo.detail

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.domain.entities.FlickrPhoto
import com.example.domain.entities.PhotoCollection
import com.example.domain.utils.DateUtil
import com.example.flickrdemo.MainActivity
import com.example.flickrdemo.MainViewModel
import com.example.flickrdemo.R
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
    private var currentPhotoIndex = 0

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
        viewmodel.photoCollection.observe(viewLifecycleOwner, this::observeImages)
    }

    private fun observeImages(photoCollection: PhotoCollection) {
        currentPhotoIndex = arguments?.getInt(INDEX_OF_IMAGE) ?: 0
        val photo = photoCollection.photos[currentPhotoIndex]
        (activity as? MainActivity)?.onKeyPressedAction = { action ->
            navigatePhotos(action, photoCollection.photos)
        }
        loadImage(photo)
    }

    private fun navigatePhotos(action: Int, photos: List<FlickrPhoto>) {
        val nextPhoto = when (action) {
            KeyEvent.KEYCODE_DPAD_RIGHT -> {
                if (currentPhotoIndex + 1 > photos.size - 1) return
                currentPhotoIndex++
                photos[currentPhotoIndex]
            }
            KeyEvent.KEYCODE_DPAD_LEFT -> {
                if (currentPhotoIndex - 1 < 0) return
                currentPhotoIndex--
                photos[currentPhotoIndex]
            }
            else -> return
        }
        loadImage(nextPhoto)
    }

    private fun loadImage(photo: FlickrPhoto) {
        binding?.run {
            titleText.text = photo.description
            val dateString = DateUtil.parseToString(photo.date)
            contentText.text = resources.getString(
                R.string.username_date_placeholder,
                photo.username,
                dateString
            )

            if (photo.photoUrl.isEmpty() && photo.photoUrl.isBlank()) return

            val mContext = context ?: return
            Glide.with(mContext)
                .load(photo.photoUrl)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(mainImage)
        }
    }
}