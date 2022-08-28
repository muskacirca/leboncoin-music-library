package com.example.leboncoin.music.library.api

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun providedMusicLibraryApi(@ApplicationContext context: Context): MusicLibraryApi {
        return Retrofit.Builder()
            .baseUrl("https://static.leboncoin.fr") // TODO put int conf
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MusicLibraryApi::class.java)
    }
}