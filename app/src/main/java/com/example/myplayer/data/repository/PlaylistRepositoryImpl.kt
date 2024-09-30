package com.example.myplayer.data.repository

import com.example.myplayer.domain.model.Playlist
import com.example.myplayer.domain.repository.PlaylistRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PlaylistRepositoryImpl : PlaylistRepository {

    private val playlists = mutableListOf<Playlist>()

    override suspend fun getAllPlaylists(): List<Playlist> = withContext(Dispatchers.IO) {
        return@withContext playlists
    }

    override suspend fun createPlaylist(name: String): Playlist = withContext(Dispatchers.IO) {
        val newPlaylist = Playlist(
            id = (playlists.maxOfOrNull { it.id } ?: 0) + 1,
            name = name,
            songs = emptyList()
        )
        playlists.add(newPlaylist)
        return@withContext newPlaylist
    }

    override suspend fun addSongToPlaylist(playlistId: Long, songId: Long) {
        withContext(Dispatchers.IO) {
            val playlist = playlists.find { it.id == playlistId }
            /*val song = MusicRepositoryImpl().getSongById(songId)
                if (playlist != null && song != null) {
                    playlist.songs += song
                } else {

                }*/
        }
    }
}