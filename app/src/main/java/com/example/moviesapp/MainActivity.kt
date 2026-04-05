package com.example.moviesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.moviesapp.data.local.MovieSettingsDataStore
import com.example.moviesapp.di.SettingsBadgeCache
import com.example.moviesapp.presentation.navigation.AppNavigation
import com.example.moviesapp.ui.theme.MoviesAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var settingsBadgeCache: SettingsBadgeCache

    @Inject
    lateinit var settingsDataStore: MovieSettingsDataStore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MoviesAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    // Проверяем наличие сохранённых настроек при запуске
                    LaunchedEffect(Unit) {
                        val filters = settingsDataStore.filters.first()
                        val hasActiveSettings = filters.genre.isNotEmpty() ||
                            filters.minRating > 0 ||
                            filters.yearFrom.isNotEmpty()
                        settingsBadgeCache.setHasSettings(hasActiveSettings)
                    }

                    AppNavigation(
                        navController = navController,
                        settingsBadgeCache = settingsBadgeCache
                    )
                }
            }
        }
    }
}
