package com.example.moviesapp.presentation.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesapp.domain.mapper.toUi
import com.example.moviesapp.domain.repository.MovieRepository
import com.example.moviesapp.presentation.model.MovieUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
 private val repository: MovieRepository,
 savedStateHandle: SavedStateHandle
) : ViewModel() {

 private val _movie = MutableStateFlow<MovieUi?>(null)
 val movie: StateFlow<MovieUi?> = _movie.asStateFlow()

 init {
 val movieId = savedStateHandle.get<String>("movieId")
 movieId?.let { loadMovie(it) }
 }

 private fun loadMovie(id: String) {
 viewModelScope.launch {
 _movie.value = repository.getMovieById(id)?.toUi()
 }
 }
}
