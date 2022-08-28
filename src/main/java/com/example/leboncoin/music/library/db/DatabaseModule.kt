package com.example.leboncoin.music.library.db

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun providesDatabase(@ApplicationContext context: Context): MusicLibraryDatabase {
        return Room.databaseBuilder(context, MusicLibraryDatabase::class.java, "music_library")
            .build()
    }

    @Provides
    fun providesMusicLibraryRepository(@ApplicationContext context: Context, database: MusicLibraryDatabase): MusicLibraryRepository {
        return MusicLibraryRepository_Impl(database)
    }
}