package com.example.myplayer

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.material3.Scaffold
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.myplayer.presentation.navigation.NavGraph
import com.example.myplayer.presentation.ui.components.BottomNavBar
import com.example.myplayer.presentation.ui.theme.MyPlayerTheme
import com.example.myplayer.presentation.viewmodel.MusicViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MusicViewModel>()

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            // Разрешение предоставлено, ничего не делаем, так как ViewModel уже запрашивает данные
        } else {
            // Разрешение не предоставлено, можно показать сообщение пользователю
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        createNotificationChannel()
        Log.d("MainActivity", "MainActivity started")
        checkAndRequestPermissions()
        try {
            setContent {
                MyPlayerTheme {
                   val navController = rememberNavController()
                    Scaffold(
                        bottomBar = { BottomNavBar(navController) }
                    ) { padding ->
                        NavGraph(navController)
                    }
                }
            }
        } catch (e: Exception) {
            Log.e("MainActivity", "Error in setContent: ${e.message}")
        }
    }

    private fun checkAndRequestPermissions() {
        val permission =
            Manifest.permission.READ_MEDIA_AUDIO // Замените на нужное вам разрешение

        when {
            ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED -> {
                // Разрешение уже предоставлено
            }
            shouldShowRequestPermissionRationale(permission) -> {
                // Покажите объяснение необходимости разрешения и запросите его
                requestPermissionLauncher.launch(permission)
            }
            else -> {
                // Запросите разрешение напрямую
                requestPermissionLauncher.launch(permission)
            }
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                "music_channel",
                "Music Playback",
                NotificationManager.IMPORTANCE_LOW
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(serviceChannel)
        }
    }
}