package com.example.moviesapp.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Movie(
    val id: String,
    val type: String,
    @SerialName("primaryTitle")
    val title: String,
    @SerialName("originalTitle")
    val originalTitle: String? = null,
    @SerialName("primaryImage")
    val image: Image? = null,
    @SerialName("startYear")
    val year: Int? = null,
    @SerialName("runtimeSeconds")
    val runtimeSeconds: Int? = null,
    val genres: List<String>? = null,
    val rating: Rating? = null,
    val plot: String? = null,
    val directors: List<Person>? = null,
    val writers: List<Person>? = null,
    val stars: List<Person>? = null,
    @SerialName("originCountries")
    val countries: List<Country>? = null,
    @SerialName("spokenLanguages")
    val languages: List<Language>? = null
) {
    val runtimeFormatted: String
        get() = runtimeSeconds?.let { seconds ->
            val hours = seconds / 3600
            val minutes = (seconds % 3600) / 60
            if (hours > 0) "${hours}ч ${minutes}мин" else "${minutes} мин"
        } ?: "N/A"
    
    val genresFormatted: String
        get() = genres?.joinToString(", ") ?: "N/A"
    
    val ratingFormatted: String
        get() = rating?.aggregateRating?.toString() ?: "N/A"
    
    val actorsFormatted: String
        get() = stars?.take(3)?.joinToString(", ") { it.displayName } ?: "N/A"
    
    val directorsFormatted: String
        get() = directors?.joinToString(", ") { it.displayName } ?: "N/A"
    
    val countriesFormatted: String
        get() = countries?.joinToString(", ") { it.name } ?: "N/A"
}

@Serializable
data class Image(
    val url: String,
    val width: Int? = null,
    val height: Int? = null
)

@Serializable
data class Rating(
    @SerialName("aggregateRating")
    val aggregateRating: Double,
    @SerialName("voteCount")
    val voteCount: Int
)

@Serializable
data class Person(
    val id: String,
    @SerialName("displayName")
    val displayName: String,
    @SerialName("primaryImage")
    val image: Image? = null,
    @SerialName("primaryProfessions")
    val professions: List<String>? = null
)

@Serializable
data class Country(
    val code: String,
    val name: String
)

@Serializable
data class Language(
    val code: String,
    val name: String
)

@Serializable
data class MovieSearchResponse(
    val titles: List<Movie>
)
