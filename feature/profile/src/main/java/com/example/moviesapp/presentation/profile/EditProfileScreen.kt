package com.example.moviesapp.presentation.profile

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import java.io.File
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(
    onDone: () -> Unit,
    onBackClick: () -> Unit,
    viewModel: EditProfileViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    var timeFieldValue by remember { mutableStateOf(TextFieldValue("")) }

    LaunchedEffect(uiState.classTime) {
        if (timeFieldValue.text != uiState.classTime) {
            timeFieldValue = TextFieldValue(
                text = uiState.classTime,
                selection = TextRange(uiState.classTime.length)
            )
        }
    }

    var showPhotoSourceSheet by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }
    var timePickerHour by remember { mutableStateOf(Calendar.getInstance().get(Calendar.HOUR_OF_DAY)) }
    var timePickerMinute by remember { mutableStateOf(Calendar.getInstance().get(Calendar.MINUTE)) }
    val bottomSheetState = rememberModalBottomSheetState()

    var cameraImageFile by remember { mutableStateOf<File?>(null) }
    var pendingCameraLaunch by remember { mutableStateOf(false) }

    val storagePermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        Manifest.permission.READ_MEDIA_IMAGES
    } else {
        Manifest.permission.READ_EXTERNAL_STORAGE
    }

    val notificationPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { /* no action if denied — alarm still fires, receiver checks */ }

    val storagePermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (!isGranted) {
            onBackClick()
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val hasNotif = ContextCompat.checkSelfPermission(
                context, Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
            if (!hasNotif) notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) pendingCameraLaunch = true
    }

    val cameraLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            cameraImageFile?.let { file ->
                viewModel.updateAvatarUri(Uri.fromFile(file).toString())
            }
        }
    }

    val photoPickerLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        uri?.let {
            try {
                context.contentResolver.takePersistableUriPermission(
                    it, android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
            } catch (_: SecurityException) { }
            viewModel.updateAvatarUri(it.toString())
        }
    }

    val galleryLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            try {
                context.contentResolver.takePersistableUriPermission(
                    it, android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
            } catch (_: SecurityException) { }
            viewModel.updateAvatarUri(it.toString())
        }
    }

    LaunchedEffect(Unit) {
        val hasStorage = ContextCompat.checkSelfPermission(
            context, storagePermission
        ) == PackageManager.PERMISSION_GRANTED
        if (!hasStorage) {
            storagePermissionLauncher.launch(storagePermission)
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val hasNotif = ContextCompat.checkSelfPermission(
                context, Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
            if (!hasNotif) notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    LaunchedEffect(pendingCameraLaunch) {
        if (pendingCameraLaunch) {
            pendingCameraLaunch = false
            val file = createCameraImageFile(context)
            cameraImageFile = file
            val uri = FileProvider.getUriForFile(
                context, "${context.packageName}.fileprovider", file
            )
            cameraLauncher.launch(uri)
        }
    }

    fun launchGallery() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            photoPickerLauncher.launch(
                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
            )
        } else {
            galleryLauncher.launch("image/*")
        }
    }

    fun onCameraSelected() {
        val hasCameraPermission = ContextCompat.checkSelfPermission(
            context, Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
        if (hasCameraPermission) pendingCameraLaunch = true
        else cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Редактирование профиля") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Назад")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .clickable { showPhotoSourceSheet = true },
                contentAlignment = Alignment.Center
            ) {
                if (uiState.avatarUri.isNotEmpty()) {
                    AsyncImage(
                        model = Uri.parse(uiState.avatarUri),
                        contentDescription = "Аватар",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        shape = CircleShape,
                        color = MaterialTheme.colorScheme.surfaceVariant
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = null,
                                modifier = Modifier.size(72.dp),
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }

            Text(
                text = "Нажмите на фото для изменения",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            OutlinedTextField(
                value = uiState.fullName,
                onValueChange = viewModel::updateFullName,
                label = { Text("ФИО") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            OutlinedTextField(
                value = uiState.position,
                onValueChange = viewModel::updatePosition,
                label = { Text("Должность") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            OutlinedTextField(
                value = uiState.resumeUrl,
                onValueChange = viewModel::updateResumeUrl,
                label = { Text("Ссылка на резюме/портфолио") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Column(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = timeFieldValue,
                    onValueChange = { tfv ->
                        val digits = tfv.text.filter { it.isDigit() }.take(4)
                        val formatted = if (digits.length <= 2) digits
                                        else "${digits.take(2)}:${digits.drop(2)}"
                        timeFieldValue = TextFieldValue(
                            text = formatted,
                            selection = TextRange(formatted.length)
                        )
                        viewModel.updateClassTime(formatted)
                    },
                    label = { Text("Время любимой пары") },
                    placeholder = { Text("ЧЧ:ММ") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    isError = uiState.classTimeError,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    trailingIcon = {
                        IconButton(onClick = {
                            val time = uiState.classTime
                            if (EditProfileViewModel.isValidTimeFormat(time)) {
                                val parts = time.split(":")
                                timePickerHour = parts[0].toInt()
                                timePickerMinute = parts[1].toInt()
                            } else {
                                val cal = Calendar.getInstance()
                                timePickerHour = cal.get(Calendar.HOUR_OF_DAY)
                                timePickerMinute = cal.get(Calendar.MINUTE)
                            }
                            showTimePicker = true
                        }) {
                            Icon(
                                imageVector = Icons.Default.AccessTime,
                                contentDescription = "Выбрать время"
                            )
                        }
                    }
                )
                if (uiState.classTimeError) {
                    Text(
                        text = "Некорректный формат времени",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                    )
                }
            }

            Button(
                onClick = { viewModel.saveProfile(onDone) },
                modifier = Modifier.fillMaxWidth(),
                enabled = !uiState.classTimeError
            ) {
                Text("Готово")
            }
        }
    }

    if (showTimePicker) {
        val timePickerState = rememberTimePickerState(
            initialHour = timePickerHour,
            initialMinute = timePickerMinute,
            is24Hour = true
        )
        AlertDialog(
            onDismissRequest = { showTimePicker = false },
            text = { TimePicker(state = timePickerState) },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.updateClassTime(
                        EditProfileViewModel.formatTime(timePickerState.hour, timePickerState.minute)
                    )
                    showTimePicker = false
                }) { Text("OK") }
            },
            dismissButton = {
                TextButton(onClick = { showTimePicker = false }) { Text("Отмена") }
            }
        )
    }

    if (showPhotoSourceSheet) {
        ModalBottomSheet(
            onDismissRequest = { showPhotoSourceSheet = false },
            sheetState = bottomSheetState
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 40.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Выбрать фото",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                OutlinedButton(
                    onClick = {
                        showPhotoSourceSheet = false
                        launchGallery()
                    },
                    modifier = Modifier.fillMaxWidth()
                ) { Text("Галерея") }

                OutlinedButton(
                    onClick = {
                        showPhotoSourceSheet = false
                        onCameraSelected()
                    },
                    modifier = Modifier.fillMaxWidth()
                ) { Text("Камера") }
            }
        }
    }
}

private fun createCameraImageFile(context: Context): File {
    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
    val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        ?: context.cacheDir
    return File.createTempFile("JPEG_${timeStamp}_", ".jpg", storageDir)
}
