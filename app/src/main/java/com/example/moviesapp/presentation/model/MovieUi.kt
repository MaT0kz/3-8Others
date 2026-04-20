package com.example.moviesapp.presentation.model

data class MovieUi(
    val id: String,
    val type: String,
    val title: String,
    val originalTitle: String?,
    val imageUrl: String?,
    val year: Int?,
    val runtimeFormatted: String,
    val genres: String,
    val rating: String,
    val voteCount: Int?,
    val plot: String?,
    val directors: String,
    val actors: String,
    val countries: String
)
