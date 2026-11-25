package com.example.appstequipo2.Modelo
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
@Serializable
data class dataCrearCuentaResponse(
    @SerialName("ok") val ok: Boolean = false,
    @SerialName("mensaje") val mensaje: String = "",
    @SerialName("id_usuario") val idUsuario: Int = 0
)
