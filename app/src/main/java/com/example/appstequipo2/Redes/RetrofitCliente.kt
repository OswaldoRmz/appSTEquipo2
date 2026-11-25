package com.example.appstequipo2.Redes

import com.example.appstequipo2.intefaceAPI.ApiServicio
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

object RetrofitCliente {
    private const val BASE_URL = "https://oswaldo.4buthh.com/capsule_api/"; //aqui va la url del api

    private val jsonConfig= Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
        isLenient = true
    }

    val apiService: ApiServicio by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(
                jsonConfig.asConverterFactory(
                    "application/json; charset=UTF-8".toMediaType()
                )
            )
            .build()
            .create(ApiServicio::class.java)
    }
}