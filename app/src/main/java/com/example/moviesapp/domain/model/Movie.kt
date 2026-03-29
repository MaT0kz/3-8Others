package com.example.moviesapp.domain.model

data class Movie(
 val id: String,
 val type: MovieType,
 val title: String,
 val originalTitle: String,
 val imageUrl: String,
 val year: Int?,
 val runtimeSeconds: Int?,
 val genres: List<String>,
 val rating: Double,
 val voteCount: Int,
 val plot: String,
 val directors: List<String>,
 val stars: List<String>,
 val countries: List<String>
)

enum class MovieType {
 MOVIE,
 TV_SERIES,
 UNKNOWN
}
