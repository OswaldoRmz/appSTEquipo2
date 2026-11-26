package com.example.appstequipo2.Modelo
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class dataPrestamoRequest(
    @SerialName("id_cliente") val idCliente: Int,
    @SerialName("id_equipo") val idEquipo: Int,
    @SerialName("fecha_inicio") val fechaInicio: String,   // "2026-11-25 14:00:00"
    @SerialName("fecha_limite") val fechaLimite: String,   // "2026-11-26 14:00:00"
    @SerialName("motivo") val motivo: String               // lo escrito en "Garantía"
)
