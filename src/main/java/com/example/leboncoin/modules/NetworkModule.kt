package com.example.leboncoin.modules

import com.example.leboncoin.api.RemoteMusicLibrary
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class NetworkModule {

    @Provides
    fun providedRemoteMusicLibrary(): RemoteMusicLibrary {
        return Retrofit.Builder()
            .baseUrl("https://static.leboncoin.fr") // TODO put int conf
            .build()
            .create(RemoteMusicLibrary::class.java)
    }
}