package com.example.axoguia.core

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class AuthRepository(): Authentication {
    val auth = FirebaseAuth.getInstance()
    val firestore = FirebaseFirestore.getInstance()
    override suspend fun requestLogin(
        email: String,
        password: String
    ): FirebaseUser? {
        return try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            result.user
        } catch (e: Exception) {
            Log.e("Error", "${e.printStackTrace()}")
            null
        }
    }
    override suspend fun requestSignUp(
        name: String,
        email: String,
        password: String,

    ): FirebaseUser? {
        return try {
            val result = auth.createUserWithEmailAndPassword(name, email).await()
            result.user
        } catch (e: Exception) {
            Log.e("Error", "\${e.printStackTrace()}")
            null
        }
    }
}