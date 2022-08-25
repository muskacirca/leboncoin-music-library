@file:Suppress("EXPERIMENTAL_FEATURE_WARNING")

package com.example.leboncoin.api

import com.example.leboncoin.model.Album
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

    private val albums = listOf(Album(1, 1, "title", "url", "thumbnailUrl"),
        Album(2, 2, "title 2", "url 2", "thumbnailUrl 2"))

    private lateinit var musicLibraryApi: MusicLibraryApi

    @Test
    fun getAlbums_should_load_albums() = runTest {

        setUpTest(FetchSuccess(albums))

        val result = instance.getAlbums()
        val actual = result.first()
        assertTrue(actual is FetchSuccess)
        assertEquals(2, (actual as FetchSuccess).items.size)

        verifyBlocking(musicLibraryApi) { getAlbums() }
    }

    @Test
    fun getAlbums_should_handle_network_errors() = runTest {

        setUpTest(FetchError("NOT_FOUND"))

        val result = instance.getAlbums()
        val actual = result.first()
        assertTrue("fetch should failed", actual is FetchError)
        assertEquals("NOT_FOUND", (actual as FetchError).message)

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