package com.example.myplayer.data.model

import android.net.Uri

data class SongEntity(
    val id: Long,
    val title: String,
    val artist: String,
    val uri: String,
    val album: String,
    val duration: Long,
    val dateAdded: Long,
    var hasLyrics: Boolean = false
)