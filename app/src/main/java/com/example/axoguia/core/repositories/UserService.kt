package com.example.axoguia.core.repositories

import com.example.axoguia.core.ResponseService
import com.example.axoguia.onboarding.registpersonal.model.UserProfile

interface UserService {

    suspend fun saveUserInfo(userProfile: UserProfile): ResponseService<Unit>
}