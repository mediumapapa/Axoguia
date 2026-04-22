package com.example.axoguia

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
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

        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        communicator = requireActivity() as FragmentCommunicator
        communicator.manegeLoader(true)
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
}

/*
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment LoginFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LoginFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
*/