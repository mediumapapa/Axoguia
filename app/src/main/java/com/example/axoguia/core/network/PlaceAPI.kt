package com.example.axoguia.core.network

import com.example.axoguia.core.model.PlaceResponse
import retrofit2.Response
import retrofit2.http.GET

interface PlaceAPI {
    @GET(".")
    suspend fun getPlaces(): Response<PlaceResponse>
}
