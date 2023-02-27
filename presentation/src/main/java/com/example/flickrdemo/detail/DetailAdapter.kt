package com.example.flickrdemo.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.domain.entities.FlickrPhoto
import com.example.domain.utils.DateUtil
import com.example.flickrdemo.R
import com.example.flickrdemo.databinding.ViewPhotoFullscreenBinding

class DetailAdapter : RecyclerView.Adapter<DetailAdapter.DetailViewHolder>() {

    var itemList: List<FlickrPhoto> = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DetailViewHolder {
        return DetailViewHolder(
            ViewPhotoFullscreenBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: DetailViewHolder, position: Int) {
        holder.bind(itemList[position])
    }

    class DetailViewHolder(private val binding: ViewPhotoFullscreenBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(photo: FlickrPhoto) {
            val resources = binding.root.resources
            binding.titleText.text = photo.description
            val dateString = DateUtil.parseToString(photo.date)
            binding.contentText.text = resources.getString(
                R.string.username_date_placeholder,
                photo.username,
                dateString
            )

            if (photo.photoUrl.isEmpty() && photo.photoUrl.isBlank()) return

            Glide.with(binding.root.context)
                .load(photo.photoUrl)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(binding.mainImage)
        }

    }
}