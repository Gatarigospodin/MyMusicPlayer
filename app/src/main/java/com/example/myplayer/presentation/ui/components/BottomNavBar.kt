package com.example.myplayer.presentation.ui.components

import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.myplayer.presentation.navigation.Screen

@Composable
fun BottomNavBar(navController: NavController) {
    val screens = listOf(
        Screen.Home,
        Screen.Search,
        Screen.Playlist
    )
    NavigationBar {
        val navBackStackEntry = navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry.value?.destination

        screens.forEach { screen ->
            NavigationBarItem(
                label = { Text(screen.title) },
                selected = currentDestination?.route == screen.route,
                icon = { /* Здесь можно добавить иконку */ }    ,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(Screen.Home.route) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}