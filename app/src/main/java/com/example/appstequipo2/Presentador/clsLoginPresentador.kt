package com.example.appstequipo2.Presentador

import android.util.Patterns
import com.example.appstequipo2.Modelo.clsUsuarioModelo
import com.example.appstequipo2.Modelo.dataLoginRequest
import com.example.appstequipo2.Vista.LoginActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class clsLoginPresentador(private val vista: LoginActivity) {
    private val modelo = clsUsuarioModelo()

    // Botón "Iniciar sesión"
    fun iniciarSesion(correo: String, password: String) {
        if (correo.isEmpty() || password.isEmpty()) {
            vista.mostrarError("Escribe tu correo y tu contraseña")
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
            vista.mostrarError("Escribe un correo electrónico válido")
            return
        }

        vista.mostrarCargando(true)
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val respuesta = modelo.iniciarSesion(dataLoginRequest(correo = correo, password = password))
                if (respuesta.ok && respuesta.usuario != null) {
                    vista.abrirInicio(respuesta.usuario.idUsuario, respuesta.usuario.nombres)
                } else {
                    vista.mostrarError(respuesta.mensaje)
                }
            } catch (e: Exception) {
                vista.mostrarError("Error al iniciar sesión: " + e.localizedMessage)
            } finally {
                vista.mostrarCargando(false)
            }
        }
    }
}