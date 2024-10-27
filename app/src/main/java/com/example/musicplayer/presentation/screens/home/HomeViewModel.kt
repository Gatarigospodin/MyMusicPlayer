package com.example.musicplayer.presentation.screens.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicplayer.domain.model.Song
import com.example.musicplayer.domain.player.MusicPlayerController
import com.example.musicplayer.domain.usecase.PlayerUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val playerUseCases: PlayerUseCases,
    private val playerController: MusicPlayerController
) : ViewModel() {
    private val _songs = MutableStateFlow<List<Song>>(emptyList())
    val songs = _songs.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    val currentSong = playerController.currentSong
    val isPlaying = playerController.isPlaying
    val progress = playerController.progress

    init {
        viewModelScope.launch {
            playerUseCases.getAllSongs().collect {
                _songs.value = it
            }
        }
    }

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
        viewModelScope.launch {
            playerUseCases.searchSongs(query).collect {
                _songs.value = it
            }
        }
    }

    fun playSong(song: Song) {
        viewModelScope.launch {
            playerUseCases.playSong(song)
        }
    }

    fun playNext() {
        viewModelScope.launch {
            playerUseCases.playNext()
        }
    }

    fun playPrevious() {
        viewModelScope.launch {
            playerUseCases.playPrevious()
        }
    }

    fun togglePlayPause() {
        viewModelScope.launch {
            playerUseCases.togglePlayPause()
        }
    }

    fun seekTo(position: Long) {
        viewModelScope.launch {
            playerUseCases.seekTo(position)
        }
    }

    fun playNext(song: Song) {
        viewModelScope.launch {
            playerUseCases.playNext(song)
        }
    }

    fun removeFromQueue(song: Song) {
        viewModelScope.launch {
            playerUseCases.removeFromQueue(song)
        }
    }

    fun stopAfterSong(song: Song) {
        viewModelScope.launch {
            playerUseCases.stopAfterSong(song)
        }
    }
}