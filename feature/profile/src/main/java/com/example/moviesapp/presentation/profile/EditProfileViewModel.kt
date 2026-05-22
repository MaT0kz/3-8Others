package com.example.moviesapp.presentation.profile

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesapp.domain.model.Profile
import com.example.moviesapp.domain.repository.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

data class EditProfileUiState(
    val fullName: String = "",
    val avatarUri: String = "",
    val resumeUrl: String = "",
    val position: String = "",
    val classTime: String = "",
    val classTimeError: Boolean = false
)

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val profileRepository: ProfileRepository,
    @ApplicationContext private val context: Context
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
                position = profile.position,
                classTime = profile.classTime
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

    fun updateClassTime(time: String) {
        val hasError = time.isNotEmpty() && !isValidTimeFormat(time)
        _uiState.value = _uiState.value.copy(
            classTime = time,
            classTimeError = hasError
        )
    }

    fun saveProfile(onComplete: () -> Unit) {
        val state = _uiState.value
        if (state.classTimeError) return
        viewModelScope.launch {
            profileRepository.updateProfile(
                Profile(
                    fullName = state.fullName,
                    avatarUri = state.avatarUri,
                    resumeUrl = state.resumeUrl,
                    position = state.position,
                    classTime = state.classTime
                )
            )
            if (state.classTime.isNotEmpty() && isValidTimeFormat(state.classTime)) {
                val parts = state.classTime.split(":")
                scheduleAlarm(context, parts[0].toInt(), parts[1].toInt(), state.fullName)
            } else {
                cancelAlarm(context)
            }
            onComplete()
        }
    }

    companion object {
        private val TIME_REGEX = Regex("^([0-1][0-9]|2[0-3]):[0-5][0-9]$")

        fun isValidTimeFormat(time: String): Boolean = TIME_REGEX.matches(time)

        fun formatTime(hour: Int, minute: Int): String =
            String.format(Locale.US, "%02d:%02d", hour, minute)

        private fun scheduleAlarm(context: Context, hour: Int, minute: Int, name: String) {
            val alarmManager = context.getSystemService(AlarmManager::class.java)
            val triggerAt = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, hour)
                set(Calendar.MINUTE, minute)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
                if (timeInMillis <= System.currentTimeMillis()) {
                    add(Calendar.DAY_OF_MONTH, 1)
                }
            }.timeInMillis

            val intent = Intent(context, NotificationReceiver::class.java).apply {
                putExtra(NotificationReceiver.EXTRA_NAME, name)
            }
            val pendingIntent = PendingIntent.getBroadcast(
                context, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && !alarmManager.canScheduleExactAlarms()) {
                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, triggerAt, pendingIntent)
            } else {
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, triggerAt, pendingIntent)
            }
        }

        private fun cancelAlarm(context: Context) {
            val alarmManager = context.getSystemService(AlarmManager::class.java)
            val intent = Intent(context, NotificationReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(
                context, 0, intent,
                PendingIntent.FLAG_NO_CREATE or PendingIntent.FLAG_IMMUTABLE
            ) ?: return
            alarmManager.cancel(pendingIntent)
        }
    }
}
