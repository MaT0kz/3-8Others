package com.example.moviesapp.data.repository

import android.util.Log
import com.example.moviesapp.data.remote.ApiService
import com.example.moviesapp.domain.mapper.toDomain
import com.example.moviesapp.domain.model.Movie
import com.example.moviesapp.domain.repository.MovieRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : MovieRepository {

    // Используем новый endpoint с фильтрацией на стороне API
    override suspend fun searchMovies(query: String): List<Movie> {
        val response = apiService.searchTitles()
        Log.d("MovieRepository", "Title search response: ${response.data?.size} items")

        // Фильтруем по названию на клиенте (нестрогое вхождение)
        val movies = response.data?.map { it.toDomain() } ?: emptyList()

        if (query.isNotBlank()) {
            val lowerQuery = query.lowercase()
            return movies.filter { movie ->
                movie.title.lowercase().contains(lowerQuery) ||
                        (movie.originalTitle?.lowercase()?.contains(lowerQuery) == true)
            }
        }

        return movies
    }

    // Перегруженная функция для поиска с фильтрами
    override suspend fun searchMoviesWithFilters(
        query: String,
        genre: String?,
        minRating: Float?,
        startYear: Int?
    ): List<Movie> {
        // Подготавливаем параметры
        val genresList = genre?.let { listOf(it) }
        val minRatingValue = minRating?.let { if (it > 0) it else null }
        val startYearValue = startYear?.let { if (it > 0) it else null }

        Log.d(
            "MovieRepository",
            "Searching with filters - query: $query, genre: $genre, minRating: $minRatingValue, startYear: $startYearValue"
        )

        // /titles не поддерживает query, получаем все и фильтруем локально
        val response = apiService.searchTitles(
            genres = genresList,
            minAggregateRating = minRatingValue,
            startYear = startYearValue,
            sortBy = "SORT_BY_POPULARITY",
            sortOrder = "DESC"
        )

        Log.d("MovieRepository", "Title search response: ${response.data?.size} items")

        // Фильтруем по названию на клиенте (нестрогое вхождение)
        var movies = response.data?.map { it.toDomain() } ?: emptyList()

        if (query.isNotBlank()) {
            val lowerQuery = query.lowercase()
            movies = movies.filter { movie ->
                movie.title.lowercase().contains(lowerQuery) ||
                        (movie.originalTitle?.lowercase()?.contains(lowerQuery) == true)
            }
        }

        return movies
    }

    override suspend fun getMovieById(id: String): Movie? {
        val response = apiService.getMovieById(id)
        return response.toDomain()
    }
}