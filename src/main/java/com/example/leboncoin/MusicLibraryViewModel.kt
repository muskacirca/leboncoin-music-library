package com.example.leboncoin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.leboncoin.api.FetchError
import com.example.leboncoin.api.FetchSuccess
import com.example.leboncoin.api.RemoteMusicLibrary
import com.example.leboncoin.model.Album
import kotlinx.coroutines.flow.*
import javax.inject.Inject

data class MusicLibraryState(
    val items: List<Album> = emptyList(),
    val isLoading: Boolean = false,
    val userMessage: String? = null
)

class MusicLibraryViewModel @Inject constructor(remoteMusicLibrary: RemoteMusicLibrary) : ViewModel() {

    val uiState = remoteMusicLibrary.getAlbums().map { albums ->
        when (albums) {
            is FetchError -> MusicLibraryState(userMessage = "Impossible de charger votre bibliothèque musicale") // TODO move in resource
            is FetchSuccess<List<Album>> -> MusicLibraryState(items = albums.items)
        }

    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000), // TODO do something with this value
        initialValue = MusicLibraryState(isLoading = true)
    )

}