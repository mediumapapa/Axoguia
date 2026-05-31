package com.example.axoguia.home.places

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import com.example.axoguia.databinding.FragmentConservationPlacesBinding
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.axoguia.core.FragmentCommunicator
import com.example.axoguia.core.ResponseService
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch


class PlacesFragment : Fragment() {

    private var _binding: FragmentConservationPlacesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: PlacesViewModel by viewModels()
    private lateinit var communicator: FragmentCommunicator
    private val adapter = PlacesAdapter { place ->
        Snackbar.make(binding.root, place.name, Snackbar.LENGTH_SHORT).show()
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentConservationPlacesBinding.inflate(inflater, container, false)
        communicator = requireActivity() as FragmentCommunicator
        binding.rvPlaces.layoutManager = LinearLayoutManager(requireContext())
        binding.rvPlaces.adapter = adapter
        observeState()
        viewModel.loadPlaces()
        return binding.root
    }

    fun observeState(){
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                   when (state) {
                       is ResponseService.Loading -> {
                           communicator.manageLoader(true)

                       }
                       is ResponseService.Success -> {
                           communicator.manageLoader(false)
                           adapter.submitList(state.data)
                       }
                       is ResponseService.Error -> {
                           communicator.manageLoader(false)
                           Snackbar.make(binding.root, state.error, Snackbar.LENGTH_LONG).show()
                       }
                       null -> {}
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
