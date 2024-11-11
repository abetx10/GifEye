package com.example.gifeye.presentation.adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.gifeye.data.model.GifData
import com.example.gifeye.databinding.ItemGifFullScreenBinding

class GifPagerAdapter : RecyclerView.Adapter<GifPagerAdapter.GifViewHolder>() {

    private var gifList: List<GifData> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GifViewHolder {
        val binding = ItemGifFullScreenBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GifViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GifViewHolder, position: Int) {
        holder.bind(gifList[position])
    }

    override fun getItemCount(): Int = gifList.size

    fun submitList(gifs: List<GifData>) {
        gifList = gifs
        notifyDataSetChanged()
    }

    inner class GifViewHolder(private val binding: ItemGifFullScreenBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(gif: GifData) {
            binding.fullScreenProgressBar.visibility = View.VISIBLE

            Glide.with(binding.imageViewGifFullScreen.context)
                .load(gif.images.original.url)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        binding.fullScreenProgressBar.visibility = View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: com.bumptech.glide.load.DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        binding.fullScreenProgressBar.visibility = View.GONE
                        return false
                    }
                })
                .into(binding.imageViewGifFullScreen)
        }
    }
}