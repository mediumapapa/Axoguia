package com.example.axoguia.core.repositories

import com.example.axoguia.core.ResponseService
import com.example.axoguia.onboarding.registerpersonal.model.UserProfile
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class UserRepository : UserService {
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

    override suspend fun getUserInfo(userId: String): ResponseService<UserProfile> = withContext(
        Dispatchers.IO) {
        try {
            val document = userCollection.document(userId)
                .get()
                .await()

            if (document.exists()) {
                val userProfile = UserProfile(
                    id = document.getString("id").orEmpty(),
                    firstName = document.getString("firstName").orEmpty(),
                    lastName = document.getString("lastName").orEmpty(),
                    userName = document.getString("userName").orEmpty(),
                    phone = document.getString("phone").orEmpty(),
                    birthDate = document.getString("birthDate").orEmpty()
                )
                ResponseService.Success(userProfile)
            } else {
                ResponseService.Error("No se encontro la informacion del perfil")
            }
        } catch (e: Exception) {
            ResponseService.Error("No se pudo cargar el perfil: ${e.localizedMessage}")
        }
    }

}
