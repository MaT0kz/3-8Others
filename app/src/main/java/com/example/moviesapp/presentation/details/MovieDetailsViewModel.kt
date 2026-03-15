package com.example.moviesapp.presentation.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.moviesapp.data.model.Movie
import com.example.moviesapp.data.repository.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MovieDetailsViewModel(
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    
    private val repository = MovieRepository()
    
    private val _movie = MutableStateFlow<Movie?>(null)
    val movie: StateFlow<Movie?> = _movie.asStateFlow()
    
    init {
        val movieId = savedStateHandle.get<String>("movieId")
        movieId?.let { loadMovie(it) }
    }
    
    private fun loadMovie(id: String) {
        _movie.value = repository.getMovieById(id)
    }
}
