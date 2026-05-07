package com.example.axoguia.onboarding.signIn

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.axoguia.R
import com.example.axoguia.core.FragmentCommunicator
import com.example.axoguia.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    //private val viewModel: LoginViewModel by viewModels()
    private lateinit var communicator: FragmentCommunicator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        communicator = requireActivity() as FragmentCommunicator
        setupValidation()
        binding.registerButton.setOnClickListener {
            // Navegar a la pantalla de registro
            findNavController().navigate(R.id.registerFragment2)
        }
        binding.restPasswordButton.setOnClickListener {
            // Navegar a la pantalla de restablecer contraseña
            findNavController().navigate(R.id.restPasswordFragment)

        }
        return binding.root
    }
    private fun setupValidation() {
        binding.login.isEnabled = false

        binding.username.addTextChangedListener {
            validateFields()
        }
        binding.password.addTextChangedListener {
            validateFields()
        }
    }

    private fun validateFields() {
        val email = binding.username.toString().trim()
        val password = binding.password.text.toString().trim()

        val isEmailValid = isValidEmail(email)
        val isPasswordValid = password.length >= 8

        binding.username.error = if (email.isNotEmpty() && isEmailValid) null else "Correo invalido"
        binding.password.error = if (password.isNotEmpty() && isPasswordValid) null else "Minimo 8 caracteres"

        binding.login.isEnabled =
            email.isNotEmpty() && password.isNotEmpty() && isEmailValid && isPasswordValid
    }

    private fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

}