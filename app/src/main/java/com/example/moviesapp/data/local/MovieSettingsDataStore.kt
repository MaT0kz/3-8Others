package com.example.moviesapp.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "movie_settings")

data class MovieFilters(
    val query: String = "star",
    val genre: String = "",
    val minRating: Int = 0,
    val yearFrom: String = ""
)

@Singleton
class MovieSettingsDataStore @Inject constructor(
    private val context: Context
) {
    private val dataStore = context.dataStore

    companion object {
        val QUERY_KEY = stringPreferencesKey("query")
        val GENRE_KEY = stringPreferencesKey("genre")
        val MIN_RATING_KEY = intPreferencesKey("min_rating")
        val YEAR_FROM_KEY = stringPreferencesKey("year_from")
    }

    val filters: Flow<MovieFilters> = dataStore.data.map { preferences ->
        MovieFilters(
            query = preferences[QUERY_KEY] ?: "star",
            genre = preferences[GENRE_KEY] ?: "",
            minRating = preferences[MIN_RATING_KEY] ?: 0,
            yearFrom = preferences[YEAR_FROM_KEY] ?: ""
        )
    }

    suspend fun updateFilters(filters: MovieFilters) {
        dataStore.edit { preferences ->
            preferences[QUERY_KEY] = filters.query
            preferences[GENRE_KEY] = filters.genre
            preferences[MIN_RATING_KEY] = filters.minRating
            preferences[YEAR_FROM_KEY] = filters.yearFrom
        }
    }

    suspend fun clearFilters() {
        dataStore.edit { preferences ->
            preferences[QUERY_KEY] = "star"
            preferences[GENRE_KEY] = ""
            preferences[MIN_RATING_KEY] = 0
            preferences[YEAR_FROM_KEY] = ""
        }
    }

    fun hasActiveFilters(): Flow<Boolean> = dataStore.data.map { preferences ->
        val genre = preferences[GENRE_KEY] ?: ""
        val minRating = preferences[MIN_RATING_KEY] ?: 0
        val yearFrom = preferences[YEAR_FROM_KEY] ?: ""
        genre.isNotEmpty() || minRating > 0 || yearFrom.isNotEmpty()
    }
}
