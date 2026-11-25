package com.example.appstequipo2.Presentador

import android.util.Patterns
import com.example.appstequipo2.Modelo.dataCrearCuentaRequest
import com.example.appstequipo2.Modelo.clsUsuarioModelo
import com.example.appstequipo2.Vista.RegistroUsuarioActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class clsRegistroUsuarioPresentador(private val vista: RegistroUsuarioActivity) {
    private val modelo = clsUsuarioModelo()

    // Botón "Crear mi cuenta"
    fun crearCuenta(
        nombres: String,
        apellidos: String,
        telefono: String,
        correo: String,
        password: String,
        direccion: String,
        aceptoTerminos: Boolean
    ) {
        // Validaciones antes de llamar a la API
        if (nombres.isEmpty() || apellidos.isEmpty() || telefono.isEmpty() || correo.isEmpty() || password.isEmpty()) {
            vista.mostrarError("Llena todos los campos obligatorios")
            return
        }

        if (telefono.length != 10 || !telefono.all { it.isDigit() }) {
            vista.mostrarError("El teléfono debe tener 10 dígitos")
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
            vista.mostrarError("Escribe un correo electrónico válido")
            return
        }

        if (password.length < 8) {
            vista.mostrarError("La contraseña debe tener al menos 8 caracteres")
            return
        }

        if (!aceptoTerminos) {
            vista.mostrarError("Debes aceptar los términos de uso")
            return
        }

        vista.mostrarCargando(true)
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val request = dataCrearCuentaRequest(
                    nombres = nombres,
                    apellidos = apellidos,
                    telefono = telefono,
                    correo = correo,
                    password = password,
                    direccion = direccion
                )
                val respuesta = modelo.crearCuenta(request)
                if (respuesta.ok) {
                    vista.cuentaCreada(respuesta.mensaje, correo)
                } else {
                    vista.mostrarError(respuesta.mensaje)
                }
            } catch (e: Exception) {
                vista.mostrarError("Error al crear la cuenta: " + e.localizedMessage)
            } finally {
                vista.mostrarCargando(false)
            }
        }
    }

    // Enlace "¿Ya tienes una cuenta? Inicia sesión"
    fun irALogin() {
        vista.abrirLogin("")
    }
}