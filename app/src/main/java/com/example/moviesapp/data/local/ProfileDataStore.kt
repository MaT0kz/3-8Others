package com.example.moviesapp.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.moviesapp.domain.model.Profile
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.profileDataStore: DataStore<Preferences> by preferencesDataStore(name = "profile")

@Singleton
class ProfileDataStore @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val dataStore = context.profileDataStore

    companion object {
        val FULL_NAME_KEY = stringPreferencesKey("full_name")
        val AVATAR_URI_KEY = stringPreferencesKey("avatar_uri")
        val RESUME_URL_KEY = stringPreferencesKey("resume_url")
        val POSITION_KEY = stringPreferencesKey("position")
    }

    val profile: Flow<Profile> = dataStore.data.map { prefs ->
        Profile(
            fullName = prefs[FULL_NAME_KEY] ?: "",
            avatarUri = prefs[AVATAR_URI_KEY] ?: "",
            resumeUrl = prefs[RESUME_URL_KEY] ?: "",
            position = prefs[POSITION_KEY] ?: ""
        )
    }

    suspend fun updateProfile(profile: Profile) {
        dataStore.edit { prefs ->
            prefs[FULL_NAME_KEY] = profile.fullName
            prefs[AVATAR_URI_KEY] = profile.avatarUri
            prefs[RESUME_URL_KEY] = profile.resumeUrl
            prefs[POSITION_KEY] = profile.position
        }
    }
}
