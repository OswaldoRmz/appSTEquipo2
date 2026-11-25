package com.example.appstequipo2.Adaptador
import com.example.appstequipo2.R
object IconosAdaptador{
    fun obtenerIcono(nombreCategoria: String): Int {
        val nombre = nombreCategoria.lowercase()
        return when {
            nombre == "todos" -> R.drawable.ic_inventario
            nombre.contains("cómputo") || nombre.contains("computo") -> R.drawable.ic_desktop_windows
            nombre.contains("audio") || nombre.contains("video") -> R.drawable.ic_proyector
            nombre.contains("redes") -> R.drawable.ic_swap_horiz
            nombre.contains("cable") -> R.drawable.ic_cable
            nombre.contains("consumible") -> R.drawable.ic_extension
            nombre.contains("electr") -> R.drawable.ic_monitor
            else -> R.drawable.ic_inventario   // categorías nuevas que dé de alta el admin
        }
    }
}