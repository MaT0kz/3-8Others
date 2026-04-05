package com.example.moviesapp.di

import com.example.moviesapp.data.repository.FavoritesRepositoryImpl
import com.example.moviesapp.data.repository.MovieRepositoryImpl
import com.example.moviesapp.domain.repository.FavoritesRepository
import com.example.moviesapp.domain.repository.MovieRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

 @Binds
 @Singleton
 abstract fun bindMovieRepository(
 impl: MovieRepositoryImpl
 ): MovieRepository

 @Binds
 @Singleton
 abstract fun bindFavoritesRepository(
 impl: FavoritesRepositoryImpl
 ): FavoritesRepository
}
