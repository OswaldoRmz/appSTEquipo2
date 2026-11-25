package com.example.appstequipo2.Modelo
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
@Serializable
data class dataUsuario(
    @SerialName("id_usuario") val idUsuario: Int = 0,
    @SerialName("nombres") val nombres: String = "",
    @SerialName("apellidos") val apellidos: String = "",
    @SerialName("telefono") val telefono: String = "",
    @SerialName("correo") val correo: String = "",
    @SerialName("direccion") val direccion: String? = null,
    @SerialName("id_rol") val idRol: Int = 0,
    @SerialName("estado") val estado: String = ""
)
