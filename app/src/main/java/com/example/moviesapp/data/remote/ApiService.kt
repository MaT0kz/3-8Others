package com.example.moviesapp.data.remote

import com.example.moviesapp.data.model.api.MovieResponse
import com.example.moviesapp.data.model.api.MovieSearchResponse
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Path

interface ApiService {

    @GET("search/titles")
    suspend fun searchMovies(
        @Query("query") query: String,
        @Query("limit") limit: Int = 10,
        @Query("page") page: Int = 1
    ): MovieSearchResponse

    @GET("titles/{id}")
    suspend fun getMovieById(
        @Path("id") id: String
    ): MovieResponse

    companion object {
        const val BASE_URL = "https://api.imdbapi.dev/"
    }
}
