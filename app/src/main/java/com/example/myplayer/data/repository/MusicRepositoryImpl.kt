package com.example.myplayer.data.repository
import android.content.ContentResolver
import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import com.example.myplayer.data.source.LocalMusicDataSource
import com.example.myplayer.domain.model.Song
import com.example.myplayer.domain.repository.MusicRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MusicRepositoryImpl @Inject constructor(
    private val localMusicDataSource: LocalMusicDataSource
) : MusicRepository {

    override suspend fun getAllSongs(): List<Song> {
        val songEntities = localMusicDataSource.fetchAllSongs()
        return songEntities.map { entity ->
            Song(
                id = entity.id,
                title = entity.title,
                artist = entity.artist,
                uri = entity.uri,
                album = entity.album,
                duration = entity.duration,
                dateAdded = entity.dateAdded
            )
        }
    }

    override suspend fun getSongById(songId: Long): Song? {
        TODO("Not yet implemented")
    }
}