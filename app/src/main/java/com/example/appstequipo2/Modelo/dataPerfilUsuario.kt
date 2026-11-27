package com.example.appstequipo2.Modelo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// Guarda los datos personales del usuario que llegan desde la base de datos
@Serializable
data class dataPerfilUsuario(
    @SerialName("id_usuario") val idUsuario: Int = 0,
    @SerialName("nombres") val nombres: String = "",
    @SerialName("apellidos") val apellidos: String = "",
    @SerialName("usuario") val usuario: String = "",
    @SerialName("correo") val correo: String = "",
    @SerialName("rol") val rol: String = "Usuario",
    @SerialName("direccion") val direccion: String = "",
    @SerialName("telefono") val telefono: String = ""
)
