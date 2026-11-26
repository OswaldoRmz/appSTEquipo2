package com.example.appstequipo2.Presentador
import com.example.appstequipo2.Modelo.clsEquipoModelo
import com.example.appstequipo2.Modelo.dataCategoria
import com.example.appstequipo2.Modelo.dataEquipo
import com.example.appstequipo2.Vista.InicioActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class clsInicioPresentador(private val vista: InicioActivity) {
    private val modelo = clsEquipoModelo()

    // Filtros actuales (null = sin filtro)
    private var idCategoriaActual: Int? = null
    private var textoBusqueda: String? = null

    fun iniciar() {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                // 1. Categorías de equipos, con "Todos" al inicio (id 0)
                val categorias = modelo.obtenerCategorias()
                val listaConTodos = listOf(dataCategoria(idCategoria = 0, nombre = "Todos", tipo = "Equipo")) + categorias
                vista.mostrarCategorias(listaConTodos)

                // 2. Todos los equipos disponibles
                cargarEquipos()
            } catch (e: Exception) {
                vista.mostrarError("Error: " + e.localizedMessage)
            }
        }
    }

    // Botón de categoría (fila deslizable)
    fun seleccionarCategoria(categoria: dataCategoria) {
        // "Todos" tiene id 0: se manda null para que la API no filtre
        idCategoriaActual = if (categoria.idCategoria == 0) null else categoria.idCategoria
        cargarEquipos()
    }

    // Buscador (al presionar "buscar" en el teclado)
    fun buscar(texto: String) {
        textoBusqueda = if (texto.isEmpty()) null else texto
        cargarEquipos()
    }

    // Tarjeta de equipo: abrir Solicitar préstamo con ese equipo
    fun seleccionarEquipo(equipo: dataEquipo) {
        vista.abrirSolicitarPrestamo(equipo.idEquipo, equipo.nombre, equipo.codigo)
    }
    // Al regresar de otra pantalla, vuelve a traer los equipos con los mismos filtros
    fun recargarEquipos() {
        cargarEquipos()
    }
    // Trae los equipos con los filtros que estén puestos (categoría y búsqueda juntos)
    private fun cargarEquipos() {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val respuesta = modelo.obtenerEquipos(idCategoriaActual, textoBusqueda)
                if (respuesta.ok) {
                    vista.mostrarEquipos(respuesta.equipos)
                    vista.actualizarTotal(respuesta.equipos.size)
                } else {
                    vista.mostrarEquipos(emptyList())
                    vista.actualizarTotal(0)
                    vista.mostrarError(respuesta.mensaje)
                }
            } catch (e: Exception) {
                vista.mostrarError("Error al cargar equipos: " + e.localizedMessage)
            }
        }
    }
}