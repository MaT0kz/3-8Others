package com.example.moviesapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_movies")
data class FavoriteMovieEntity(
    @PrimaryKey
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
    val countries: String,
    val addedAt: Long = System.currentTimeMillis()
)
