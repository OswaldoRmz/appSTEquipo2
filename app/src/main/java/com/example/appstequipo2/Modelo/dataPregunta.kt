package com.example.appstequipo2.Modelo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

// Guarda cada pregunta que llega desde el PHP
@Serializable
data class dataPregunta(
    @SerialName("id_faq") val id_faq: Int = 0,
    @SerialName("pregunta") val pregunta: String = "",
    @SerialName("respuesta") val respuesta: String = "",
    @SerialName("orden_visual") val orden_visual: Int = 0,
    @SerialName("estado") val estado: String = "Activo",

    //es un interrptorm local que sirve para saber si ñla pregunta esta abirta
    @Transient var desplegada: Boolean = false
)
