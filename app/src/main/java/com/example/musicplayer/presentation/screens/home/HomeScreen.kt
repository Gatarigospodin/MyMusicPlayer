package com.example.musicplayer.presentation.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.musicplayer.presentation.components.PlayerControls
import com.example.musicplayer.presentation.components.SearchBar
import com.example.musicplayer.presentation.components.SongItem

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val songs by viewModel.songs.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val currentSong by viewModel.currentSong.collectAsState()
    val isPlaying by viewModel.isPlaying.collectAsState()
    val progress by viewModel.progress.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        SearchBar(
            query = searchQuery,
            onQueryChange = viewModel::onSearchQueryChange
        )

        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            items(songs) { song ->
                var showMenu by remember { mutableStateOf(false) }

                SongItem(
                    song = song,
                    onClick = { viewModel.playSong(song) },
                    onMenuClick = { showMenu = true }
                )

                DropdownMenu(
                    expanded = showMenu,
                    onDismissRequest = { showMenu = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Play Next") },
                        onClick = {
                            viewModel.playNext(song)
                            showMenu = false
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Remove from Queue") },
                        onClick = {
                            viewModel.removeFromQueue(song)
                            showMenu = false
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Stop After This Song") },
                        onClick = {
                            viewModel.stopAfterSong(song)
                            showMenu = false
                        }
                    )
                }
            }
        }

        PlayerControls(
            currentSong = currentSong,
            isPlaying = isPlaying,
            progress = progress,
            duration = currentSong?.duration ?: 0L,
            onPlayPauseClick = viewModel::togglePlayPause,
            onPreviousClick = viewModel::playPrevious,
            onNextClick = viewModel::playNext,
            onSeekTo = viewModel::seekTo
        )
    }
}