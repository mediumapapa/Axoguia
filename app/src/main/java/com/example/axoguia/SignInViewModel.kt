package com.example.axoguia

import android.util.Log
import androidx.lifecycle.ViewModel // Importación añadida
import androidx.lifecycle.viewModelScope
import com.example.axoguia.core.AuthRepository
import kotlinx.coroutines.launch

class SignInViewModel : ViewModel() { // Herencia añadida

    private val repository = AuthRepository()

    fun requestSignUp(name: String, email: String, password: String) {
        viewModelScope.launch {
            val result = repository.requestSignUp(name, email, password)
            result?.let { user ->
                Log.i("Session", "Se ha creado el usuario ${user.uid}")
            } ?: run {
                Log.e("Error", "Hubo un error al crear al usuario")
            }
        }
    }
}