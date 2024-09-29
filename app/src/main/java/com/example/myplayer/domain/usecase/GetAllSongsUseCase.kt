package com.example.myplayer.domain.usecase

import com.example.myplayer.domain.model.Song
import com.example.myplayer.domain.repository.MusicRepository
import javax.inject.Inject

class GetAllSongsUseCase @Inject constructor(
    private val repository: MusicRepository
) {
    suspend operator fun invoke(): List<Song> {
        return repository.getAllSongs()
    }
}