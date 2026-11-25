package com.example.appstequipo2.Modelo

import com.example.appstequipo2.Redes.RetrofitCliente
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class clsEquipoModelo {

    // Solo las categorías de equipos (las de servicios van en otra pantalla)
    suspend fun obtenerCategorias(): List<dataCategoria> = withContext(Dispatchers.IO) {
        try {
            val response = RetrofitCliente.apiService.obtenerCategorias().execute()
            if (response.isSuccessful) {
                val categorias = response.body() ?: emptyList()
                categorias.filter { it.tipo == "Equipo" }
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    // Equipos disponibles; idCategoria = null trae todas las categorías
    suspend fun obtenerEquipos(idCategoria: Int?, buscar: String?): dataEquipoResponse = withContext(Dispatchers.IO) {
        try {
            val response = RetrofitCliente.apiService.obtenerEquipos(idCategoria, buscar).execute()
            if (response.isSuccessful) {
                response.body() ?: dataEquipoResponse(ok = false, mensaje = "Sin respuesta del servidor")
            } else {
                dataEquipoResponse(ok = false, mensaje = "No se pudieron cargar los equipos (código ${response.code()})")
            }
        } catch (e: Exception) {
            dataEquipoResponse(ok = false, mensaje = "Error de conexión: ${e.localizedMessage}")
        }
    }
}