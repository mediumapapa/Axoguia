package com.example.axoguia.core.repositories

import com.example.axoguia.core.ResponseService
import com.example.axoguia.onboarding.registpersonal.model.UserProfile
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class UserRepository {
    private val firestore = FirebaseFirestore.getInstance()
    private val userCollection = firestore.collection("users")

    override suspend fun saveUserInfo(userProfile: UserProfile): ResponseService<Unit> = withContext(
        Dispatchers.IO) {
        try {
            userCollection.document(userProfile.id)
                .set(userProfile)
                .await()
            ResponseService.Success(Unit)
        } catch (e: Exception) {
            ResponseService.Error("No se pudo crear el perfil: ${e.localizedMessage}")
        }
    }

}