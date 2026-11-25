package com.example.appstequipo2.intefaceAPI

import com.example.appstequipo2.Modelo.dataCategoria
import com.example.appstequipo2.Modelo.dataCrearCuentaRequest
import com.example.appstequipo2.Modelo.dataCrearCuentaResponse
import com.example.appstequipo2.Modelo.dataEquipoResponse
import com.example.appstequipo2.Modelo.dataLoginRequest
import com.example.appstequipo2.Modelo.dataLoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiServicio {
    // Crear cuenta de cliente (POST)
    @POST("crear_cuenta.php")
    fun crearCuenta(@Body request: dataCrearCuentaRequest): Call<dataCrearCuentaResponse>

    // Iniciar sesión con correo y contraseña (POST)
    @POST("login.php")
    fun iniciarSesion(@Body request: dataLoginRequest): Call<dataLoginResponse>

    // Categorías (responde una lista directa, sin status/data)
    @GET("obtener_categorias.php")
    fun obtenerCategorias(): Call<List<dataCategoria>>

    // Equipos disponibles para préstamo; los dos filtros son opcionales
    @GET("obtener_equipos.php")
    fun obtenerEquipos(
        @Query("id_categoria") idCategoria: Int? = null,
        @Query("buscar") buscar: String? = null
    ): Call<dataEquipoResponse>
}