package com.example.musicplayer.domain.player

import android.content.Context
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.example.musicplayer.domain.model.Song
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MusicPlayerController @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val player: ExoPlayer = ExoPlayer.Builder(context).build()
    private val queue = mutableListOf<Song>()
    private var currentIndex = -1
    private var stopAfterCurrentSong = false

    private val _currentSong = MutableStateFlow<Song?>(null)
    val currentSong: StateFlow<Song?> = _currentSong.asStateFlow()

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean> = _isPlaying.asStateFlow()

    private val _progress = MutableStateFlow(0L)
    val progress: StateFlow<Long> = _progress.asStateFlow()

    init {
        player.addListener(object : Player.Listener {
            override fun onPlaybackStateChanged(state: Int) {
                super.onPlaybackStateChanged(state)
                when (state) {
                    Player.STATE_ENDED -> {
                        if (stopAfterCurrentSong) {
                            stopAfterCurrentSong = false
                            player.pause()
                            _isPlaying.value = false
                        } else {
                            playNext()
                        }
                    }
                }
            }

            override fun onIsPlayingChanged(playing: Boolean) {
                super.onIsPlayingChanged(playing)
                _isPlaying.value = playing
            }

        })

        // Обновление прогресса воспроизведения
        CoroutineScope(Dispatchers.Main).launch {
            while (true) {
                delay(1000)
                if (player.isPlaying) {
                    _progress.value = player.currentPosition
                }
            }
        }
    }

    fun playSong(song: Song) {
        val index = queue.indexOf(song)
        if (index != -1) {
            currentIndex = index
        } else {
            queue.add(song)
            currentIndex = queue.lastIndex
        }

        val mediaItem = MediaItem.fromUri(song.uri)
        player.setMediaItem(mediaItem)
        player.prepare()
        player.play()
        _currentSong.value = song
    }

    fun togglePlayPause() {
        if (player.isPlaying) {
            player.pause()
        } else {
            player.play()
        }
    }

    fun playNext() {
        if (queue.isEmpty()) return
        currentIndex = (currentIndex + 1) % queue.size
        playSong(queue[currentIndex])
    }

    fun playPrevious() {
        if (queue.isEmpty()) return
        currentIndex = if (currentIndex <= 0) queue.lastIndex else currentIndex - 1
        playSong(queue[currentIndex])
    }

    fun seekTo(position: Long) {
        player.seekTo(position)
    }

    fun addToQueue(song: Song) {
        queue.add(song)
    }

    fun addNextInQueue(song: Song) {
        if (currentIndex == -1) {
            addToQueue(song)
        } else {
            queue.add(currentIndex + 1, song)
        }
    }

    fun removeFromQueue(song: Song) {
        val index = queue.indexOf(song)
        if (index == -1) return

        queue.removeAt(index)
        if (index < currentIndex) {
            currentIndex--
        }
        if (queue.isEmpty()) {
            currentIndex = -1
            player.stop()
            _currentSong.value = null
        }
    }

    fun stopAfterSong(song: Song) {
        if (queue.contains(song)) {
            stopAfterCurrentSong = true
        }
    }

    fun release() {
        player.release()
    }
}