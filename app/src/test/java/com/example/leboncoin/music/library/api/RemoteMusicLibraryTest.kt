@file:Suppress("EXPERIMENTAL_FEATURE_WARNING")

package com.example.leboncoin.music.library.api

import com.example.leboncoin.music.library.FetchError
import com.example.leboncoin.music.library.FetchResult
import com.example.leboncoin.music.library.FetchSuccess
import com.example.leboncoin.music.library.RemoteMusicLibrary
import com.example.leboncoin.music.library.model.Album
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verifyBlocking

import retrofit2.Response


class RemoteMusicLibraryTest {

    private lateinit var instance: RemoteMusicLibrary

    private val albums = listOf(
        Album(1, 1, "title", "url", "thumbnailUrl"),
        Album(2, 2, "title 2", "url 2", "thumbnailUrl 2")
    )

    private lateinit var musicLibraryApi: MusicLibraryApi

    @Test
    fun getAlbums_should_load_albums() = runTest {

        setUpTest(FetchSuccess(albums))

        val result = instance.getAlbums()
        assertTrue(result is FetchSuccess)
        assertEquals(2, (result as FetchSuccess).items.size)

        verifyBlocking(musicLibraryApi) { getAlbums() }
    }

    @Test
    fun getAlbums_should_handle_network_errors() = runTest {

        setUpTest(FetchError("NOT_FOUND"))

        val result = instance.getAlbums()
        assertTrue("fetch should failed", result is FetchError)
        assertEquals("NOT_FOUND", (result as FetchError).message)

        verifyBlocking(musicLibraryApi) { getAlbums() }
    }

    private fun setUpTest(response: FetchResult<List<Album>>) {

        musicLibraryApi = mock {
            onBlocking { getAlbums() } doReturn when (response) {
                is FetchError -> Response.error(500, ResponseBody.create(MediaType.get("text/html"), response.message))
                is FetchSuccess -> Response.success(response.items)
            }
        }
        instance = RemoteMusicLibrary(musicLibraryApi)

    }
}