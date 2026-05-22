package com.example.moviesapp.data.repository

import com.example.moviesapp.data.local.ProfileDataStore
import com.example.moviesapp.domain.model.Profile
import com.example.moviesapp.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProfileRepositoryImpl @Inject constructor(
    private val profileDataStore: ProfileDataStore
) : ProfileRepository {

    override val profile: Flow<Profile> = profileDataStore.profile

    override suspend fun updateProfile(profile: Profile) {
        profileDataStore.updateProfile(profile)
    }
}
