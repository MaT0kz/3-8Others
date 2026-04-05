package com.example.moviesapp.data.model.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieResponse(
 val id: String,
 @SerialName("primaryTitle")
 val title: String? = null,
 @SerialName("originalTitle")
 val originalTitle: String? = null,
 @SerialName("primaryImage")
 val image: ImageResponse? = null,
 @SerialName("startYear")
 val year: Int? = null,
 val type: String? = null,
 val rating: RatingResponse? = null,
 val genres: List<String>? = null,
 val plot: String? = null,
 val directors: List<PersonResponse>? = null,
 val writers: List<PersonResponse>? = null,
 val stars: List<PersonResponse>? = null,
 @SerialName("runtimeSeconds")
 val runtimeSeconds: Int? = null,
 @SerialName("originCountries")
 val countries: List<CountryResponse>? = null
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
 val aggregateRating: Double? = null,
 @SerialName("voteCount")
 val voteCount: Int? = null
)

@Serializable
data class PersonResponse(
 val id: String? = null,
 @SerialName("displayName")
 val displayName: String? = null,
 @SerialName("primaryImage")
 val image: ImageResponse? = null
)

@Serializable
data class CountryResponse(
 val code: String? = null,
 val name: String? = null
)

@Serializable
data class MovieSearchResponse(
 val results: List<MovieResponse>? = null,
 val titles: List<MovieResponse>? = null,
 val errorMessage: String? = null
)

// Response для нового /titles endpoint
@Serializable
data class TitleSearchResponse(
 @SerialName("titles")
 val data: List<TitleData>? = null,
 val paging: PagingInfo? = null
)

@Serializable
data class TitleData(
 val id: String,
 @SerialName("primaryTitle")
 val title: String? = null,
 @SerialName("originalTitle")
 val originalTitle: String? = null,
 @SerialName("primaryImage")
 val image: ImageResponse? = null,
 @SerialName("startYear")
 val year: Int? = null,
 val type: String? = null,
 val rating: RatingResponse? = null,
 val genres: List<String>? = null,
 val plot: String? = null,
 @SerialName("runtimeSeconds")
 val runtimeSeconds: Int? = null,
 @SerialName("originCountries")
 val countries: List<String>? = null
)

@Serializable
data class PlotData(
 @SerialName("plotText")
 val plotText: PlotTextData? = null
)

@Serializable
data class PlotTextData(
 val plainText: String? = null,
 val htmlText: String? = null
)

@Serializable
data class PagingInfo(
 val nextPageToken: String? = null,
 val previousPageToken: String? = null
)
