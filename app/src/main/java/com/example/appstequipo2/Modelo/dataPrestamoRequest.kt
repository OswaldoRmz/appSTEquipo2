package com.example.appstequipo2.Modelo
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class dataPrestamoRequest(
    @SerialName("id_cliente") val idCliente: Int,
)
