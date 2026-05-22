package com.example.moviesapp.data.remote

import com.example.moviesapp.data.model.api.MovieResponse
import com.example.moviesapp.data.model.api.MovieSearchResponse
import com.example.moviesapp.data.model.api.TitleSearchResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("search/titles")
    suspend fun searchMovies(
        @Query("query") query: String,
        @Query("limit") limit: Int = 10,
        @Query("page") page: Int = 1
    ): MovieSearchResponse

    // Новый endpoint с фильтрацией на стороне API
    @GET("titles")
    suspend fun searchTitles(
        @Query("genres") genres: List<String>? = null,
        @Query("startYear") startYear: Int? = null,
        @Query("endYear") endYear: Int? = null,
        @Query("minAggregateRating") minAggregateRating: Float? = null,
        @Query("maxAggregateRating") maxAggregateRating: Float? = null,
        @Query("minVoteCount") minVoteCount: Int? = null,
        @Query("sortBy") sortBy: String = "SORT_BY_POPULARITY",
        @Query("sortOrder") sortOrder: String = "DESC",
        @Query("pageToken") pageToken: String? = null
    ): TitleSearchResponse

    @GET("titles/{id}")
    suspend fun getMovieById(
        @Path("id") id: String
    ): MovieResponse

    companion object {
        const val BASE_URL = "https://api.imdbapi.dev/"
    }
}
