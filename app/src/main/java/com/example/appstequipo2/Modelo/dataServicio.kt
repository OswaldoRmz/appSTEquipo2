package com.example.appstequipo2.Modelo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// Almacena cada servicio que llega desde la base de datos
@Serializable
data class dataServicio(
    @SerialName("id_servicio") val id_servicio: Int = 0,
    @SerialName("nombre") val nombre: String = "",
    @SerialName("descripcion") val descripcion: String = "",
    @SerialName("id_categoria") val id_categoria: Int = 0,
    @SerialName("categoria") val categoria: String = "",
    @SerialName("estado") val estado: String = "Activo"
)
