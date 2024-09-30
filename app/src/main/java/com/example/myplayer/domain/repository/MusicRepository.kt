package com.example.myplayer.domain.repository

import com.example.myplayer.domain.model.Song

interface MusicRepository {
    suspend fun getAllSongs(): List<Song>
    suspend fun getSongById(songId: Long): Song?
}