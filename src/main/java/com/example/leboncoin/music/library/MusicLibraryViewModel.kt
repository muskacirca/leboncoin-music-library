package com.example.leboncoin.music.library

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.leboncoin.music.library.model.Album
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class MusicLibraryState(
    val items: List<Album> = emptyList(),
    val isLoading: Boolean = false,
    val userMessage: String? = null
)

class MusicLibraryViewModel @Inject constructor(controller: MusicLibraryController) : ViewModel() {

    val uiState: MutableStateFlow<MusicLibraryState> = MutableStateFlow(MusicLibraryState())
    init {

        viewModelScope.launch {
            uiState.emit(MusicLibraryState(isLoading = true))
            val musicLibraryState = when (val albums = controller.getAlbums()) {
                is FetchError -> MusicLibraryState(userMessage = "Impossible de charger votre bibliothèque musicale")
                is FetchSuccess<List<Album>> -> MusicLibraryState(items = albums.items)
            }
            uiState.emit(musicLibraryState)
        }
    }

}