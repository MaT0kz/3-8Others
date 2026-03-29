package com.example.moviesapp.presentation.details

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailsScreen(
 onBackClick: () -> Unit,
 viewModel: MovieDetailsViewModel = hiltViewModel()
) {
 val movie by viewModel.movie.collectAsState()

 movie?.let { film ->
 Scaffold(
 topBar = {
 TopAppBar(
 title = {
 Text(
 text = film.title,
 maxLines =1,
 overflow = TextOverflow.Ellipsis
 )
 },
 navigationIcon = {
 IconButton(onClick = onBackClick) {
 Icon(
 imageVector = Icons.AutoMirrored.Filled.ArrowBack,
 contentDescription = "Назад"
 )
 }
 },
 colors = TopAppBarDefaults.topAppBarColors(
 containerColor = MaterialTheme.colorScheme.primaryContainer,
 titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
 )
 )
 }
 ) { paddingValues ->
 Column(
 modifier = Modifier
 .fillMaxSize()
 .padding(paddingValues)
 .verticalScroll(rememberScrollState())
 ) {
 ConstraintLayout(
 modifier = Modifier
 .fillMaxWidth()
 .height(300.dp)
 ) {
 val (poster, ratingCard, typeChip) = createRefs()

 AsyncImage(
 model = film.imageUrl,
 contentDescription = "Постер ${film.title}",
 modifier = Modifier
 .constrainAs(poster) {
 top.linkTo(parent.top)
 start.linkTo(parent.start)
 end.linkTo(parent.end)
 bottom.linkTo(parent.bottom)
 }
 .width(200.dp)
 .height(280.dp)
 .clip(RoundedCornerShape(12.dp)),
 contentScale = ContentScale.Crop
 )

 Card(
 modifier = Modifier
 .constrainAs(ratingCard) {
 end.linkTo(poster.end, margin = (-16).dp)
 bottom.linkTo(poster.bottom)
 },
 colors = CardDefaults.cardColors(
 containerColor = MaterialTheme.colorScheme.primary
 )
 ) {
 Text(
 text = "⭐ ${film.rating}",
 style = MaterialTheme.typography.titleMedium,
 color = MaterialTheme.colorScheme.onPrimary,
 modifier = Modifier.padding(horizontal =12.dp, vertical =6.dp)
 )
 }

 AssistChip(
 onClick = {},
 label = {
 Text(
 text = film.type,
 style = MaterialTheme.typography.labelMedium
 )
 },
 modifier = Modifier.constrainAs(typeChip) {
 start.linkTo(poster.start, margin = (-8).dp)
 top.linkTo(poster.top)
 },
 colors = AssistChipDefaults.assistChipColors(
 containerColor = MaterialTheme.colorScheme.secondaryContainer
 )
 )
 }

 Column(
 modifier = Modifier
 .fillMaxWidth()
 .padding(16.dp)
 ) {
 Text(
 text = film.title,
 style = MaterialTheme.typography.headlineMedium
 )

 film.originalTitle?.let { original ->
 Text(
 text = original,
 style = MaterialTheme.typography.bodyMedium,
 color = MaterialTheme.colorScheme.onSurfaceVariant
 )
 }

 Spacer(modifier = Modifier.height(8.dp))

 Row(
 verticalAlignment = Alignment.CenterVertically
 ) {
 film.year?.let {
 AssistChip(
 onClick = {},
 label = { Text(it.toString()) }
 )
 Spacer(modifier = Modifier.width(8.dp))
 }
 AssistChip(
 onClick = {},
 label = { Text(film.runtimeFormatted) }
 )
 Spacer(modifier = Modifier.width(8.dp))
 if (film.countries.isNotEmpty()) {
 AssistChip(
 onClick = {},
 label = { Text(film.countries) }
 )
 }
 }

 Spacer(modifier = Modifier.height(16.dp))

 Text(
 text = "Жанр",
 style = MaterialTheme.typography.labelLarge,
 color = MaterialTheme.colorScheme.primary
 )
 Text(
 text = film.genres.ifEmpty { "N/A" },
 style = MaterialTheme.typography.bodyLarge
 )

 if (film.directors.isNotEmpty()) {
 Spacer(modifier = Modifier.height(16.dp))

 Text(
 text = "Режиссёр",
 style = MaterialTheme.typography.labelLarge,
 color = MaterialTheme.colorScheme.primary
 )
 Text(
 text = film.directors,
 style = MaterialTheme.typography.bodyLarge
 )
 }

 if (film.actors.isNotEmpty()) {
 Spacer(modifier = Modifier.height(16.dp))

 Text(
 text = "В ролях",
 style = MaterialTheme.typography.labelLarge,
 color = MaterialTheme.colorScheme.primary
 )
 Text(
 text = film.actors,
 style = MaterialTheme.typography.bodyLarge
 )
 }

 film.voteCount?.let { votes ->
 Spacer(modifier = Modifier.height(8.dp))

 Text(
 text = "$votes голосов",
 style = MaterialTheme.typography.bodySmall,
 color = MaterialTheme.colorScheme.onSurfaceVariant
 )
 }

 film.plot?.let {
 Spacer(modifier = Modifier.height(16.dp))

 HorizontalDivider()

 Spacer(modifier = Modifier.height(16.dp))

 Text(
 text = "Описание",
 style = MaterialTheme.typography.labelLarge,
 color = MaterialTheme.colorScheme.primary
 )
 Spacer(modifier = Modifier.height(8.dp))
 Text(
 text = it,
 style = MaterialTheme.typography.bodyLarge
 )
 }

 Spacer(modifier = Modifier.height(24.dp))

 Button(
 onClick = { },
 modifier = Modifier.fillMaxWidth()
 ) {
 Icon(
 imageVector = Icons.Default.Favorite,
 contentDescription = null,
 modifier = Modifier.size(18.dp)
 )
 Spacer(modifier = Modifier.width(8.dp))
 Text("Добавить в избранное")
 }
 }
 }
 }
 }
}
