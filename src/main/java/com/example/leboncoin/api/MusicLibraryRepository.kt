package com.example.leboncoin.api

import com.example.leboncoin.model.Album
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

interface MusicLibraryRepository {

    fun getAlbums(): Flow<FetchResult<List<Album>>>
}

class LocalMusicLibrary : MusicLibraryRepository {
    override fun getAlbums(): Flow<FetchResult<List<Album>>> {
        TODO("Not yet implemented")
    }
}


open class RemoteMusicLibrary @Inject constructor(
    private val musicLibraryApi: MusicLibraryApi) : MusicLibraryRepository {

    override fun getAlbums(): Flow<FetchResult<List<Album>>> {
        return MutableStateFlow(runBlocking(Dispatchers.IO) {
            val response = musicLibraryApi.getAlbums()
            when (response.code()) {
                200 -> FetchSuccess(response.body() ?: emptyList())
                else -> FetchError("NOT_FOUND")
            }
        })
    }
}
sealed interface FetchResult<T>
class FetchSuccess<T>(val items: T) : FetchResult<T>
class FetchError<T>(val message: String, cause: T? = null): FetchResult<T>
