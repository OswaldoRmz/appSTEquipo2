package com.example.appstequipo2.Modelo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// Recibe cada fila del PHP de orden_servicio
@Serializable
data class dataOrdenHistorial(
    @SerialName("id_orden") val idOrden: Int = 0,
    @SerialName("folio") val folio: String = "",
    @SerialName("equipo_receptor") val equipoReceptor: String = "",
    @SerialName("falla_reportada") val fallaReportada: String = "",
    @SerialName("diagnostico_tecnico") val diagnosticoTecnico: String? = null,
    @SerialName("fecha_ingreso") val fechaIngreso: String = "",
    @SerialName("fecha_entrega") val fechaEntrega: String? = null,
    @SerialName("estado") val estado: String = "",
    @SerialName("costo_total") val costoTotal: String? = null,
    @SerialName("servicio_solicitado") val servicioSolicitado: String? = null
)

// Recibe cada fila del PHP de prestamo
@Serializable
data class dataPrestamoHistorial(
    @SerialName("id_prestamo") val idPrestamo: Int = 0,
    @SerialName("folio") val folio: String = "",
    @SerialName("fecha_inicio") val fechaInicio: String = "",
    @SerialName("fecha_limite") val fechaLimite: String = "",
    @SerialName("motivo") val motivo: String = "",
    @SerialName("estado") val estado: String = "",
    @SerialName("fecha_devolucion") val fechaDevolucion: String? = null,
    @SerialName("observaciones_devolucion") val observacionesDevolucion: String? = null,
    @SerialName("codigo_equipo") val codigoEquipo: String = "",
    @SerialName("equipo") val equipo: String = "",
    @SerialName("marca") val marca: String = ""
)
