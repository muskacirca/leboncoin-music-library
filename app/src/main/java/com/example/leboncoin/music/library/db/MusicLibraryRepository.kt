package com.example.leboncoin.music.library.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.leboncoin.music.library.model.Album

@Dao
interface MusicLibraryRepository {

    @Query("SELECT * FROM album")
    fun getAll(): List<Album>

    @Query("SELECT EXISTS(SELECT 1 FROM album LIMIT 1)")
    fun isPopulated(): Boolean

    @Query("SELECT * FROM album WHERE id IN (:ids)")
    fun loadAllByIds(ids: IntArray): List<Album>

    @Insert
    fun insertAll(vararg albums: Album)
}