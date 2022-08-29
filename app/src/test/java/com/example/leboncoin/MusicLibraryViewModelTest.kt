package com.example.leboncoin

import com.example.leboncoin.music.library.*
import com.example.leboncoin.music.library.model.Album
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verifyBlocking

class MusicLibraryViewModelTest {

    private lateinit var instance: MusicLibraryViewModel

    private val albums = listOf(
        Album(1, 1, "title", "url", "thumbnailUrl"),
        Album(2, 2, "title 2", "url 2", "thumbnailUrl 2")
    )

    private lateinit var controller: MusicLibraryController

    @Before
    fun setup() {
        Dispatchers.setMain(StandardTestDispatcher())
    }

    @After
    fun teardown() {
        Dispatchers.resetMain()
    }

    @Test
    fun should_initialize_the_state_properly() = runTest {

        val controller = mock<MusicLibraryController> {
            onBlocking { getAlbums() } doReturn FetchSuccess(albums)
        }

        instance = MusicLibraryViewModel(controller)

        val results = mutableListOf<MusicLibraryState>()
        val job = instance.uiState
            .onEach(results::add)
            .launchIn(CoroutineScope(UnconfinedTestDispatcher(testScheduler)))

        runCurrent()

        val first = results.first()
        assertFalse(first.isLoading)
        assertEquals(0, first.items.size)

        val second = results[1]
        assertTrue(second.isLoading)
        assertEquals(0, second.items.size)

        val last = results.last()
        assertFalse(last.isLoading)
        assertEquals(2, last.items.size)


        verifyBlocking(controller) { getAlbums() }
        job.cancel()
    }
}