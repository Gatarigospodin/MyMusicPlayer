package com.example.musicplayer.di

import android.content.Context
import com.example.musicplayer.data.local.storage.LocalMusicScanner
import com.example.musicplayer.data.repository.MediaRepositoryImpl
import com.example.musicplayer.domain.player.MusicPlayerController
import com.example.musicplayer.domain.repository.MediaRepository
import com.example.musicplayer.domain.usecase.PlayerUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideMediaRepository(
        localMusicScanner: LocalMusicScanner
    ): MediaRepository = MediaRepositoryImpl(localMusicScanner)

    @Provides
    @Singleton
    fun providePlayerUseCases(
        mediaRepository: MediaRepository,
        playerController: MusicPlayerController
    ): PlayerUseCases = PlayerUseCases(mediaRepository, playerController)

    @Provides
    @Singleton
    fun provideMusicPlayerController(
        @ApplicationContext context: Context
    ): MusicPlayerController = MusicPlayerController(context)
}