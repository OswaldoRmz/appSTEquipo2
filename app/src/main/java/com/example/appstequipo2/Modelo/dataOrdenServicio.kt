package com.example.appstequipo2.Modelo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

//para mandar la solicitud de servicio por POST al PHP
@Serializable
data class dataOrdenServicio(
    @SerialName("id_cliente") val id_cliente: Int,
    @SerialName("id_servicio") val id_servicio: Int? = null,
    @SerialName("equipo_receptor") val equipo_receptor: String,
    @SerialName("falla_reportada") val falla_reportada: String
)
