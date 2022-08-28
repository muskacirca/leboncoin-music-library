package com.example.leboncoin.music.library.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.leboncoin.music.library.model.Album

@Database(entities = [Album::class], version = 1)
abstract class MusicLibraryDatabase : RoomDatabase() {
    abstract fun albumRepository(): MusicLibraryRepository
}