package com.example.moviesapp.presentation.navigation

sealed class Screen(val route: String) {
    object Movies : Screen("movies")
    object Favorites : Screen("favorites")
    object Search : Screen("search")
    object MovieDetails : Screen("movie/{movieId}") {
        fun createRoute(movieId: String) = "movie/$movieId"
    }
}
