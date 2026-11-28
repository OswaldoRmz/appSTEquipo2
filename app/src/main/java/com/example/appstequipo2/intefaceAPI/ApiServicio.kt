package com.example.appstequipo2.intefaceAPI

import com.example.appstequipo2.Modelo.dataCategoria
import com.example.appstequipo2.Modelo.dataCrearCuentaRequest
import com.example.appstequipo2.Modelo.dataCrearCuentaResponse
import com.example.appstequipo2.Modelo.dataEquipoResponse
import com.example.appstequipo2.Modelo.dataLoginRequest
import com.example.appstequipo2.Modelo.dataLoginResponse
import com.example.appstequipo2.Modelo.dataMisOrdenesResponse
import com.example.appstequipo2.Modelo.dataMisPrestamosResponse
import com.example.appstequipo2.Modelo.dataOrdenResponse
import com.example.appstequipo2.Modelo.dataOrdenServicio
import com.example.appstequipo2.Modelo.dataPrestamoRequest
import com.example.appstequipo2.Modelo.dataPrestamosResponse
import com.example.appstequipo2.Modelo.dataServicioResponse
import com.example.appstequipo2.Modelo.dataPregunta
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

    // Solicitar el préstamo de un equipo (POST)
    @POST("solicitar_prestamo.php")
    fun solicitarPrestamo(@Body request: dataPrestamoRequest): Call<dataPrestamosResponse>

    // Catálogo de servicios activos
    @GET("obtener_servicios.php")
    fun obtenerServicios(): Call<dataServicioResponse>

    // Crear una orden de servicio (POST)
    @POST("solicitar_servicio.php")
    fun solicitarServicio(@Body request: dataOrdenServicio): Call<dataOrdenResponse>

    // Historial de órdenes de servicio del cliente
    @GET("mis_ordenes.php")
    fun obtenerMisOrdenes(@Query("id_cliente") idCliente: Int): Call<dataMisOrdenesResponse>

    // Historial de préstamos del cliente
    @GET("mis_prestamos.php")
    fun obtenerMisPrestamos(@Query("id_cliente") idCliente: Int): Call<dataMisPrestamosResponse>
    // Preguntas frecuentes activas (responde una lista directa, sin status/data)
    @GET("faq.php")
    fun obtenerPreguntas(): Call<List<dataPregunta>>
}