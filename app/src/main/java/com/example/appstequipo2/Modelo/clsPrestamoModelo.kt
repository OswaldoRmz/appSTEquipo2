package com.example.appstequipo2.Modelo

import com.example.appstequipo2.Redes.RetrofitCliente
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

class clsPrestamoModelo {
    // Sirve para leer el mensaje que manda la API cuando responde con error (400, 404, 409...)
    private val json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
    }

    suspend fun solicitarPrestamo(request: dataPrestamoRequest): dataPrestamosResponse = withContext(Dispatchers.IO) {
        try {
            val response = RetrofitCliente.apiService.solicitarPrestamo(request).execute()
            if (response.isSuccessful) {
                response.body() ?: dataPrestamosResponse(ok = false, mensaje = "Sin respuesta del servidor")
            } else {
                // Ejemplo: "El equipo seleccionado no está disponible"
                try {
                    json.decodeFromString<dataPrestamosResponse>(response.errorBody()?.string() ?: "")
                } catch (e: Exception) {
                    dataPrestamosResponse(ok = false, mensaje = "No se pudo enviar la solicitud (código ${response.code()})")
                }
            }
        } catch (e: Exception) {
            dataPrestamosResponse(ok = false, mensaje = "Error de conexión: ${e.localizedMessage}")
        }
    }
}