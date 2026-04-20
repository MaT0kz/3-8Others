package com.example.moviesapp.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavItem(
    val route: String,
    val icon: ImageVector,
    val title: String
)

val bottomNavItems = listOf(
    BottomNavItem(
        route = Screen.Movies.route,
        icon = Icons.Default.Movie,
        title = "Фильмы"
    ),
    BottomNavItem(
        route = Screen.Favorites.route,
        icon = Icons.Default.Favorite,
        title = "Избранное"
    ),
    BottomNavItem(
        route = Screen.Search.route,
        icon = Icons.Default.Search,
        title = "Поиск"
    )
)
