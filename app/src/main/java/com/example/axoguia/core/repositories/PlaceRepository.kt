package com.example.axoguia.core.repositories

import com.example.axoguia.core.ResponseService
import com.example.axoguia.core.model.Place
import com.example.axoguia.core.network.ApiClient
import com.example.axoguia.core.network.PlaceService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PlaceRepository : PlaceService {
    private val api = ApiClient.placeApi

    override suspend fun getPlaces(
        limit: Int,
        type: String?
    ): ResponseService<List<Place>> = withContext(Dispatchers.IO) {
        try {
            val response = api.getPlaces()

            if (response.isSuccessful) {
                val body = response.body()

                if (body != null) {
                    val places = body.places
                        .filter { place ->
                            type.isNullOrBlank() || place.type.contains(type, ignoreCase = true)
                        }
                        .take(limit)

                    ResponseService.Success(places)
                } else {
                    ResponseService.Error("Respuesta vacía del servidor")
                }
            } else {
                ResponseService.Error("Error ${response.code()}: ${response.message()}")
            }
        } catch (e: Exception) {
            ResponseService.Error(
                "No se pudieron cargar los lugares: ${e.localizedMessage}"
            )
        }
    }
}
