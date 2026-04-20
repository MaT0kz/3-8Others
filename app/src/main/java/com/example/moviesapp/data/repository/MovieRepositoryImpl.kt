package com.example.moviesapp.data.repository

import android.util.Log
import com.example.moviesapp.data.model.api.*
import com.example.moviesapp.data.remote.ApiService
import com.example.moviesapp.domain.mapper.toDomain
import com.example.moviesapp.domain.model.Movie
import com.example.moviesapp.domain.repository.MovieRepository
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : MovieRepository {

    override suspend fun searchMovies(query: String): List<Movie> {
        val response = apiService.searchMovies(query = query)
        Log.d("MovieRepository", "Response: $response")
        Log.d("MovieRepository", "Results: ${response.results}, Titles: ${response.titles}")
        val movies = response.results ?: response.titles
        return movies?.map { it.toDomain() } ?: emptyList()
    }

    override suspend fun getMovieById(id: String): Movie? {
        val response = apiService.getMovieById(id)
        return response.toDomain()
    }
}