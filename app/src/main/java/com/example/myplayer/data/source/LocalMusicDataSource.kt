package com.example.myplayer.data.source

import android.content.ContentResolver
import android.provider.MediaStore
import com.example.myplayer.data.model.SongEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LocalMusicDataSource(private val contentResolver: ContentResolver) {

    suspend fun fetchAllSongs(): List<SongEntity> = withContext(Dispatchers.IO) {
        val songs = mutableListOf<SongEntity>()
        val uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.DATA,
            MediaStore.Audio.Media.ALBUM,
            MediaStore.Audio.Media.DURATION,
            MediaStore.Audio.Media.DATE_ADDED
        )
        val selection = "${MediaStore.Audio.Media.IS_MUSIC} != 0"
        val cursor = contentResolver.query(uri, projection, selection, null, null)

        cursor?.use { cur ->
            val idIndex = cur.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)
            val titleIndex = cur.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)
            val artistIndex = cur.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)
            val dataIndex = cur.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)
            val albumIndex = cur.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM)
            val durationIndex = cur.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)
            val dateAddedIndex = cur.getColumnIndexOrThrow(MediaStore.Audio.Media.DATE_ADDED)

            while (cur.moveToNext()) {
                val song = SongEntity(
                    id = cur.getLong(idIndex),
                    title = cur.getString(titleIndex),
                    artist = cur.getString(artistIndex),
                    uri = cur.getString(dataIndex),
                    album = cur.getString(albumIndex),
                    duration = cur.getLong(durationIndex),
                    dateAdded = cur.getLong(dateAddedIndex)
                )
                songs.add(song)
            }
        }
        songs.sortByDescending { it.dateAdded }
        return@withContext songs
    }
}