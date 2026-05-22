package com.example.moviesapp.domain.repository

import com.example.moviesapp.domain.model.Profile
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    val profile: Flow<Profile>
    suspend fun updateProfile(profile: Profile)
}
