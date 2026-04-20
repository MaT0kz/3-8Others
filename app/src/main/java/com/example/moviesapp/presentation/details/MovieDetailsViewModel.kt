package com.example.moviesapp.presentation.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesapp.domain.mapper.toUi
import com.example.moviesapp.domain.model.Result
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
    data class Success(val movie: MovieUi) : DetailsUiState()
    data class Error(val message: String) : DetailsUiState()
}

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val getMovieByIdUseCase: GetMovieByIdUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow<DetailsUiState>(DetailsUiState.Loading)
    val uiState: StateFlow<DetailsUiState> = _uiState.asStateFlow()

    init {
        val movieId = savedStateHandle.get<String>("movieId")
        movieId?.let { loadMovie(it) }
    }

    private fun loadMovie(id: String) {
        viewModelScope.launch {
            _uiState.value = DetailsUiState.Loading
            when (val result = getMovieByIdUseCase(id)) {
                is Result.Success -> {
                    _uiState.value = DetailsUiState.Success(result.data.toUi())
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

    fun refresh(id: String) {
        loadMovie(id)
    }
}
