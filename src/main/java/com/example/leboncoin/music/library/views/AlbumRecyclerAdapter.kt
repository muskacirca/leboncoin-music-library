package com.example.leboncoin.music.library.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.example.leboncoin.R
import com.example.leboncoin.music.library.model.Album
import com.squareup.picasso.Picasso


class AlbumRecyclerAdapter(private var dataSet: Array<Album>) : RecyclerView.Adapter<AlbumRecyclerAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        @BindView(R.id.album_thumbnail)
        lateinit var albumThumbnailView: ImageView

        @BindView(R.id.album_row_title)
        lateinit var  textView: TextView

        init {
            ButterKnife.bind(this, view);
        }

        @OnClick
        fun onClick(view: View) {
            Navigation.findNavController(view).navigate(R.id.action_FirstFragment_to_SecondFragment, Bundle())
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
        viewHolder.textView.text = dataSet[position].title
        if (viewHolder.albumThumbnailView.drawable == null) {
            Picasso.get().load(dataSet[position].thumbnailUrl)
                .into(viewHolder.albumThumbnailView)
        }
    }

    override fun getItemCount() = dataSet.size

}