package com.example.flickrdemo.adapters

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.leanback.widget.ImageCardView
import androidx.leanback.widget.Presenter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.domain.entities.FlickrPhoto
import com.example.flickrdemo.R

class PhotoPresenter : Presenter() {

    private var defaultCardImage: Drawable? = null

    override fun onCreateViewHolder(parent: ViewGroup): ViewHolder {
        defaultCardImage = ResourcesCompat.getDrawable(parent.resources, R.drawable.ic_block, null)

        val cardView: ImageCardView = object : ImageCardView(parent.context) {
            override fun setSelected(selected: Boolean) {
                updateCardBackgroundColor(this, selected)
                super.setSelected(selected)
            }
        }.apply {
            isFocusable = true
            isFocusableInTouchMode = true
        }

        updateCardBackgroundColor(cardView, false)
        return ViewHolder(cardView)
    }

    private fun updateCardBackgroundColor(view: ImageCardView, selected: Boolean) {
        val resId = if (selected) R.drawable.fade_to_black_selected_bckg else R.drawable.fade_to_black_bckg

        // Both background colors should be set because the view's
        // background is temporarily visible during animations.
        view.setBackgroundResource(resId)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, item: Any) {
        val photo: FlickrPhoto = item as? FlickrPhoto ?: throw Exception("Not a photo")
        val cardView = viewHolder.view as? ImageCardView ?: return
        cardView.setupImageView(photo)

    }

    private fun ImageCardView.setupImageView(photo: FlickrPhoto) {
        Log.d("Presenter", "binding image: ${photo.photoUrl}")
        titleText = photo.description
        contentText = "${photo.username} / ${photo.date}" // TODO apply format
        if (photo.photoUrl.isEmpty() && photo.photoUrl.isBlank()) return

        // Set card size from dimension resources.
        val width = resources.getDimensionPixelSize(R.dimen.card_width)
        val height = resources.getDimensionPixelSize(R.dimen.card_height)
        setMainImageDimensions(width, height)

        Glide.with(context)
            .load(photo.photoUrl)
            .apply(RequestOptions.errorOf(defaultCardImage))
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .into(mainImageView)
    }

    override fun onUnbindViewHolder(viewHolder: ViewHolder) {
        val cardView = viewHolder.view as? ImageCardView ?: return

        // Remove references to images so that the garbage collector can free up memory.

        // Remove references to images so that the garbage collector can free up memory.
        cardView.badgeImage = null
        cardView.mainImage = null
    }
}