package com.example.axoguia.home.places

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.axoguia.core.model.Place
import com.example.axoguia.core.FragmentCommunicator
import com.example.axoguia.core.ResponseService
import com.example.axoguia.databinding.DialogPlaceDetailBinding
import com.example.axoguia.databinding.FragmentPlacesBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch


class PlacesFragment : Fragment() {

    private var _binding: FragmentPlacesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: PlacesViewModel by viewModels()
    private lateinit var communicator: FragmentCommunicator
    private val adapter = PlacesAdapter { place ->
        showPlaceDetail(place)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlacesBinding.inflate(inflater, container, false)
        communicator = requireActivity() as FragmentCommunicator
        setupRecyclerView()
        observeState()
        viewModel.loadPlaces(limit = 10)
        return binding.root
    }

    private fun setupRecyclerView() {
        binding.rvPlaces.layoutManager = LinearLayoutManager(requireContext())
        binding.rvPlaces.adapter = adapter
    }

    private fun observeState(){
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

    private fun showPlaceDetail(place: Place) {
        val detailBinding = DialogPlaceDetailBinding.inflate(layoutInflater)

        detailBinding.tvDetailName.text = place.name
        detailBinding.tvDetailType.text = "Tipo: ${place.type}"
        detailBinding.tvDetailDescription.text = place.description
        detailBinding.tvDetailAddress.text = "Direccion: ${place.address}"
        detailBinding.tvDetailBorough.text = "Alcaldia: ${place.borough}"
        detailBinding.tvDetailPostalCode.text = "CP: ${place.postalCode ?: "Sin CP"}"
        detailBinding.tvDetailCoordinates.text =
            "Coordenadas: ${place.coordinates.latitude}, ${place.coordinates.longitude}"
        detailBinding.tvDetailOperator.text = "Operador: ${place.operator}"

        MaterialAlertDialogBuilder(requireContext())
            .setView(detailBinding.root)
            .setPositiveButton("Cerrar", null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.rvPlaces.adapter = null
        _binding = null
    }
}
