package com.example.moviesapp.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesapp.domain.model.Profile
import com.example.moviesapp.domain.repository.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

data class EditProfileUiState(
    val fullName: String = "",
    val avatarUri: String = "",
    val resumeUrl: String = "",
    val position: String = ""
)

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val profileRepository: ProfileRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(EditProfileUiState())
    val uiState: StateFlow<EditProfileUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val profile = profileRepository.profile.first()
            _uiState.value = EditProfileUiState(
                fullName = profile.fullName,
                avatarUri = profile.avatarUri,
                resumeUrl = profile.resumeUrl,
                position = profile.position
            )
        }
    }

    fun updateFullName(name: String) {
        _uiState.value = _uiState.value.copy(fullName = name)
    }

    fun updateResumeUrl(url: String) {
        _uiState.value = _uiState.value.copy(resumeUrl = url)
    }

    fun updatePosition(position: String) {
        _uiState.value = _uiState.value.copy(position = position)
    }

    fun updateAvatarUri(uri: String) {
        _uiState.value = _uiState.value.copy(avatarUri = uri)
    }

    fun saveProfile(onComplete: () -> Unit) {
        viewModelScope.launch {
            val state = _uiState.value
            profileRepository.updateProfile(
                Profile(
                    fullName = state.fullName,
                    avatarUri = state.avatarUri,
                    resumeUrl = state.resumeUrl,
                    position = state.position
                )
            )
            onComplete()
        }
    }
}
