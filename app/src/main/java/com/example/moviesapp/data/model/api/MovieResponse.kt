package com.example.moviesapp.data.model.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieResponse(
 val id: String,
 val type: String,
 @SerialName("primaryTitle")
 val title: String,
 @SerialName("originalTitle")
 val originalTitle: String? = null,
 @SerialName("primaryImage")
 val image: ImageResponse? = null,
 @SerialName("startYear")
 val year: Int? = null,
 @SerialName("runtimeSeconds")
 val runtimeSeconds: Int? = null,
 val genres: List<String>? = null,
 val rating: RatingResponse? = null,
 val plot: String? = null,
 val directors: List<PersonResponse>? = null,
 val writers: List<PersonResponse>? = null,
 val stars: List<PersonResponse>? = null,
 @SerialName("originCountries")
 val countries: List<CountryResponse>? = null,
 @SerialName("spokenLanguages")
 val languages: List<LanguageResponse>? = null
)

@Serializable
data class ImageResponse(
 val url: String,
 val width: Int? = null,
 val height: Int? = null
)

@Serializable
data class RatingResponse(
 @SerialName("aggregateRating")
 val aggregateRating: Double,
 @SerialName("voteCount")
 val voteCount: Int
)

@Serializable
data class PersonResponse(
 val id: String,
 @SerialName("displayName")
 val displayName: String,
 @SerialName("primaryImage")
 val image: ImageResponse? = null,
 @SerialName("primaryProfessions")
 val professions: List<String>? = null
)

@Serializable
data class CountryResponse(
 val code: String,
 val name: String
)

@Serializable
data class LanguageResponse(
 val code: String,
 val name: String
)

@Serializable
data class MovieSearchResponse(
 val titles: List<MovieResponse>
)
