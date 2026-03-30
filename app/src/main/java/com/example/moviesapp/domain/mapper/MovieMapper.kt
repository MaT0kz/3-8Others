package com.example.moviesapp.domain.mapper

import com.example.moviesapp.data.model.api.MovieResponse
import com.example.moviesapp.domain.model.Movie
import com.example.moviesapp.domain.model.MovieType

fun MovieResponse.toDomain(): Movie {
 return Movie(
 id = id,
 type = when (type) {
 "movie" -> MovieType.MOVIE
 "tvSeries" -> MovieType.TV_SERIES
 else -> MovieType.UNKNOWN
 },
 title = title ?: "",
 originalTitle = originalTitle ?: title ?: "",
 imageUrl = image?.url ?: "",
 year = year,
 runtimeSeconds = runtimeSeconds,
 genres = genres ?: emptyList(),
 rating = rating?.aggregateRating ?: 0.0,
 voteCount = rating?.voteCount ?: 0,
 plot = plot ?: "",
 directors = directors?.mapNotNull { it.displayName } ?: emptyList(),
 stars = stars?.mapNotNull { it.displayName } ?: emptyList(),
 countries = countries?.mapNotNull { it.name } ?: emptyList()
 )
}

fun Movie.toUi(): com.example.moviesapp.presentation.model.MovieUi {
 return com.example.moviesapp.presentation.model.MovieUi(
 id = id,
 type = when (type) {
 MovieType.MOVIE -> "Фильм"
 MovieType.TV_SERIES -> "Сериал"
 MovieType.UNKNOWN -> "N/A"
 },
 title = title,
 originalTitle = if (originalTitle != title) originalTitle else null,
 imageUrl = imageUrl.ifEmpty { null },
 year = year,
 runtimeFormatted = runtime ?: "N/A",
 genres = genres.joinToString(", "),
 rating = String.format("%.1f", rating),
 voteCount = voteCount,
 plot = plot.ifEmpty { null },
 directors = directors.joinToString(", "),
 actors = stars.take(3).joinToString(", "),
 countries = countries.joinToString(", ")
 )
}
