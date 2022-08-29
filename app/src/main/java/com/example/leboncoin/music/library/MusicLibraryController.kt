package com.example.leboncoin.music.library

import com.example.leboncoin.music.library.model.Album
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

open class MusicLibraryController @Inject constructor(

    private val localMusicLibrary: LocalMusicLibrary,
    private val remoteMusicLibrary: RemoteMusicLibrary,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {

    open suspend fun getAlbums(): FetchResult<List<Album>> {
        return withContext(ioDispatcher) {
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