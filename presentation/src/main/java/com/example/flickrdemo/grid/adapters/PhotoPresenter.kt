package com.example.flickrdemo.grid.adapters

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.leanback.widget.ImageCardView
import androidx.leanback.widget.Presenter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.domain.entities.FlickrPhoto
import com.example.domain.utils.DateUtil
import com.example.flickrdemo.R

class PhotoPresenter : Presenter() {

    private var defaultCardImage: Drawable? = null

    override fun onCreateViewHolder(parent: ViewGroup): ViewHolder {
        defaultCardImage = ResourcesCompat.getDrawable(parent.resources, R.drawable.ic_block, null)

        val cardView: PhotoView = PhotoView(parent.context).apply {
            isFocusable = true
            isFocusableInTouchMode = true
        }

        return ViewHolder(cardView)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, item: Any) {
        val photo: FlickrPhoto = item as? FlickrPhoto ?: throw Exception("Not a photo")
        val cardView = viewHolder.view as? PhotoView ?: return
        cardView.setupImageView(photo)
    }

    private fun PhotoView.setupImageView(photo: FlickrPhoto) {
        Log.d("Presenter", "binding image: ${photo.photoUrl}")
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

    override fun onUnbindViewHolder(viewHolder: ViewHolder) {
        val cardView = viewHolder.view as? ImageCardView ?: return

        // Remove references to images so that the garbage collector can free up memory.

        // Remove references to images so that the garbage collector can free up memory.
        cardView.badgeImage = null
        cardView.mainImage = null
    }
}