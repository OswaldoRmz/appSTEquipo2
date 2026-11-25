package com.example.appstequipo2.Modelo

import com.example.appstequipo2.Redes.RetrofitCliente
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

class clsUsuarioModelo {
    // Sirve para leer el mensaje que manda la API cuando responde con error (400, 401, 409...)
    private val json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
    }

    suspend fun crearCuenta(request: dataCrearCuentaRequest): dataCrearCuentaResponse = withContext(Dispatchers.IO) {
        try {
            val response = RetrofitCliente.apiService.crearCuenta(request).execute()
            if (response.isSuccessful) {
                response.body() ?: dataCrearCuentaResponse(ok = false, mensaje = "Sin respuesta del servidor")
            } else {
                // Ejemplo: "El correo ya está registrado."
                try {
                    json.decodeFromString<dataCrearCuentaResponse>(response.errorBody()?.string() ?: "")
                } catch (e: Exception) {
                    dataCrearCuentaResponse(ok = false, mensaje = "No se pudo crear la cuenta (código ${response.code()})")
                }
            }
        } catch (e: Exception) {
            dataCrearCuentaResponse(ok = false, mensaje = "Error de conexión: ${e.localizedMessage}")
        }
    }

    suspend fun iniciarSesion(request: dataLoginRequest): dataLoginResponse = withContext(Dispatchers.IO) {
        try {
            val response = RetrofitCliente.apiService.iniciarSesion(request).execute()
            if (response.isSuccessful) {
                response.body() ?: dataLoginResponse(ok = false, mensaje = "Sin respuesta del servidor")
            } else {
                // Ejemplo: "Credenciales incorrectas"
                try {
                    json.decodeFromString<dataLoginResponse>(response.errorBody()?.string() ?: "")
                } catch (e: Exception) {
                    // CAMBIO: ahora muestra el código HTTP para saber qué falló
                    dataLoginResponse(ok = false, mensaje = "No se pudo iniciar sesión (código ${response.code()})")
                }
            }
        } catch (e: Exception) {
            dataLoginResponse(ok = false, mensaje = "Error de conexión: ${e.localizedMessage}")
        }
    }
}