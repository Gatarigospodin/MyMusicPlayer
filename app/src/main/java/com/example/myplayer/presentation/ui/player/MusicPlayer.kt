package com.example.myplayer.presentation.ui.player

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer

class MusicPlayer(context: Context) {
    private val exoPlayer: ExoPlayer = ExoPlayer.Builder(context).build()

    fun play(uri: Uri) {
        try {
            Log.d("MusicPlayer", "Attempting to play song: $uri")
            val mediaItem = MediaItem.fromUri(uri)
            exoPlayer.setMediaItem(mediaItem)
            exoPlayer.prepare()
            exoPlayer.play()
            Log.d("MusicPlayer", "Song started playing")
        } catch (e: Exception) {
            Log.e("MusicPlayer", "Error playing song: ${e.message}")
        }
    }

    fun pause() {
        Log.d("MusicPlayer", "Pausing song")
        exoPlayer.pause()
    }

    fun stop() {
        Log.d("MusicPlayer", "Stopping and releasing player")
        exoPlayer.stop()
        exoPlayer.release()
    }

    val isPlaying: Boolean
        get() = exoPlayer.isPlaying
}