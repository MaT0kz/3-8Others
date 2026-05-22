package com.example.moviesapp.presentation.profile

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesapp.domain.repository.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ProfileUiState(
    val fullName: String = "",
    val avatarUri: String = "",
    val resumeUrl: String = "",
    val position: String = ""
)

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileRepository: ProfileRepository,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    private val _isDownloading = MutableStateFlow(false)
    val isDownloading: StateFlow<Boolean> = _isDownloading.asStateFlow()

    private val _downloadedFileUri = MutableStateFlow<Uri?>(null)
    val downloadedFileUri: StateFlow<Uri?> = _downloadedFileUri.asStateFlow()

    init {
        viewModelScope.launch {
            profileRepository.profile.collect { profile ->
                _uiState.value = ProfileUiState(
                    fullName = profile.fullName,
                    avatarUri = profile.avatarUri,
                    resumeUrl = profile.resumeUrl,
                    position = profile.position
                )
            }
        }
    }

    fun downloadResume(url: String) {
        viewModelScope.launch {
            _isDownloading.value = true
            _downloadedFileUri.value = null
            try {
                val extension = url.substringAfterLast('.', "").substringBefore('?').take(5)
                val fileName = "resume_${System.currentTimeMillis()}.$extension"
                val request = DownloadManager.Request(Uri.parse(url))
                    .setTitle("Резюме")
                    .setDescription("Скачивание...")
                    .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                    .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName)
                    .setAllowedOverMetered(true)
                    .setAllowedOverRoaming(true)

                val dm = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
                val downloadId = dm.enqueue(request)

                var done = false
                while (!done) {
                    delay(500)
                    val query = DownloadManager.Query().setFilterById(downloadId)
                    val cursor = dm.query(query)
                    if (cursor.moveToFirst()) {
                        val statusIdx = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)
                        when (cursor.getInt(statusIdx)) {
                            DownloadManager.STATUS_SUCCESSFUL -> {
                                // getUriForDownloadedFile возвращает content:// URI,
                                // безопасный для передачи другим приложениям (без FileUriExposedException)
                                _downloadedFileUri.value = dm.getUriForDownloadedFile(downloadId)
                                done = true
                            }
                            DownloadManager.STATUS_FAILED -> done = true
                        }
                    }
                    cursor.close()
                }
            } finally {
                _isDownloading.value = false
            }
        }
    }

    fun clearDownloadedUri() {
        _downloadedFileUri.value = null
    }
}
