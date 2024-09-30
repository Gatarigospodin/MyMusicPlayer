package com.example.myplayer.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myplayer.presentation.ui.screen.HomeScreen
import com.example.myplayer.presentation.ui.screen.PlaylistScreen
import com.example.myplayer.presentation.ui.screen.SearchScreen

sealed class Screen(val route: String, val title: String, val iconName: String) {
    object Home : Screen("home", "Дом", "Home")
    object Search : Screen("search", "Поиск", "Search")
    object Playlist : Screen("playlist", "Плейлисты", "LibraryMusic")
}

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) { HomeScreen(navController) }
        composable(Screen.Search.route) { SearchScreen(navController) }
        composable(Screen.Playlist.route) { PlaylistScreen(navController) }
    }
}