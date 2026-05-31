package com.example.axoguia.core.network

import com.example.axoguia.core.ResponseService
import com.example.axoguia.core.model.Place

interface PlaceService {
    suspend fun getPlaces(
        limit: Int = 20,
        type: String? = null
    ): ResponseService<List<Place>>
}
