package com.example.moviesapp.domain.repository

import com.example.moviesapp.domain.model.Movie

interface MovieRepository {
    fun getMovies(): List<Movie>
    fun getMovieById(id: String): Movie?
}
