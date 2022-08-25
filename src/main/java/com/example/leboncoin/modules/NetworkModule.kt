package com.example.leboncoin.modules

import com.example.leboncoin.api.MusicLibraryApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class NetworkModule {

    @Provides
    fun providedMusicLibraryApi(): MusicLibraryApi {
        return Retrofit.Builder()
            .baseUrl("https://static.leboncoin.fr") // TODO put int conf
            .build()
            .create(MusicLibraryApi::class.java)
    }
}