package com.example.axoguia.onboarding.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.axoguia.databinding.FragmentRegisterBinding
import com.example.axoguia.onboarding.signIn.SignInViewModel

class   RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<SignInViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        binding.registerPersonalButton.setOnClickListener {
            viewModel.requestSignUp(
                binding.nameTiet.text.toString().trim(),
                binding.emailTiet.text.toString().trim(),
                binding.passwordTiet.text.toString().trim()
            )
        }
        return binding.root

    }

}