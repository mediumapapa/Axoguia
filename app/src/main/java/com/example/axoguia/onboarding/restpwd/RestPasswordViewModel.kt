package com.example.axoguia.onboarding.restpwd

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.axoguia.core.ResponseService
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class RestPasswordViewModel : ViewModel() {

    private val auth = FirebaseAuth.getInstance()
    private val _resetState = MutableStateFlow<ResponseService<Unit>?>(null)
    val resetState: StateFlow<ResponseService<Unit>?> = _resetState.asStateFlow()

    fun validateEmail(email: String): String? {
        if (email.isBlank()) return "El correo es requerido"
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) return "Correo invalido"
        return null
    }

    fun sendResetEmail(email: String) {
        if (validateEmail(email) != null) return

        viewModelScope.launch {
            _resetState.value = ResponseService.Loading
            _resetState.value = try {
                auth.sendPasswordResetEmail(email).await()
                ResponseService.Success(Unit)
            } catch (e: Exception) {
                ResponseService.Error(e.localizedMessage ?: "No se pudo enviar el correo")
            }
        }
    }
}
