package com.example.moviesapp.di

import com.example.moviesapp.data.remote.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

 @Provides
 @Singleton
 fun provideOkHttpClient(): OkHttpClient {
 val loggingInterceptor = HttpLoggingInterceptor().apply {
 level = HttpLoggingInterceptor.Level.BODY
 }

 return OkHttpClient.Builder()
 .addInterceptor(loggingInterceptor)
 .connectTimeout(30, TimeUnit.SECONDS)
 .readTimeout(30, TimeUnit.SECONDS)
 .writeTimeout(30, TimeUnit.SECONDS)
 .build()
 }

 @Provides
 @Singleton
 fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
 val json = Json {
 ignoreUnknownKeys = true
 isLenient = true
 }

 return Retrofit.Builder()
 .baseUrl(ApiService.BASE_URL)
 .client(okHttpClient)
 .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
 .build()
 }

 @Provides
 @Singleton
 fun provideApiService(retrofit: Retrofit): ApiService {
 return retrofit.create(ApiService::class.java)
 }
}
