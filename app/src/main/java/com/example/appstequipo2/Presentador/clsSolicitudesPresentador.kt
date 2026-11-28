package com.example.appstequipo2.Presentador

import com.example.appstequipo2.Modelo.clsSolicitudesModelo
import com.example.appstequipo2.Vista.SolicitudesActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class clsSolicitudesPresentador(private val vista: SolicitudesActivity) {
    private val modelo = clsSolicitudesModelo()

    // Trae órdenes de servicio y préstamos del usuario
    fun cargarHistorial(idUsuario: Int) {
        if (idUsuario <= 0) {
            vista.mostrarError("No se recibió el usuario, vuelve a iniciar sesión")
            return
        }

        CoroutineScope(Dispatchers.Main).launch {
            try {
                val respuestaOrdenes = modelo.obtenerMisOrdenes(idUsuario)
                val respuestaPrestamos = modelo.obtenerMisPrestamos(idUsuario)

                // Si alguna de las dos falla, se avisa pero se muestra lo que sí llegó
                if (!respuestaOrdenes.ok || !respuestaPrestamos.ok) {
                    vista.mostrarError("No se pudo cargar todo tu historial, revisa tu conexión")
                }

                vista.mostrarHistorial(respuestaOrdenes.ordenes, respuestaPrestamos.prestamos)
            } catch (e: Exception) {
                vista.mostrarError("Error al cargar tus solicitudes: " + e.localizedMessage)
            }
        }
    }
}