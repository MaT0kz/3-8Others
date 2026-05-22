package com.example.moviesapp.data.repository

import com.example.moviesapp.data.local.FavoriteMovieDao
import com.example.moviesapp.data.local.FavoriteMovieEntity
import com.example.moviesapp.domain.repository.FavoritesRepository
import com.example.moviesapp.presentation.model.MovieUi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavoritesRepositoryImpl @Inject constructor(
    private val favoriteMovieDao: FavoriteMovieDao
) : FavoritesRepository {

    override fun getAllFavorites(): Flow<List<MovieUi>> {
        return favoriteMovieDao.getAllFavorites().map { entities ->
            entities.map { it.toUi() }
        }
    }

    override fun isFavorite(movieId: String): Flow<Boolean> {
        return favoriteMovieDao.isFavorite(movieId)
    }

    override suspend fun addToFavorites(movie: MovieUi) {
        favoriteMovieDao.insertFavorite(movie.toEntity())
    }

    override suspend fun removeFromFavorites(movieId: String) {
        favoriteMovieDao.deleteFavoriteById(movieId)
    }

    private fun FavoriteMovieEntity.toUi(): MovieUi {
        return MovieUi(
            id = id,
            type = type,
            title = title,
            originalTitle = originalTitle,
            imageUrl = imageUrl,
            year = year,
            runtimeFormatted = runtimeFormatted,
            genres = genres,
            rating = rating,
            voteCount = voteCount,
            plot = plot,
            directors = directors,
            actors = actors,
            countries = countries
        )
    }

    private fun MovieUi.toEntity(): FavoriteMovieEntity {
        return FavoriteMovieEntity(
            id = id,
            type = type,
            title = title,
            originalTitle = originalTitle,
            imageUrl = imageUrl,
            year = year,
            runtimeFormatted = runtimeFormatted,
            genres = genres,
            rating = rating,
            voteCount = voteCount,
            plot = plot,
            directors = directors,
            actors = actors,
            countries = countries
        )
    }
}
