package com.example.flickrdemo.grid

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.leanback.widget.TitleViewAdapter
import com.example.flickrdemo.databinding.ViewPhotoSearchTitleBinding

class PhotoSearchTitleHeader(context: Context, attrs: AttributeSet) :
    ConstraintLayout(context, attrs),
    TitleViewAdapter.Provider {

    private val binding: ViewPhotoSearchTitleBinding

    init {
        val inflater = LayoutInflater.from(context)
        binding = ViewPhotoSearchTitleBinding.inflate(inflater, this)
    }

    private val titleViewAdapter = object : TitleViewAdapter() {
        override fun getSearchAffordanceView(): View {
            return binding.browseOrb
        }

        override fun setTitle(titleText: CharSequence?) {
            binding.browseTitle.text = titleText
        }

        override fun setBadgeDrawable(drawable: Drawable?) {
            binding.browseBadge.setImageDrawable(drawable)
            binding.browseBadge.visibility = View.VISIBLE
        }

        override fun setOnSearchClickedListener(listener: OnClickListener?) {
            binding.browseOrb.setOnClickListener(listener)
            binding.browseOrb.visibility = View.VISIBLE
        }
    }

    override fun getTitleViewAdapter(): TitleViewAdapter = titleViewAdapter
}