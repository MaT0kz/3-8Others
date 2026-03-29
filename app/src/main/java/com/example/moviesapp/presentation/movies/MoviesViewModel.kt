package com.example.moviesapp.presentation.movies

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
class MoviesViewModel @Inject constructor(
 private val repository: MovieRepository
) : ViewModel() {

 private val _movies = MutableStateFlow<List<MovieUi>>(emptyList())
 val movies: StateFlow<List<MovieUi>> = _movies.asStateFlow()

 init {
 loadMovies()
 }

 private fun loadMovies() {
 viewModelScope.launch {
 _movies.value = repository.getMovies().map { it.toUi() }
 }
 }
}
