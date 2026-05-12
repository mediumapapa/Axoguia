package com.example.axoguia.onboarding.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.axoguia.R
import com.example.axoguia.core.FragmentCommunicator
import com.example.axoguia.core.ResponseService
import com.example.axoguia.databinding.FragmentRegisterBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<RegisterViewModel>()
    private lateinit var communicator: FragmentCommunicator

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        communicator = requireActivity() as FragmentCommunicator
        setupValidation()
        setupClickListeners()
        observeState()
        return binding.root
    }

    private fun setupValidation() {
        binding.registerPersonalButton.isEnabled = false
        binding.nameTiet.addTextChangedListener { validateAndEnable() }
        binding.emailTiet.addTextChangedListener { validateAndEnable() }
        binding.passwordTiet.addTextChangedListener { validateAndEnable() }
        binding.confirmPasswordTiet.addTextChangedListener { validateAndEnable() }
    }

    private fun validateAndEnable() {
        val name = binding.nameTiet.text.toString().trim()
        val email = binding.emailTiet.text.toString().trim()
        val pass = binding.passwordTiet.text.toString().trim()
        val confirm = binding.confirmPasswordTiet.text.toString().trim()

        binding.nameTiet.error = viewModel.validateName(name)
        binding.emailTiet.error = viewModel.validateEmail(email)
        binding.passwordTiet.error = viewModel.validatePassword(pass)
        binding.confirmPasswordTiet.error = viewModel.validateConfirmPassword(pass, confirm)

        binding.registerPersonalButton.isEnabled =
            viewModel.isRegisterFormValid(name, email, pass, confirm)
    }

    private fun setupClickListeners() {
        binding.registerPersonalButton.setOnClickListener {
            val name = binding.nameTiet.text.toString().trim()
            val email = binding.emailTiet.text.toString().trim()
            val password = binding.passwordTiet.text.toString().trim()
            viewModel.requestSignUp(name, email, password)
        }
        binding.registerText.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun observeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.registerState.collect { state ->
                    when (state) {
                        is ResponseService.Loading -> {
                            communicator.manageLoader(true)
                            binding.registerPersonalButton.isEnabled = false
                        }
                        is ResponseService.Success -> {
                            communicator.manageLoader(false)
                            // TODO: navegar a pantalla de datos personales
                            findNavController().navigate(R.id.action_registerFragment2_to_registerPersonalFragment)
                        }
                        is ResponseService.Error -> {
                            communicator.manageLoader(false)
                            binding.registerPersonalButton.isEnabled = true
                            Snackbar.make(binding.root, state.error,
                                Snackbar.LENGTH_LONG).show()
                        }
                        null -> Unit
                    }
                }
            }
        }
    }

}
