package com.example.myplayer.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myplayer.domain.model.Song
import com.example.myplayer.presentation.viewmodel.MusicViewModel

@Composable
fun SongItem(song: Song, viewModel: MusicViewModel = hiltViewModel()) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable {
                viewModel.playSong(song)
            }
    ) {
        // Заменить на Image
        Box(
            modifier = Modifier
                .size(64.dp)
                .clip(RoundedCornerShape(8.dp)) // Закруглённые углы
                .border(1.dp, Color.Gray, RoundedCornerShape(8.dp)) // Серая граница
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = song.title,
                style = TextStyle(
                    fontSize = 18.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            )
            Text(
                text = song.artist,
                style = TextStyle(
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            )

            if (song.hasLyrics) {
                Text(
                    text = "LYRICS",
                    style = TextStyle(
                        fontSize = 12.sp,
                        color = Color.Gray
                    ),
                    modifier = Modifier
                        .padding(top = 4.dp)
                        .background(Color.DarkGray)
                        .padding(4.dp) // Для эффекта тега
                )
            }
        }

        // Кнопка с "тремя точками"
        IconButton(onClick = { /* TODO: Handle menu click */ }) {
            Icon(Icons.Default.MoreVert, contentDescription = "More", tint = Color.White)
        }
    }
}