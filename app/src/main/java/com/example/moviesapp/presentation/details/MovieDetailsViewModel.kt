package com.example.moviesapp.presentation.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesapp.domain.mapper.toUi
import com.example.moviesapp.domain.model.Result
import com.example.moviesapp.domain.repository.FavoritesRepository
import com.example.moviesapp.domain.usecase.GetMovieByIdUseCase
import com.example.moviesapp.presentation.model.MovieUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class DetailsUiState {
 data object Loading : DetailsUiState()
 data class Success(val movie: MovieUi, val isFavorite: Boolean = false) : DetailsUiState()
 data class Error(val message: String) : DetailsUiState()
}

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
 private val getMovieByIdUseCase: GetMovieByIdUseCase,
 private val favoritesRepository: FavoritesRepository,
 savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow<DetailsUiState>(DetailsUiState.Loading)
    val uiState: StateFlow<DetailsUiState> = _uiState.asStateFlow()

    private var currentMovie: MovieUi? = null
    private var currentMovieId: String? = null

    init {
        val movieId = savedStateHandle.get<String>("movieId")
        movieId?.let { 
            currentMovieId = it
            loadMovie(it) 
        }
    }

    private fun loadMovie(id: String) {
        viewModelScope.launch {
            _uiState.value = DetailsUiState.Loading
            when (val result = getMovieByIdUseCase(id)) {
                is Result.Success -> {
                    val movie = result.data.toUi()
                    currentMovie = movie
                    
                    // Проверяем, есть ли в избранном
                    favoritesRepository.isFavorite(id).collect { isFavorite ->
                        currentMovie?.let { 
                            _uiState.value = DetailsUiState.Success(it, isFavorite)
                        }
                    }
                }

                is Result.Error -> {
                    _uiState.value =
                        DetailsUiState.Error(result.exception.message ?: "Unknown error")
                }

                is Result.Loading -> {
                    _uiState.value = DetailsUiState.Loading
                }
            }
        }
    }

    fun toggleFavorite() {
        viewModelScope.launch {
            val movie = currentMovie ?: return@launch
            val state = _uiState.value
            if (state is DetailsUiState.Success) {
                if (state.isFavorite) {
                    favoritesRepository.removeFromFavorites(movie.id)
                } else {
                    favoritesRepository.addToFavorites(movie)
                }
            }
        }
    }

    fun refresh(id: String) {
        loadMovie(id)
    }
}
