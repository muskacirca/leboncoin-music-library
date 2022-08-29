package com.example.leboncoin.music.library.views

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.leboncoin.R
import com.example.leboncoin.databinding.AlbumRowLayoutBinding
import com.example.leboncoin.music.library.OnItemClickListener
import com.example.leboncoin.music.library.model.Album
import com.squareup.picasso.Picasso

class AlbumRecyclerAdapter(
    private var dataSet: Array<Album>,
    private val onItemClickListener: OnItemClickListener<Album>
) : RecyclerView.Adapter<AlbumRecyclerAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val binding = AlbumRowLayoutBinding.bind(view)

        fun bind(album: Album, listener: OnItemClickListener<Album>) {
            binding.albumRow.setOnClickListener { listener.onItemClick(it, album) }
            binding.albumRowTitle.text = album.title
            if (binding.albumThumbnail.drawable == null) {
                Picasso.get()
                    .load(album.thumbnailUrl)
                    .error(R.drawable.ic_img_not_found)
                    .into(binding.albumThumbnail)
            }
        }
    }

    fun updateData(albums: Array<Album>) {

        this.dataSet = albums
        this.notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.album_row_layout, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(dataSet[position], onItemClickListener)
    }

    override fun getItemCount() = dataSet.size

}