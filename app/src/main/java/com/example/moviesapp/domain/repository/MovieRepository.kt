package com.example.moviesapp.domain.repository

import com.example.moviesapp.domain.model.Movie

interface MovieRepository {
    suspend fun searchMovies(query: String): List<Movie>
    suspend fun searchMoviesWithFilters(
        query: String,
        genre: String?,
        minRating: Float?,
        startYear: Int?
    ): List<Movie>

    suspend fun getMovieById(id: String): Movie?
}
