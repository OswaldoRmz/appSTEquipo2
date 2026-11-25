package com.example.appstequipo2.Modelo
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
@Serializable
data class dataCrearCuentaRequest(
    @SerialName("nombres") val nombres: String,
    @SerialName("apellidos") val apellidos: String,
    @SerialName("telefono") val telefono: String,
    @SerialName("correo") val correo: String,
    @SerialName("password") val password: String,
    @SerialName("direccion") val direccion: String
)
