package com.example.moviesapp.domain.repository

import com.example.moviesapp.presentation.model.MovieUi
import kotlinx.coroutines.flow.Flow

interface FavoritesRepository {
    fun getAllFavorites(): Flow<List<MovieUi>>
    fun isFavorite(movieId: String): Flow<Boolean>
    suspend fun addToFavorites(movie: MovieUi)
    suspend fun removeFromFavorites(movieId: String)
}
