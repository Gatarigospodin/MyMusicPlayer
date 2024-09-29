package com.example.myplayer.di

import android.content.ContentResolver
import android.content.Context
import com.example.myplayer.data.repository.MusicRepositoryImpl
import com.example.myplayer.data.source.LocalMusicDataSource
import com.example.myplayer.domain.repository.MusicRepository
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
    fun provideContentResolver(@ApplicationContext context: Context): ContentResolver {
        return context.contentResolver
    }

    @Provides
    fun provideLocalMusicDataSource(contentResolver: ContentResolver): LocalMusicDataSource {
        return LocalMusicDataSource(contentResolver)
    }

    @Provides
    fun provideMusicRepository(localMusicDataSource: LocalMusicDataSource): MusicRepository {
        return MusicRepositoryImpl(localMusicDataSource)
    }
}