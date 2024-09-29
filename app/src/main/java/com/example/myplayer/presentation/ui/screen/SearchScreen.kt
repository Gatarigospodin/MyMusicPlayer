package com.example.myplayer.presentation.ui.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Поиск") })
        },
        content = { padding ->
            TextField(
                value = "",
                onValueChange = {},
                placeholder = { Text("Введите текст") },
                modifier = Modifier.padding(padding)
            )
        }
    )
}