package com.example.axoguia.home.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.axoguia.databinding.FragmentAccountBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class AccountFragment : Fragment() {

    private var _binding: FragmentAccountBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<AccountViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeState()
        viewModel.loadCurrentUser()
    }

    private fun observeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    binding.loadingPb.visibility = if (state.isLoading) View.VISIBLE else View.GONE

                    state.userProfile?.let { userProfile ->
                        val fullName = "${userProfile.firstName} ${userProfile.lastName}".trim()
                        binding.greetingTv.text = "Hola, $fullName"
                        binding.avatarTv.text = getInitials(userProfile.firstName, userProfile.lastName)
                        binding.phoneTv.text = userProfile.phone.ifBlank { "No registrado" }
                    }

                    binding.emailTv.text = state.email.ifBlank { "No registrado" }

                    state.error?.let { error ->
                        binding.greetingTv.text = "Hola"
                        binding.phoneTv.text = "No registrado"
                        Snackbar.make(binding.root, error, Snackbar.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    private fun getInitials(firstName: String, lastName: String): String {
        val firstInitial = firstName.trim().firstOrNull()?.uppercaseChar()?.toString().orEmpty()
        val lastInitial = lastName.trim().firstOrNull()?.uppercaseChar()?.toString().orEmpty()
        return (firstInitial + lastInitial).ifBlank { "A" }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
