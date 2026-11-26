package com.example.appstequipo2.Modelo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
data class dataSolicitudHistorial(
    @SerialName("tipo") val tipo: String = "",
    @SerialName("folio") val folio: String = "",
    @SerialName("titulo") val titulo: String = "",
    @SerialName("subtitulo") val subtitulo: String = "",
    @SerialName("fecha") val fecha: String = "",
    @SerialName("estado") val estado: String = "",
    @SerialName("detalle_extra_1") val detalleExtra1: String = "",
    @SerialName("detalle_extra_2") val detalleExtra2: String = "",

    @Transient var desplegada: Boolean = false
)
