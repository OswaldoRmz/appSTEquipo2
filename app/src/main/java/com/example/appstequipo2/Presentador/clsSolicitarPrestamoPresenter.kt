package com.example.appstequipo2.Presentador

import com.example.appstequipo2.Vista.SolicitarPrestamoActivity
import com.example.appstequipo2.Modelo.clsPrestamoModelo
import com.example.appstequipo2.Modelo.dataPrestamoRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
class clsSolicitarPrestamoPresenter(private val vista: SolicitarPrestamoActivity) {
    private val modelo = clsPrestamoModelo()

    private val formatoApi = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)

    // Botón "Continuar"
    fun enviarSolicitud(idUsuario: Int, idEquipo: Int, inicio: Calendar, fin: Calendar, garantia: String) {
        if (idUsuario <= 0 || idEquipo <= 0) {
            vista.mostrarError("No se recibió el equipo o el usuario")
            return
        }

        if (inicio.timeInMillis < System.currentTimeMillis()) {
            vista.mostrarError("La fecha y hora de préstamo ya pasaron")
            return
        }

        if (!fin.after(inicio)) {
            vista.mostrarError("La devolución debe ser después del préstamo")
            return
        }

        if (garantia.isEmpty()) {
            vista.mostrarError("Escribe qué dejarás en garantía")
            return
        }

        vista.mostrarCargando(true)
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val request = dataPrestamoRequest(
                    idCliente = idUsuario,
                    idEquipo = idEquipo,
                    fechaInicio = formatoApi.format(inicio.time),
                    fechaLimite = formatoApi.format(fin.time),
                    motivo = garantia
                )
                val respuesta = modelo.solicitarPrestamo(request)
                if (respuesta.ok) {
                    vista.solicitudEnviada(respuesta.mensaje, respuesta.datos?.folio ?: "")
                } else {
                    vista.mostrarError(respuesta.mensaje)
                }
            } catch (e: Exception) {
                vista.mostrarError("Error al enviar la solicitud: " + e.localizedMessage)
            } finally {
                vista.mostrarCargando(false)
            }
        }
    }
}