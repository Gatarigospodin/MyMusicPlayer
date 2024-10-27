package com.example.musicplayer.domain.model

data class Song(
    val id: Long,
    val title: String,
    val artist: String,
    val uri: String,
    val duration: Long,
    val album: String,
    val albumArt: String?,
    val addedTime: Long
)