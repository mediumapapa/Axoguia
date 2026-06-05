package com.example.axoguia.core.model

import com.google.gson.annotations.SerializedName

data class PlaceResponse(
    @SerializedName("meta") val meta: PlaceMeta,
    @SerializedName("lugares") val places: List<Place>
)

data class PlaceMeta(
    @SerializedName("titulo") val title: String,
    @SerializedName("especiePrincipal") val mainSpecies: String,
    @SerializedName("ciudad") val city: String,
    @SerializedName("pais") val country: String,
    @SerializedName("totalLugares") val totalPlaces: Int,
    @SerializedName("version") val version: String,
    @SerializedName("fuente") val source: String,
    @SerializedName("nota") val note: String
)

data class Place(
    @SerializedName("id") val id: Int,
    @SerializedName("nombre") val name: String,
    @SerializedName("nombreCorto") val shortName: String,
    @SerializedName("tipo") val type: String,
    @SerializedName("descripcion") val description: String,
    @SerializedName("direccion") val address: String,
    @SerializedName("alcaldia") val borough: String,
    @SerializedName("cp") val postalCode: String?,
    @SerializedName("referenciaTransporte") val transportReference: String?,
    @SerializedName("coordenadas") val coordinates: Coordinates,
    @SerializedName("coordsApprox") val approximateCoordinates: Boolean,
    @SerializedName("accesoPublico") val publicAccess: Boolean,
    @SerializedName("requiereCita") val requiresAppointment: Boolean,
    @SerializedName("operador") val operator: String
)

data class Coordinates(
    @SerializedName("lat") val latitude: Double,
    @SerializedName("lng") val longitude: Double
)
