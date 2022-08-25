package com.example.leboncoin.api

import com.example.leboncoin.model.Album
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface MusicLibraryRepository {

    suspend fun getAlbums(): List<Album>
}

class LocalMusicLibrary : MusicLibraryRepository {
    override suspend fun getAlbums(): List<Album> {
        TODO("Not yet implemented")
    }
}


class RemoteMusicLibrary @Inject constructor(
    private val musicLibraryApi: MusicLibraryApi) : MusicLibraryRepository {
    override suspend fun getAlbums(): List<Album> {
        return withContext(Dispatchers.IO) {
            val response = musicLibraryApi.getAlbums()
            when (response.code()) {
                200 -> response.body() ?: emptyList()
                else -> throw FetchError(Errors.NETWORK_ERROR)
            }
        }
    }
}
enum class Errors {
    NETWORK_ERROR
}

sealed class ApiException(message: Errors? = null, cause: Throwable? = null) : RuntimeException(message?.name, cause)
class FetchError(message: Errors, cause: Throwable? = null) : ApiException(message, cause)