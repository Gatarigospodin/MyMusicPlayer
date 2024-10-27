package com.example.musicplayer.data.repository

import android.util.Log
import com.example.musicplayer.data.local.storage.LocalMusicScanner
import com.example.musicplayer.domain.model.Song
import com.example.musicplayer.domain.repository.MediaRepository
import javax.inject.Inject

class MediaRepositoryImpl @Inject constructor(
    private val localMusicScanner: LocalMusicScanner
) : MediaRepository {
    private var cachedSongs: List<Song> = emptyList()

    override suspend fun getAllSongs(): List<Song> {
        if (cachedSongs.isEmpty()) {
            cachedSongs = localMusicScanner.scanLocalMusic()
        }
        Log.d("MediaRepositoryImpl", cachedSongs.toString())
        return cachedSongs
    }

    override suspend fun searchSongs(query: String): List<Song> {
        return getAllSongs().filter { song ->
            song.title.contains(query, ignoreCase = true) ||
                    song.artist.contains(query, ignoreCase = true)
        }
    }

    override suspend fun getSongByUri(uri: String): Song? {
        return getAllSongs().find { it.uri == uri }
    }
}