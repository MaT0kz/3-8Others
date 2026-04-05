package com.example.moviesapp.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesapp.data.local.MovieFilters
import com.example.moviesapp.data.local.MovieSettingsDataStore
import com.example.moviesapp.di.SettingsBadgeCache
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SearchUiState(
    val query: String = "star",
    val genre: String = "",
    val minRating: Int = 0,
    val yearFrom: String = "",
    val isLoading: Boolean = false
)

@HiltViewModel
class SearchViewModel @Inject constructor(
 private val settingsDataStore: MovieSettingsDataStore,
 private val badgeCache: SettingsBadgeCache
) : ViewModel() {

 private val _uiState = MutableStateFlow(SearchUiState())
 val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

 init {
 viewModelScope.launch {
 loadSettings()
 }
 }

 private suspend fun loadSettings() {
 _uiState.value = _uiState.value.copy(isLoading = true)
 val filters = settingsDataStore.filters.first()
 _uiState.value = SearchUiState(
 query = filters.query,
 genre = filters.genre,
 minRating = filters.minRating,
 yearFrom = filters.yearFrom,
 isLoading = false
 )
 }

    fun updateQuery(query: String) {
        _uiState.value = _uiState.value.copy(query = query)
    }

    fun updateGenre(genre: String) {
        _uiState.value = _uiState.value.copy(genre = genre)
    }

    fun updateMinRating(rating: Int) {
        _uiState.value = _uiState.value.copy(minRating = rating)
    }

    fun updateYearFrom(year: String) {
        _uiState.value = _uiState.value.copy(yearFrom = year)
    }

    fun saveSettings(onComplete: () -> Unit) {
        viewModelScope.launch {
            val state = _uiState.value
            val filters = MovieFilters(
                query = state.query.ifBlank { "star" },
                genre = state.genre,
                minRating = state.minRating,
                yearFrom = state.yearFrom
            )
            
            android.util.Log.d("SearchViewModel", "Saving filters - genre: '${filters.genre}', minRating: ${filters.minRating}, yearFrom: '${filters.yearFrom}'")
            
            settingsDataStore.updateFilters(filters)

            // Обновляем бейдж
            val hasActiveSettings = state.genre.isNotEmpty() || 
                state.minRating > 0 || 
                state.yearFrom.isNotEmpty()
            badgeCache.setHasSettings(hasActiveSettings)

            android.util.Log.d("SearchViewModel", "Settings saved, hasActiveSettings: $hasActiveSettings")
            
            // Небольшая задержка чтобы данные успели сохраниться
            kotlinx.coroutines.delay(100)
            onComplete()
        }
    }

    fun clearSettings() {
        viewModelScope.launch {
            settingsDataStore.clearFilters()
            _uiState.value = SearchUiState()
            badgeCache.clearSettings()
        }
    }
}
