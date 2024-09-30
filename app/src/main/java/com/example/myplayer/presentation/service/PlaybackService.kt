package com.example.myplayer.presentation.service

import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.ServiceInfo
import android.os.Binder
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.example.myplayer.MainActivity
import com.example.myplayer.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PlaybackService : Service() {

    private val binder = PlaybackBinder()
    private lateinit var exoPlayer: ExoPlayer
    private val coroutineScope = CoroutineScope(Dispatchers.Main + Job())

    private val _currentPosition = MutableStateFlow(0L)
    val currentPosition: StateFlow<Long> = _currentPosition

    private val _duration = MutableStateFlow(0L)
    val duration: StateFlow<Long> = _duration

    inner class PlaybackBinder : Binder() {
        fun getService(): PlaybackService = this@PlaybackService
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, PlaybackService::class.java)
        }
        const val NOTIFICATION_ID = 1
    }

    override fun onCreate() {
        super.onCreate()
        exoPlayer = ExoPlayer.Builder(this).build()
        observePlayerPosition()
    }

    override fun onBind(intent: Intent?): IBinder {
        return binder
    }

    fun play(mediaUri: String) {
        val mediaItem = MediaItem.fromUri(mediaUri)
        exoPlayer.setMediaItem(mediaItem)
        exoPlayer.prepare()
        exoPlayer.play()

        startForeground(
            NOTIFICATION_ID,
            createNotification(),
            ServiceInfo.FOREGROUND_SERVICE_TYPE_MEDIA_PLAYBACK
        )
    }

    private fun createNotification(): Notification {
        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE
        )

        return NotificationCompat.Builder(this, "music_channel")
            .setContentTitle("Music Player")
            .setContentText("Playing: ${exoPlayer.currentMediaItem?.mediaId}")
            .setSmallIcon(R.drawable.ic_music_note)
            .setContentIntent(pendingIntent)
            .build()
    }

    fun pause() {
        exoPlayer.pause()
    }

    fun resume() {
        exoPlayer.play()
    }

    fun stop() {
        exoPlayer.stop()
        stopForeground(true)
        coroutineScope.cancel()
    }

    fun seekTo(position: Long) {
        exoPlayer.seekTo(position)
    }

    private fun observePlayerPosition() {
        coroutineScope.launch {
            while (true) {
                _currentPosition.value = exoPlayer.currentPosition
                delay(1000L)
            }
        }
    }

    override fun onDestroy() {
        exoPlayer.release()
        coroutineScope.cancel()
        super.onDestroy()
    }
}