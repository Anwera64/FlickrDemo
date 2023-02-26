package com.example.flickrdemo.grid.adapters

import android.content.Context
import android.view.LayoutInflater
import androidx.leanback.widget.BaseCardView
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