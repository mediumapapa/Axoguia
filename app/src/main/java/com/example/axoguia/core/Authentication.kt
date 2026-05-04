package com.example.axoguia.core

import com.google.firebase.auth.FirebaseUser

interface Authentication {
    suspend fun requestLogin(email: String, password: String): FirebaseUser? //con suspend es como indicamos que es corrutina
    suspend fun requestSignUp(name: String, email: String, password: String): ResponseService?
}