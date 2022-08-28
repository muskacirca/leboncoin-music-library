package com.example.leboncoin.music.library.db

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.leboncoin.music.library.model.Album
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class AlbumRepositoryTest {

    private lateinit var albumRepository: MusicLibraryRepository
    private lateinit var db: MusicLibraryDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, MusicLibraryDatabase::class.java).build()
        albumRepository = db.albumRepository()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun should_insert_album_in_database() {

        albumRepository.insertAll(anAlbum(1), anAlbum(2))
        val albums = albumRepository.getAll()
        assertEquals(2, albums.size)
    }

    @Test
    @Throws(Exception::class)
    fun should_know_when_database_is_populated() {

        assertFalse(albumRepository.isPopulated())

        albumRepository.insertAll(anAlbum(1))

        assertTrue(albumRepository.isPopulated())
    }

    private fun anAlbum(withId: Int): Album {
        return Album(withId, withId, "title", "url", "thumbnailUrl")
    }
}