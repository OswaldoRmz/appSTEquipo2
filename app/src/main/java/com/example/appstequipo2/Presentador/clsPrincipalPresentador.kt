package com.example.appstequipo2.Presentador
import com.example.appstequipo2.Vista.MainActivity

class clsPrincipalPresentador(private val vista: MainActivity) {
    fun irALogin() {
        vista.abrirLogin()
    }
    // Botón "Crear una cuenta nueva"
    fun irARegistro() {
        vista.abrirRegistro()
    }
    //Boton de ayuda para que te lleve a las preguntas frecuentes
    fun  irAyuda(){
        vista.abrirAyuda()
    }
}