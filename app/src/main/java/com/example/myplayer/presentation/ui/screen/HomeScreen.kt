package com.example.myplayer.presentation.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.myplayer.domain.model.Song
import com.example.myplayer.presentation.viewmodel.MusicViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, viewModel: MusicViewModel = hiltViewModel()) {
    val songs by viewModel.songs.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Все песни") })
        },
        content = { padding ->
            LazyColumn(contentPadding = padding) {
                items(songs) { song ->
                    SongItem(song)
                }
            }
        }
    )
}

@Composable
fun SongItem(song: Song) {
    ListItem(
        headlineContent = { Text(song.title) },
        supportingContent = { Text(song.artist) },
        modifier = Modifier.clickable { /* Обработка клика по песне */ }
    )
}