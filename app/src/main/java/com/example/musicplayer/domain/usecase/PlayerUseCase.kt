package com.example.musicplayer.domain.usecase

import com.example.musicplayer.domain.model.Song
import com.example.musicplayer.domain.player.MusicPlayerController
import com.example.musicplayer.domain.repository.MediaRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PlayerUseCases @Inject constructor(
    private val mediaRepository: MediaRepository,
    private val playerController: MusicPlayerController
) {
    suspend fun playSong(song: Song) {
        playerController.playSong(song)
    }

    suspend fun playNext() {
        playerController.playNext()
    }

    suspend fun playPrevious() {
        playerController.playPrevious()
    }

    suspend fun togglePlayPause() {
        playerController.togglePlayPause()
    }

    suspend fun seekTo(position: Long) {
        playerController.seekTo(position)
    }

    suspend fun addToQueue(song: Song) {
        playerController.addToQueue(song)
    }

    suspend fun removeFromQueue(song: Song) {
        playerController.removeFromQueue(song)
    }

    suspend fun playNext(song: Song) {
        playerController.addNextInQueue(song)
    }

    suspend fun stopAfterSong(song: Song) {
        playerController.stopAfterSong(song)
    }

    suspend fun getAllSongs(): Flow<List<Song>> = flow {
        emit(mediaRepository.getAllSongs())
    }

    suspend fun searchSongs(query: String): Flow<List<Song>> = flow {
        emit(mediaRepository.searchSongs(query))
    }
}