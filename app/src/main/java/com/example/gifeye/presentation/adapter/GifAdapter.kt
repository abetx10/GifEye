package com.example.gifeye.presentation.adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.gifeye.data.model.GifData
import com.example.gifeye.databinding.ItemGifBinding

class GifAdapter(
    private var gifList: List<GifData>,
    private val onDeleteClick: (String) -> Unit,
    private val onItemClick: (Int) -> Unit
) : RecyclerView.Adapter<GifAdapter.GifViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GifViewHolder {
        val binding = ItemGifBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GifViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GifViewHolder, position: Int) {
        holder.bind(gifList[position])
        holder.itemView.setOnClickListener {
            onItemClick(position)
        }
    }

    override fun getItemCount(): Int = gifList.size

    inner class GifViewHolder(private val binding: ItemGifBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(gif: GifData) {
            binding.itemProgressBar.visibility = View.VISIBLE
            binding.imageViewGif.visibility = View.INVISIBLE

            Glide.with(binding.imageViewGif.context)
                .load(gif.images.original.url)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        binding.itemProgressBar.visibility = View.GONE
                        binding.imageViewGif.visibility = View.VISIBLE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        binding.itemProgressBar.visibility = View.GONE
                        binding.imageViewGif.visibility = View.VISIBLE
                        return false
                    }
                })
                .into(binding.imageViewGif)

            binding.deleteButton.setOnClickListener {
                onDeleteClick(gif.id)
            }
        }
    }

    fun updateGifs(newGifList: List<GifData>) {
        gifList = newGifList
        notifyDataSetChanged()
    }
}