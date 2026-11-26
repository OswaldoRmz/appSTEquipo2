package com.example.appstequipo2.Modelo
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class dataPrestamosResponse(
    @SerialName("status") val ok: Boolean = false,
    @SerialName("mensaje") val mensaje: String = "",
    @SerialName("data") val datos: dataFolio? = null
)
