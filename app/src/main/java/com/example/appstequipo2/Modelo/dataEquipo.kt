package com.example.appstequipo2.Modelo
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class dataEquipo(
    @SerialName("id_equipo") val idEquipo: Int = 0,
    @SerialName("codigo") val codigo: String = "",
    @SerialName("nombre") val nombre: String = "",
    @SerialName("imagen_url") val imagenUrl: String? = null,
    @SerialName("ubicacion") val ubicacion: String? = null,
    @SerialName("estado") val estado: String = "",
    @SerialName("id_marca") val idMarca: Int = 0,
    @SerialName("marca") val marca: String = "",
    @SerialName("id_categoria") val idCategoria: Int = 0,
    @SerialName("categoria") val categoria: String = ""
)
