package com.example.moviesapp.presentation.movies

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesapp.data.local.MovieSettingsDataStore
import com.example.moviesapp.domain.mapper.toUi
import com.example.moviesapp.domain.repository.MovieRepository
import com.example.moviesapp.domain.usecase.GetMoviesUseCase
import com.example.moviesapp.presentation.model.MovieUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

sealed class MoviesUiState {
    data object Loading : MoviesUiState()
    data class Success(val movies: List<MovieUi>) : MoviesUiState()
    data class Error(val message: String) : MoviesUiState()
}

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val getMoviesUseCase: GetMoviesUseCase,
    private val movieRepository: MovieRepository,
    private val settingsDataStore: MovieSettingsDataStore
) : ViewModel() {

    private val _uiState = MutableStateFlow<MoviesUiState>(MoviesUiState.Loading)
    val uiState: StateFlow<MoviesUiState> = _uiState.asStateFlow()

    init {
        loadMoviesWithFilters()
    }

    fun loadMoviesWithFilters() {
        viewModelScope.launch {
            val filters = settingsDataStore.filters.first()
            android.util.Log.d(
                "MoviesViewModel",
                "loadMoviesWithFilters - query: '${filters.query}', genre: '${filters.genre}', minRating: ${filters.minRating}, yearFrom: '${filters.yearFrom}'"
            )
            loadMovies(filters.query)
        }
    }

    fun loadMovies(query: String = "star") {
        viewModelScope.launch {
            Log.d("MoviesViewModel", "Loading movies for query: $query")
            _uiState.value = MoviesUiState.Loading

            // Получаем текущие фильтры
            val filters = settingsDataStore.filters.first()

            try {
                // Используем новый метод с фильтрацией на стороне API
                val movies = movieRepository.searchMoviesWithFilters(
                    query = query.ifBlank { "star" },
                    genre = filters.genre.ifBlank { null },
                    minRating = if (filters.minRating > 0) filters.minRating.toFloat() else null,
                    startYear = filters.yearFrom.toIntOrNull()
                )

                Log.d("MoviesViewModel", "Success: ${movies.size} movies")
                _uiState.value = MoviesUiState.Success(movies.take(10).map { it.toUi() })
            } catch (e: Exception) {
                val message = getErrorMessage(e)
                Log.e("MoviesViewModel", "Error: $message")
                _uiState.value = MoviesUiState.Error(message)
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

    fun searchMovies(query: String) {
        if (query.isNotBlank()) {
            Log.d("MoviesViewModel", "Searching: $query")
            loadMovies(query)
        }
    }

    fun refresh() {
        loadMoviesWithFilters()
    }
}
