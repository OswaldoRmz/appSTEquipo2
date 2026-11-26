package com.example.appstequipo2.Modelo
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class dataFolio(
    @SerialName("folio") val folio: String = ""
)
