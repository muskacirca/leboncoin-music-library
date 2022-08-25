package com.example.leboncoin.api

import com.example.leboncoin.model.Album
import retrofit2.Response
import retrofit2.http.GET

interface MusicLibraryApi {

    @GET("/img/shared/technical-test.json")
    suspend fun getAlbums() : Response<List<Album>>
}