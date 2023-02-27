package com.example.flickrdemo.grid.adapters

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import androidx.leanback.widget.BaseCardView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.domain.entities.FlickrPhoto
import com.example.domain.utils.DateUtil
import com.example.flickrdemo.R
import com.example.flickrdemo.databinding.ViewPhotoBinding

class PhotoView(context: Context) : BaseCardView(context) {

    val binding: ViewPhotoBinding

    init {
        val inflater = LayoutInflater.from(getContext())
        binding = ViewPhotoBinding.inflate(inflater, this, true)
    }

    override fun setSelected(selected: Boolean) {
        updateCardForegroundColor(selected)
        super.setSelected(selected)
    }

    fun setupImageView(photo: FlickrPhoto, defaultCardImage: Drawable?) {
        binding.titleText.text = photo.description
        val dateString = DateUtil.parseToString(photo.date)
        binding.contentText.text = resources.getString(
            R.string.username_date_placeholder,
            photo.username,
            dateString
        )

        if (photo.photoUrl.isEmpty() && photo.photoUrl.isBlank()) return

        Glide.with(context)
            .load(photo.photoUrl)
            .apply(RequestOptions.errorOf(defaultCardImage))
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .into(binding.mainImage)
    }


    private fun updateCardForegroundColor(selected: Boolean) {
        val resId = if (selected) {
            R.drawable.fade_to_black_selected_bckg
        } else {
            R.drawable.fade_to_black_bckg
        }

        // Both background colors should be set because the view's
        // background is temporarily visible during animations.
        binding.backgroundFade.setBackgroundResource(resId)
    }
}