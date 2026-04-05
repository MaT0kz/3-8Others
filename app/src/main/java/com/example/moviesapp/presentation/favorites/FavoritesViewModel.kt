package com.example.moviesapp.presentation.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesapp.domain.repository.FavoritesRepository
import com.example.moviesapp.presentation.model.MovieUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class FavoritesUiState {
 data object Loading : FavoritesUiState()
 data class Success(val favorites: List<MovieUi>) : FavoritesUiState()
 data class Error(val message: String) : FavoritesUiState()
}

@HiltViewModel
class FavoritesViewModel @Inject constructor(
 private val favoritesRepository: FavoritesRepository
) : ViewModel() {

 private val _uiState = MutableStateFlow<FavoritesUiState>(FavoritesUiState.Loading)
 val uiState: StateFlow<FavoritesUiState> = _uiState.asStateFlow()

 init {
 loadFavorites()
 }

 private fun loadFavorites() {
 viewModelScope.launch {
 favoritesRepository.getAllFavorites().collect { favorites ->
 _uiState.value = FavoritesUiState.Success(favorites)
 }
 }
 }

 fun removeFromFavorites(movieId: String) {
 viewModelScope.launch {
 favoritesRepository.removeFromFavorites(movieId)
 }
 }
}
