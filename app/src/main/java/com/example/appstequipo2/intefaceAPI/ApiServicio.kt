package com.example.appstequipo2.intefaceAPI

import com.example.appstequipo2.Modelo.dataCrearCuentaRequest
import com.example.appstequipo2.Modelo.dataCrearCuentaResponse
import com.example.appstequipo2.Modelo.dataLoginRequest
import com.example.appstequipo2.Modelo.dataLoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
interface ApiServicio {
    // Crear cuenta de cliente (POST)
    @POST("crear_cuenta.php")
    fun crearCuenta(@Body request: dataCrearCuentaRequest): Call<dataCrearCuentaResponse>

    // Iniciar sesión con correo y contraseña (POST)
    @POST("login.php")
    fun iniciarSesion(@Body request: dataLoginRequest): Call<dataLoginResponse>
}