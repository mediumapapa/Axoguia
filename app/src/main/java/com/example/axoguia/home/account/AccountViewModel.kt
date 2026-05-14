package com.example.axoguia.home.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.axoguia.core.ResponseService
import com.example.axoguia.core.repositories.UserRepository
import com.example.axoguia.onboarding.registerpersonal.model.UserProfile
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class AccountUiState(
    val isLoading: Boolean = false,
    val userProfile: UserProfile? = null,
    val email: String = "",
    val error: String? = null
)

class AccountViewModel : ViewModel() {

    private val auth = FirebaseAuth.getInstance()
    private val repository = UserRepository()

    private val _uiState = MutableStateFlow(AccountUiState())
    val uiState: StateFlow<AccountUiState> = _uiState.asStateFlow()

    fun loadCurrentUser() {
        val currentUser = auth.currentUser

        if (currentUser == null) {
            _uiState.value = AccountUiState(error = "No hay una sesion iniciada")
            return
        }

        viewModelScope.launch {
            _uiState.value = AccountUiState(
                isLoading = true,
                email = currentUser.email.orEmpty()
            )

            when (val response = repository.getUserInfo(currentUser.uid)) {
                is ResponseService.Success -> {
                    _uiState.value = AccountUiState(
                        userProfile = response.data,
                        email = currentUser.email.orEmpty()
                    )
                }
                is ResponseService.Error -> {
                    _uiState.value = AccountUiState(
                        email = currentUser.email.orEmpty(),
                        error = response.error
                    )
                }
                is ResponseService.Loading -> Unit
            }
        }
    }
}
