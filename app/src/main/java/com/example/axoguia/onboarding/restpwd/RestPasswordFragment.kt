package com.example.axoguia.onboarding.restpwd

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
import com.example.axoguia.core.FragmentCommunicator
import com.example.axoguia.core.ResponseService
import com.example.axoguia.databinding.FragmentRestPasswordBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class RestPasswordFragment : Fragment() {
    private var _binding: FragmentRestPasswordBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<RestPasswordViewModel>()
    private lateinit var communicator: FragmentCommunicator

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRestPasswordBinding.inflate(inflater, container, false)
        communicator = requireActivity() as FragmentCommunicator
        setupValidation()
        setupClickListeners()
        observeState()
        return binding.root
    }

    private fun setupValidation() {
        binding.sendButton.isEnabled = false
        binding.emailTiet.addTextChangedListener {
            val email = binding.emailTiet.text.toString().trim()
            binding.emailTil.error = viewModel.validateEmail(email)
            binding.sendButton.isEnabled = binding.emailTil.error == null
        }
    }

    private fun setupClickListeners() {
        binding.btnClose.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.sendButton.setOnClickListener {
            viewModel.sendResetEmail(binding.emailTiet.text.toString().trim())
        }
    }

    private fun observeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.resetState.collect { state ->
                    when (state) {
                        is ResponseService.Loading -> {
                            communicator.manageLoader(true)
                            binding.sendButton.isEnabled = false
                        }
                        is ResponseService.Success -> {
                            communicator.manageLoader(false)
                            Snackbar.make(binding.root, "Correo enviado", Snackbar.LENGTH_LONG).show()
                            findNavController().navigateUp()
                        }
                        is ResponseService.Error -> {
                            communicator.manageLoader(false)
                            binding.sendButton.isEnabled = true
                            Snackbar.make(binding.root, state.error, Snackbar.LENGTH_LONG).show()
                        }
                        null -> Unit
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
