package com.example.myplayer.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myplayer.domain.model.Song
import com.example.myplayer.domain.usecase.GetAllSongsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MusicViewModel @Inject constructor(
    private val getAllSongsUseCase: GetAllSongsUseCase
) : ViewModel() {

    private val _songs = MutableStateFlow<List<Song>>(emptyList())
    val songs: StateFlow<List<Song>> = _songs

    init {
        loadSongs()
    }

    private fun loadSongs() {
        viewModelScope.launch {
            val songList = getAllSongsUseCase()
            _songs.value = songList
        }
    }
}