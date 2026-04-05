package com.example.moviesapp.presentation.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.NavType
import com.example.moviesapp.di.SettingsBadgeCache
import com.example.moviesapp.presentation.details.MovieDetailsScreen
import com.example.moviesapp.presentation.favorites.FavoritesScreen
import com.example.moviesapp.presentation.movies.MoviesScreen
import com.example.moviesapp.presentation.search.SearchScreen

@Composable
fun AppNavigation(
    navController: NavHostController,
    settingsBadgeCache: SettingsBadgeCache
) {
    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                navController = navController,
                settingsBadgeCache = settingsBadgeCache
            )
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screen.Search.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(
                route = Screen.Search.route
            ) {
                MoviesScreen(
                    onMovieClick = { movieId ->
                        navController.navigate(Screen.MovieDetails.createRoute(movieId))
                    }
                )
            }

            composable(
                route = Screen.Settings.route
            ) {
                SearchScreen(
                    onSettingsApplied = {
                        navController.navigate(Screen.Search.route) {
                            popUpTo(Screen.Search.route) {
                                inclusive = true
                            }
                            launchSingleTop = true
                        }
                    }
                )
            }

            composable(
                route = Screen.Favorites.route
            ) {
                FavoritesScreen()
            }

            composable(
                route = Screen.MovieDetails.route,
                arguments = listOf(
                    navArgument("movieId") { type = NavType.StringType }
                ),
                enterTransition = {
                    slideIntoContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Start,
                        animationSpec = tween(300)
                    )
                },
                exitTransition = {
                    slideOutOfContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.End,
                        animationSpec = tween(300)
                    )
                },
                popEnterTransition = {
                    slideIntoContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.End,
                        animationSpec = tween(300)
                    )
                },
                popExitTransition = {
                    slideOutOfContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Start,
                        animationSpec = tween(300)
                    )
                }
            ) {
                MovieDetailsScreen(
                    onBackClick = { navController.popBackStack() }
                )
            }
        }
    }
}

@Composable
private fun BottomNavigationBar(
    navController: NavHostController,
    settingsBadgeCache: SettingsBadgeCache
) {
    val hasSettings by settingsBadgeCache.hasSettings.collectAsState()

    NavigationBar {
        bottomNavItems.forEach { item ->
            NavigationBarItem(
                icon = { 
                    if (item.route == Screen.Settings.route && hasSettings) {
                        BadgedBox(
                            badge = {
                                Badge(
                                    containerColor = MaterialTheme.colorScheme.error
                                )
                            }
                        ) {
                            Icon(item.icon, contentDescription = item.title)
                        }
                    } else {
                        Icon(item.icon, contentDescription = item.title)
                    }
                },
                label = { Text(item.title) },
                selected = false,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(Screen.Search.route) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}
