package com.example.myplayer.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PauseCircleFilled
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.PlayCircleFilled
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myplayer.domain.model.Song
import com.example.myplayer.presentation.viewmodel.MusicViewModel

@Composable
fun TrackSlider(
    value: Float,
    onValueChange: (newValue: Float) -> Unit,
    onValueChangeFinished: () -> Unit,
    songDuration: Float
) {
    Slider(
        value = value,
        onValueChange = {
            onValueChange(it)
        },
        onValueChangeFinished = {

            onValueChangeFinished()

        },
        valueRange = 0f..songDuration,
        colors = SliderDefaults.colors(
            thumbColor = Color.Black,
            activeTrackColor = Color.DarkGray,
            inactiveTrackColor = Color.Gray,
        )
    )
}

@Composable
fun PlayerControls(
    viewModel: MusicViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val currentPosition by viewModel.currentPosition.collectAsState()
    val isPlaying by viewModel.isPlaying.collectAsState()
    val currentSong by viewModel.currentSong.collectAsState()
    val duration by viewModel.duration.collectAsState()

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(8.dp)
    ) {
        currentSong?.let {
            Text(text = it.title, style = MaterialTheme.typography.titleMedium)
            Text(text = it.artist, style = MaterialTheme.typography.bodySmall)

            Spacer(modifier = Modifier.height(8.dp))
            TrackSlider(
                value = if (duration > 0) currentPosition / duration.toFloat() else 0f,
                onValueChange = { value ->
                    viewModel.seekTo((value * duration).toLong())
                },
                onValueChangeFinished = { /*TODO*/ },
                songDuration = it.duration.toFloat()
            )
        }
        Row(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { viewModel.previousSong() }) {
                Icon(Icons.Default.SkipPrevious, contentDescription = "Previous")
            }
            isPlaying.let {
                if (it) {
                    IconButton(onClick = { viewModel.pauseSong() }) {
                        Icon(
                            Icons.Default.PauseCircleFilled,
                            contentDescription = "Pause",
                            modifier = Modifier.size(48.dp)
                        )
                    }
                } else {
                    IconButton(onClick = { viewModel.resumeSong() }) {
                        Icon(
                            Icons.Default.PlayCircleFilled,
                            contentDescription = "Play",
                            modifier = Modifier.size(48.dp)
                        )
                    }
                }
            }
            IconButton(onClick = { viewModel.nextSong() }) {
                Icon(Icons.Default.SkipNext, contentDescription = "Next")
            }
        }

    }
}