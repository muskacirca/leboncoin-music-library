package com.example.leboncoin.music.library

import com.example.leboncoin.music.library.model.Album
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MusicLibraryController @Inject constructor(

    private val localMusicLibrary: LocalMusicLibrary,
    private val remoteMusicLibrary: RemoteMusicLibrary) {

    suspend fun getAlbums(): FetchResult<List<Album>> {
        return withContext(Dispatchers.IO) {
            if (localMusicLibrary.isDatabasePopulated()) {
                localMusicLibrary.getAlbums()

            } else {
                val albums = remoteMusicLibrary.getAlbums()
                if (albums is FetchSuccess) localMusicLibrary.insertAlbums(albums.items)
                albums
            }
        }
    }
}