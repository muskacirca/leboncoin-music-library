package com.example.leboncoin.music.library.api

import com.example.leboncoin.music.library.model.Album
import retrofit2.Response
import retrofit2.http.GET

interface MusicLibraryApi {

    @GET("/img/shared/technical-test.json")
    suspend fun getAlbums() : Response<List<Album>>
}