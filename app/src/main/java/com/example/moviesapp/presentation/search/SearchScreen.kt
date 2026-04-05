package com.example.moviesapp.presentation.search

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    onSettingsApplied: () -> Unit = {},
    viewModel: SearchViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "Настройки поиска",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 24.dp)
        )
        
        // Поисковый запрос
        OutlinedTextField(
            value = uiState.query,
            onValueChange = { viewModel.updateQuery(it) },
            label = { Text("Поисковый запрос") },
            placeholder = { Text("Например: star, matrix, batman") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            leadingIcon = {
                Icon(Icons.Default.Clear, contentDescription = null)
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Жанр
        OutlinedTextField(
            value = uiState.genre,
            onValueChange = { viewModel.updateGenre(it) },
            label = { Text("Жанр") },
            placeholder = { Text("Например: Comedy, Action, Drama") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Минимальная оценка
        Text(
            text = "Минимальная оценка: ${uiState.minRating}",
            style = MaterialTheme.typography.bodyLarge
        )

        Slider(
            value = uiState.minRating.toFloat(),
            onValueChange = { viewModel.updateMinRating(it.toInt()) },
            valueRange = 0f..10f,
            steps = 9,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Год выпуска (с)
        OutlinedTextField(
            value = uiState.yearFrom,
            onValueChange = { viewModel.updateYearFrom(it) },
            label = { Text("Год выпуска (с)") },
            placeholder = { Text("Например: 2020") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Кнопки
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedButton(
                onClick = { viewModel.clearSettings() },
                modifier = Modifier.weight(1f)
            ) {
                Icon(Icons.Default.Clear, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Сбросить")
            }

            Button(
                onClick = { viewModel.saveSettings(onSettingsApplied) },
                modifier = Modifier.weight(1f)
            ) {
                Icon(Icons.Default.Check, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Готово")
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Информация о текущих настройках
        val hasActiveFilters = uiState.genre.isNotEmpty() || 
            uiState.minRating > 0 || 
            uiState.yearFrom.isNotEmpty()

        if (hasActiveFilters) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Активные фильтры:",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    if (uiState.genre.isNotEmpty()) {
                        Text("• Жанр: ${uiState.genre}")
                    }
                    if (uiState.minRating > 0) {
                        Text("• Мин. оценка: ${uiState.minRating}")
                    }
                    if (uiState.yearFrom.isNotEmpty()) {
                        Text("• Год с: ${uiState.yearFrom}")
                    }
                }
            }
        }
    }
}

