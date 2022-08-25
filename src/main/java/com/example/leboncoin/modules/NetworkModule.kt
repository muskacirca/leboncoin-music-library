package com.example.leboncoin.modules

import com.example.leboncoin.api.MusicLibraryApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class NetworkModule {

    @Provides
    fun providedMusicLibraryApi(): MusicLibraryApi {
        return Retrofit.Builder()
            .baseUrl("https://static.leboncoin.fr") // TODO put int conf
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MusicLibraryApi::class.java)
    }
}