package com.example.flickrdemo.grid.adapters

import android.graphics.drawable.Drawable
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

class PhotoPresenter(private val onClick: (item: FlickrPhoto) -> Unit) : Presenter() {

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
        cardView.setupImageView(photo, defaultCardImage)
        cardView.binding.root.setOnClickListener {
            onClick(item)
        }
    }



    override fun onUnbindViewHolder(viewHolder: ViewHolder) {
        val cardView = viewHolder.view as? ImageCardView ?: return
        // Remove references to images so that the garbage collector can free up memory.
        cardView.mainImage = null
    }
}