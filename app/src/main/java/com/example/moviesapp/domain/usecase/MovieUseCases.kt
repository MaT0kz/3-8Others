package com.example.moviesapp.domain.usecase

import com.example.moviesapp.domain.model.Movie
import com.example.moviesapp.domain.model.Result
import com.example.moviesapp.domain.repository.MovieRepository
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

class GetMoviesUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(query: String = "star"): Result<List<Movie>> {
        return try {
            val movies = repository.searchMovies(query)
            Result.Success(movies)
        } catch (e: Exception) {
            val message = getErrorMessage(e)
            Result.Error(Exception(message))
        }
    }
}

class GetMovieByIdUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(id: String): Result<Movie> {
        return try {
            val movie = repository.getMovieById(id)
            if (movie != null) {
                Result.Success(movie)
            } else {
                Result.Error(Exception("Movie not found"))
            }
        } catch (e: Exception) {
            val message = getErrorMessage(e)
            Result.Error(Exception(message))
        }
    }
}

private fun getErrorMessage(e: Exception): String {
    return when (e) {
        is UnknownHostException -> "Нет подключения к интернету"
        is SocketTimeoutException -> "Время ожидания истекло. Проверьте подключение"
        else -> e.message ?: "Произошла ошибка"
    }
}
