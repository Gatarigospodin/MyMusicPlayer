package com.example.musicplayer.domain.repository

import com.example.musicplayer.domain.model.Song

interface MediaRepository {
    suspend fun getAllSongs(): List<Song>
    suspend fun searchSongs(query: String): List<Song>
    suspend fun getSongByUri(uri: String): Song?
}