package com.example.myplayer.presentation.viewmodel

import android.app.Application
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myplayer.domain.model.Song
import com.example.myplayer.domain.usecase.GetAllSongsUseCase
import com.example.myplayer.presentation.service.PlaybackService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MusicViewModel @Inject constructor(
    private val getAllSongsUseCase: GetAllSongsUseCase,
    application: Application
) : AndroidViewModel(application) {

    private val context = getApplication<Application>()

    private val _songs = MutableStateFlow<List<Song>>(emptyList())
    val songs: StateFlow<List<Song>> = _songs

    private val _currentSong = MutableStateFlow<Song?>(null)
    val currentSong: StateFlow<Song?> = _currentSong

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean> = _isPlaying

    private val _currentPosition = MutableStateFlow(0L)
    val currentPosition: StateFlow<Long> = _currentPosition

    private val _duration = MutableStateFlow(0L)
    val duration: StateFlow<Long> = _duration

    private var playbackService: PlaybackService? = null
    private var isServiceBound = false

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, binder: IBinder?) {
            val serviceBinder = binder as PlaybackService.PlaybackBinder
            playbackService = serviceBinder.getService()
            isServiceBound = true

            playbackService?.let { service ->
                viewModelScope.launch {
                    service.currentPosition.collect { position ->
                        _currentPosition.value = position
                    }
                }

                viewModelScope.launch {
                    service.duration.collect { dur ->
                        _duration.value = dur
                    }
                }
            }
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            playbackService = null
            isServiceBound = false
        }
    }

    init {
        loadSongs()
        bindService()
    }

    override fun onCleared() {
        super.onCleared()
        unbindService()
    }

    private fun bindService() {
        val intent = PlaybackService.newIntent(context)
        context.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
    }

    private fun unbindService() {
        if (isServiceBound) {
            context.unbindService(serviceConnection)
            isServiceBound = false
        }
    }

    private fun loadSongs() {
        viewModelScope.launch {
            val songList = getAllSongsUseCase()
            _songs.value = songList
        }
    }

    fun playSong(song: Song) {
        _currentSong.value = song
        _isPlaying.value = true
        playbackService?.play(song.uri)
    }

    fun pauseSong() {
        _isPlaying.value = false
        playbackService?.pause()
    }

    fun resumeSong() {
        _isPlaying.value = true
        playbackService?.resume()
    }

    fun stopSong() {
        _currentSong.value = null
        _isPlaying.value = false
        playbackService?.stop()
    }

    fun seekTo(position: Long) {
        playbackService?.seekTo(position)
    }

    fun nextSong() {
        val currentIndex = _songs.value.indexOf(_currentSong.value)
        if (currentIndex != -1 && currentIndex < _songs.value.size - 1) {
            val nextSong = _songs.value[currentIndex + 1]
            playSong(nextSong)
        }
    }

    fun previousSong() {
        val currentIndex = _songs.value.indexOf(_currentSong.value)
        if (currentIndex > 0) {
            val previousSong = _songs.value[currentIndex - 1]
            playSong(previousSong)
        }
    }
}

