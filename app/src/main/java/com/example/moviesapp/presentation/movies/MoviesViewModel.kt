package com.example.moviesapp.presentation.movies

import androidx.lifecycle.ViewModel
import com.example.moviesapp.data.model.Movie
import com.example.moviesapp.data.repository.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MoviesViewModel : ViewModel() {
    
    private val repository = MovieRepository()
    
    private val _movies = MutableStateFlow<List<Movie>>(emptyList())
    val movies: StateFlow<List<Movie>> = _movies.asStateFlow()
    
    init {
        loadMovies()
    }
    
    private fun loadMovies() {
        _movies.value = repository.getMovies()
    }
}
