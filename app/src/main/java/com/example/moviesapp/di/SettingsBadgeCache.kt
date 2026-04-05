package com.example.moviesapp.di

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Класс-кэш для хранения информации о необходимости показа бейджа.
 * Используется для индикации наличия настроек фильтрации.
 * Создаётся через DI и является общим для экрана списка и настроек.
 */
@Singleton
class SettingsBadgeCache @Inject constructor() {

    private val _hasSettings = MutableStateFlow(false)
    val hasSettings: StateFlow<Boolean> = _hasSettings.asStateFlow()

    fun setHasSettings(hasSettings: Boolean) {
        _hasSettings.value = hasSettings
    }

    fun clearSettings() {
        _hasSettings.value = false
    }
}
