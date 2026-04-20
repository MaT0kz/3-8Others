package com.example.moviesapp.presentation.movies

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesapp.domain.mapper.toUi
import com.example.moviesapp.domain.model.Result
import com.example.moviesapp.domain.usecase.GetMoviesUseCase
import com.example.moviesapp.presentation.model.MovieUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class MoviesUiState {
    data object Loading : MoviesUiState()
    data class Success(val movies: List<MovieUi>) : MoviesUiState()
    data class Error(val message: String) : MoviesUiState()
}

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val getMoviesUseCase: GetMoviesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<MoviesUiState>(MoviesUiState.Loading)
    val uiState: StateFlow<MoviesUiState> = _uiState.asStateFlow()

    init {
        loadMovies()
    }

    fun loadMovies(query: String = "star") {
        viewModelScope.launch {
            Log.d("MoviesViewModel", "Loading movies for query: $query")
            _uiState.value = MoviesUiState.Loading
            when (val result = getMoviesUseCase(query)) {
                is Result.Success -> {
                    Log.d("MoviesViewModel", "Success: ${result.data.size} movies")
                    _uiState.value = MoviesUiState.Success(result.data.map { it.toUi() })
                }

                is Result.Error -> {
                    Log.e("MoviesViewModel", "Error: ${result.exception.message}")
                    _uiState.value =
                        MoviesUiState.Error(result.exception.message ?: "Unknown error")
                }

                is Result.Loading -> {
                    _uiState.value = MoviesUiState.Loading
                }
            }
        }
    }

    fun searchMovies(query: String) {
        if (query.isNotBlank()) {
            Log.d("MoviesViewModel", "Searching: $query")
            loadMovies(query)
        }
    }
}
