package com.example.appstequipo2.Modelo
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
@Serializable
data class dataLoginRequest(
    @SerialName("correo") val correo: String,
    @SerialName("password") val password: String
)
