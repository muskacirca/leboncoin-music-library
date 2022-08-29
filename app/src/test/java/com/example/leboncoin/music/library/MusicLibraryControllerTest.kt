package com.example.leboncoin.music.library

import com.example.leboncoin.music.library.model.Album
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class MusicLibraryControllerTest {

    private lateinit var instance: MusicLibraryController
    private lateinit var localRepo: LocalMusicLibrary
    private lateinit var remoteRepo: RemoteMusicLibrary

    private val albums = listOf(
        Album(1, 1, "title", "url", "thumbnailUrl"),
        Album(2, 2, "title 2", "url 2", "thumbnailUrl 2")
    )

    @Before
    fun setup() {
        Dispatchers.setMain(StandardTestDispatcher()) // !!!
    }

    @After
    fun teardown() {
        Dispatchers.resetMain()
    }

    @Test
    fun should_load_albums_from_database() = runTest {

        localRepo = mock {
            onGeneric { isDatabasePopulated() } doReturn true
            onBlocking { getAlbums() } doReturn FetchSuccess(albums)
        }
        remoteRepo = mock {  }

        instance = MusicLibraryController(localRepo, remoteRepo, StandardTestDispatcher())

        val result = instance.getAlbums()

        assertTrue(result is FetchSuccess)
    }
}