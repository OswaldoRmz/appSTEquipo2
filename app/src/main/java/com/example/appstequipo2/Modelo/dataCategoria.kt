package com.example.appstequipo2.Modelo
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class dataCategoria(
    @SerialName("id_categoria") val idCategoria: Int = 0,
    @SerialName("nombre") val nombre: String = "",
    @SerialName("tipo") val tipo: String = ""
)
