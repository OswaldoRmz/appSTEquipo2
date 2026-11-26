package com.example.appstequipo2.Vista

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appstequipo2.Adaptador.SolicitudesAdaptador
import com.example.appstequipo2.Modelo.dataOrdenHistorial
import com.example.appstequipo2.Modelo.dataPrestamoHistorial
import com.example.appstequipo2.Modelo.dataSolicitudHistorial
import com.example.appstequipo2.R
import com.google.android.material.card.MaterialCardView

class SolicitudesActivity : AppCompatActivity() {

    private lateinit var rvSolicitudes: RecyclerView
    private lateinit var adapterSolicitudes: SolicitudesAdaptador
    private lateinit var tvSinSolicitudes: TextView
    private lateinit var tvBadgeActivas: TextView
    private lateinit var cardBuscador: MaterialCardView
    private lateinit var etBuscarSolicitud: EditText

    // Pestañas
    private lateinit var tvTabActivas: TextView
    private lateinit var tvTabResueltas: TextView
    private lateinit var tvTabTodas: TextView
    private lateinit var indActivas: View
    private lateinit var indResueltas: View
    private lateinit var indTodas: View

    private var listaCompleta: MutableList<dataSolicitudHistorial> = mutableListOf()
    private var pestanaActual = "ACTIVAS"

    private var idUsuario = 0
    private var nombres = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_mis_solicitudes)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        idUsuario = intent.getIntExtra("ID_USUARIO", 0)
        nombres = intent.getStringExtra("NOMBRES") ?: ""

        rvSolicitudes = findViewById(R.id.rvSolicitudes)
        tvSinSolicitudes = findViewById(R.id.tvSinSolicitudes)
        tvBadgeActivas = findViewById(R.id.tvBadgeActivas)
        cardBuscador = findViewById(R.id.cardBuscadorSolicitudes)
        etBuscarSolicitud = findViewById(R.id.etBuscarSolicitud)

        tvTabActivas = findViewById(R.id.tvTabActivas)
        tvTabResueltas = findViewById(R.id.tvTabResueltas)
        tvTabTodas = findViewById(R.id.tvTabTodas)
        indActivas = findViewById(R.id.indActivas)
        indResueltas = findViewById(R.id.indResueltas)
        indTodas = findViewById(R.id.indTodas)

        rvSolicitudes.layoutManager = LinearLayoutManager(this)
        adapterSolicitudes = SolicitudesAdaptador(emptyList())
        rvSolicitudes.adapter = adapterSolicitudes

        configurarListeners()

        // Aquí llamas a tu presentador pasando 'idUsuario' para traer órdenes y préstamos de tus 2 PHP:
        // presenter.cargarHistorial(idUsuario)
    }

    private fun configurarListeners() {
        // Mostrar u ocultar la barra de búsqueda con la lupa
        findViewById<MaterialCardView>(R.id.btnToggleBuscar).setOnClickListener {
            if (cardBuscador.visibility == View.VISIBLE) {
                cardBuscador.visibility = View.GONE
                etBuscarSolicitud.setText("")
            } else {
                cardBuscador.visibility = View.VISIBLE
                etBuscarSolicitud.requestFocus()
            }
        }

        // Filtrar en tiempo real al escribir
        etBuscarSolicitud.doAfterTextChanged {
            aplicarFiltros()
        }

        // Clics en las 3 pestañas
        findViewById<LinearLayout>(R.id.tabActivas).setOnClickListener { cambiarPestana("ACTIVAS") }
        findViewById<LinearLayout>(R.id.tabResueltas).setOnClickListener { cambiarPestana("RESUELTAS") }
        findViewById<LinearLayout>(R.id.tabTodas).setOnClickListener { cambiarPestana("TODAS") }

        // --- NAVEGACIÓN DEL MENÚ INFERIOR ---
        findViewById<LinearLayout>(R.id.navInicio).setOnClickListener {
            val intent = Intent(this, InicioActivity::class.java)
            intent.putExtra("ID_USUARIO", idUsuario)
            intent.putExtra("NOMBRES", nombres)
            startActivity(intent)
        }

        findViewById<LinearLayout>(R.id.navServicios).setOnClickListener {
            val intent = Intent(this, ServiciosActivity::class.java)
            intent.putExtra("ID_USUARIO", idUsuario)
            intent.putExtra("NOMBRES", nombres)
            startActivity(intent)
        }

        findViewById<LinearLayout>(R.id.navSoporte).setOnClickListener {
            val intent = Intent(this, PreguntasFrecuentesActivity::class.java)
            intent.putExtra("ID_USUARIO", idUsuario)
            intent.putExtra("NOMBRES", nombres)
            startActivity(intent)
        }
    }

    private fun cambiarPestana(nueva: String) {
        pestanaActual = nueva

        // Pintar pestaña Activas
        tvTabActivas.setTextColor(if (nueva == "ACTIVAS") Color.parseColor("#0F172A") else Color.parseColor("#64748B"))
        tvTabActivas.setTypeface(null, if (nueva == "ACTIVAS") Typeface.BOLD else Typeface.NORMAL)
        indActivas.visibility = if (nueva == "ACTIVAS") View.VISIBLE else View.INVISIBLE

        // Pintar pestaña Resueltas
        tvTabResueltas.setTextColor(if (nueva == "RESUELTAS") Color.parseColor("#0F172A") else Color.parseColor("#64748B"))
        tvTabResueltas.setTypeface(null, if (nueva == "RESUELTAS") Typeface.BOLD else Typeface.NORMAL)
        indResueltas.visibility = if (nueva == "RESUELTAS") View.VISIBLE else View.INVISIBLE

        // Pintar pestaña Todas
        tvTabTodas.setTextColor(if (nueva == "TODAS") Color.parseColor("#0F172A") else Color.parseColor("#64748B"))
        tvTabTodas.setTypeface(null, if (nueva == "TODAS") Typeface.BOLD else Typeface.NORMAL)
        indTodas.visibility = if (nueva == "TODAS") View.VISIBLE else View.INVISIBLE

        aplicarFiltros()
    }

    private fun esSolicitudResuelta(estado: String): Boolean {
        return estado.equals("Entregado", true) ||
                estado.equals("Devuelto", true) ||
                estado.equals("Finalizado", true) ||
                estado.equals("Cancelado", true)
    }

    private fun aplicarFiltros() {
        val texto = etBuscarSolicitud.text.toString().trim()

        // Actualizar numerito verde de Activas
        val totalActivas = listaCompleta.count { !esSolicitudResuelta(it.estado) }
        tvBadgeActivas.text = totalActivas.toString()

        val filtrada = listaCompleta.filter { item ->
            val coincideTab = when (pestanaActual) {
                "ACTIVAS" -> !esSolicitudResuelta(item.estado)
                "RESUELTAS" -> esSolicitudResuelta(item.estado)
                else -> true
            }
            val coincideTexto = texto.isEmpty() ||
                    item.folio.contains(texto, true) ||
                    item.titulo.contains(texto, true) ||
                    item.subtitulo.contains(texto, true)
            coincideTab && coincideTexto
        }

        adapterSolicitudes.actualizarLista(filtrada)
        tvSinSolicitudes.visibility = if (filtrada.isEmpty()) View.VISIBLE else View.GONE
    }

    // --- MÉTODO QUE LLAMA EL PRESENTADOR CON LOS RESULTADOS DE AMBOS PHP ---
    fun mostrarHistorial(ordenes: List<dataOrdenHistorial>, prestamos: List<dataPrestamoHistorial>) {
        listaCompleta.clear()

        // Convertimos cada orden de servicio al formato de tarjeta
        for (o in ordenes) {
            val diag = o.diagnosticoTecnico ?: "En revisión por técnico"
            val costo = if (!o.costoTotal.isNullOrEmpty() && o.costoTotal != "0.00") " • Total: $${o.costoTotal}" else ""

            listaCompleta.add(
                dataSolicitudHistorial(
                    tipo = "SERVICIO",
                    folio = o.folio,
                    titulo = o.equipoReceptor,
                    subtitulo = o.servicioSolicitado ?: "Soporte técnico general",
                    fecha = "Ingreso: ${o.fechaIngreso}",
                    estado = o.estado,
                    detalleExtra1 = "Falla reportada: ${o.fallaReportada}",
                    detalleExtra2 = "Diagnóstico: $diag$costo"
                )
            )
        }

        // Convertimos cada préstamo al formato de tarjeta
        for (p in prestamos) {
            val obs = p.observacionesDevolucion ?: "Sin observaciones"

            listaCompleta.add(
                dataSolicitudHistorial(
                    tipo = "PRÉSTAMO",
                    folio = p.folio,
                    titulo = "${p.equipo} (${p.marca})",
                    subtitulo = "Código: ${p.codigoEquipo}",
                    fecha = "Inicio: ${p.fechaInicio} • Límite: ${p.fechaLimite}",
                    estado = p.estado,
                    detalleExtra1 = "Motivo: ${p.motivo}",
                    detalleExtra2 = "Observaciones: $obs"
                )
            )
        }

        aplicarFiltros()
    }

    fun mostrarError(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
    }
}