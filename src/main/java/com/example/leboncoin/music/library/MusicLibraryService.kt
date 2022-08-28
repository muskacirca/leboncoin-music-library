package com.example.leboncoin.music.library

import com.example.leboncoin.music.library.api.MusicLibraryApi
import com.example.leboncoin.music.library.db.MusicLibraryRepository
import com.example.leboncoin.music.library.model.Album
import java.net.ConnectException
import javax.inject.Inject

interface MusicLibraryService {

    suspend fun getAlbums(): FetchResult<List<Album>>
}

class LocalMusicLibrary @Inject constructor(
    private val musicLibraryRepository: MusicLibraryRepository) : MusicLibraryService {

    fun isDatabasePopulated(): Boolean {
        return musicLibraryRepository.isPopulated()
    }

    fun insertAlbums(albums: List<Album>) {
        musicLibraryRepository.insertAll(*albums.toTypedArray())
    }

    override suspend fun getAlbums(): FetchResult<List<Album>> {
        return FetchSuccess(musicLibraryRepository.getAll())
    }

}

open class RemoteMusicLibrary @Inject constructor(
    private val musicLibraryApi: MusicLibraryApi
) : MusicLibraryService {

    override suspend fun getAlbums(): FetchResult<List<Album>> {

        return try {
            val response = musicLibraryApi.getAlbums()
            return when (response.code()) {
                200 -> FetchSuccess(response.body() ?: emptyList())
                else -> FetchError("NOT_FOUND")
            }
        } catch (e: Exception) {
            when (e) {
                is ConnectException -> FetchError("NO_INTERNET")
                else -> FetchError("UNKNOWN_ERROR")
            }
        }


    }
}
