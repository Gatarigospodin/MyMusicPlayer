package com.example.myplayer.domain.repository

import com.example.myplayer.domain.model.Playlist

interface PlaylistRepository {
    suspend fun getAllPlaylists(): List<Playlist>
    suspend fun createPlaylist(name: String): Playlist
    suspend fun addSongToPlaylist(playlistId: Long, songId: Long)
}