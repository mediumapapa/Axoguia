package com.example.axoguia.home.places

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.axoguia.core.ResponseService
import com.example.axoguia.core.model.Place
import com.example.axoguia.core.network.PlaceService
import com.example.axoguia.core.repositories.PlaceRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PlacesViewModel : ViewModel() {
    private val service: PlaceService = PlaceRepository()

    private val _uiState = MutableStateFlow<ResponseService<List<Place>>?>(null)
    val uiState: StateFlow<ResponseService<List<Place>>?> = _uiState.asStateFlow()

    fun loadPlaces(limit: Int = 20, type: String? = null) {
        viewModelScope.launch {
            _uiState.value = ResponseService.Loading
            _uiState.value = service.getPlaces(
                limit = limit,
                type = type
            )
        }
    }
}
