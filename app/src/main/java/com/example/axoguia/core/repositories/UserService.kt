package com.example.axoguia.core.repositories

import com.example.axoguia.core.ResponseService
import com.example.axoguia.onboarding.registerpersonal.model.UserProfile

interface UserService {

    suspend fun saveUserInfo(userProfile: UserProfile): ResponseService<Unit>
    suspend fun getUserInfo(userId: String): ResponseService<UserProfile>
}
