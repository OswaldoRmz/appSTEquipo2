package com.example.appstequipo2.Modelo

import com.example.appstequipo2.Redes.RetrofitCliente
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class clsSolicitudesModelo {

    // Órdenes de servicio que ha pedido el cliente
    suspend fun obtenerMisOrdenes(idCliente: Int): dataMisOrdenesResponse = withContext(Dispatchers.IO) {
        try {
            val response = RetrofitCliente.apiService.obtenerMisOrdenes(idCliente).execute()
            if (response.isSuccessful) {
                response.body() ?: dataMisOrdenesResponse(ok = false, mensaje = "Sin respuesta del servidor")
            } else {
                dataMisOrdenesResponse(ok = false, mensaje = "No se pudieron cargar tus servicios (código ${response.code()})")
            }
        } catch (e: Exception) {
            dataMisOrdenesResponse(ok = false, mensaje = "Error de conexión: ${e.localizedMessage}")
        }
    }

    // Préstamos que ha pedido el cliente
    suspend fun obtenerMisPrestamos(idCliente: Int): dataMisPrestamosResponse = withContext(Dispatchers.IO) {
        try {
            val response = RetrofitCliente.apiService.obtenerMisPrestamos(idCliente).execute()
            if (response.isSuccessful) {
                response.body() ?: dataMisPrestamosResponse(ok = false, mensaje = "Sin respuesta del servidor")
            } else {
                dataMisPrestamosResponse(ok = false, mensaje = "No se pudieron cargar tus préstamos (código ${response.code()})")
            }
        } catch (e: Exception) {
            dataMisPrestamosResponse(ok = false, mensaje = "Error de conexión: ${e.localizedMessage}")
        }
    }
}