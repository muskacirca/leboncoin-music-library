package com.example.leboncoin.music.library.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Album(
    @ColumnInfo(name = "album_id") val albumId: Int,
    @PrimaryKey val id: Int,
    val title: String,
    val url: String,
    @ColumnInfo(name = "thumbnail_url") val thumbnailUrl: String
)