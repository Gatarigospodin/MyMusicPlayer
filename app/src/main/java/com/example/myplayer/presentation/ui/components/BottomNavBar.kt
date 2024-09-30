package com.example.myplayer.presentation.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Help
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LibraryMusic
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.myplayer.presentation.navigation.Screen

fun getIconByName(iconName: String): ImageVector {
    return when (iconName) {
        "Home" -> Icons.Default.Home
        "Search" -> Icons.Default.Search
        "LibraryMusic" -> Icons.Default.LibraryMusic
        else -> Icons.Default.Cancel // Фолбек на случай, если иконка не найдена
    }
}

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
                icon = { Icon(imageVector = getIconByName(screen.iconName), contentDescription = null) }    ,
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