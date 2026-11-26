package com.example.appstequipo2.Vista

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.appstequipo2.Presentador.clsSolicitarPrestamoPresenter
import com.example.appstequipo2.R
import com.google.android.material.card.MaterialCardView
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class SolicitarPrestamoActivity : AppCompatActivity() {
    private lateinit var presenter: clsSolicitarPrestamoPresenter
    private lateinit var btnBack: ImageButton
    private lateinit var tvSelectedName: TextView
    private lateinit var tvSelectedStatus: TextView
    private lateinit var tvChangeEquipment: TextView
    private lateinit var cardLoanDate: MaterialCardView
    private lateinit var cardReturnDate: MaterialCardView
    private lateinit var cardPickupTime: MaterialCardView
    private lateinit var cardReturnTime: MaterialCardView
    private lateinit var tvLoanDate: TextView
    private lateinit var tvReturnDate: TextView
    private lateinit var tvPickupTime: TextView
    private lateinit var tvReturnTime: TextView
    private lateinit var etLoanReason: EditText
    private lateinit var btnContinue: Button

    // Datos que manda Inicio
    private var idUsuario = 0
    private var idEquipo = 0

    // Fecha y hora elegidas (cada Calendar guarda día y hora juntos)
    private val fechaInicio = Calendar.getInstance()
    private val fechaFin = Calendar.getInstance()

    // Cómo se muestran: "Miércoles, 25 de noviembre" y "02:00 PM"
    private val formatoFecha = SimpleDateFormat("EEEE, d 'de' MMMM", Locale.forLanguageTag("es-MX"))
    private val formatoHora = SimpleDateFormat("hh:mm a", Locale.US)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_solicitar_prestamo)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        btnBack = findViewById(R.id.btnBack)
        tvSelectedName = findViewById(R.id.tvSelectedName)
        tvSelectedStatus = findViewById(R.id.tvSelectedStatus)
        tvChangeEquipment = findViewById(R.id.tvChangeEquipment)
        cardLoanDate = findViewById(R.id.cardLoanDate)
        cardReturnDate = findViewById(R.id.cardReturnDate)
        cardPickupTime = findViewById(R.id.cardPickupTime)
        cardReturnTime = findViewById(R.id.cardReturnTime)
        tvLoanDate = findViewById(R.id.tvLoanDate)
        tvReturnDate = findViewById(R.id.tvReturnDate)
        tvPickupTime = findViewById(R.id.tvPickupTime)
        tvReturnTime = findViewById(R.id.tvReturnTime)
        etLoanReason = findViewById(R.id.etLoanReason)
        btnContinue = findViewById(R.id.btnContinue)

        idUsuario = intent.getIntExtra("ID_USUARIO", 0)
        idEquipo = intent.getIntExtra("ID_EQUIPO", 0)
        val nombre = intent.getStringExtra("NOMBRE_EQUIPO") ?: ""
        val codigo = intent.getStringExtra("CODIGO_EQUIPO") ?: ""

        // Equipo seleccionado en Inicio
        tvSelectedName.text = nombre
        tvSelectedStatus.text = "$codigo · Disponible"

        // Valores iniciales: préstamo hoy a la siguiente hora en punto, devolución mañana a la misma hora
        fechaInicio.add(Calendar.HOUR_OF_DAY, 1)
        fechaInicio.set(Calendar.MINUTE, 0)
        fechaInicio.set(Calendar.SECOND, 0)
        fechaInicio.set(Calendar.MILLISECOND, 0)
        fechaFin.timeInMillis = fechaInicio.timeInMillis
        fechaFin.add(Calendar.DAY_OF_MONTH, 1)
        mostrarFechas()

        presenter = clsSolicitarPrestamoPresenter(this)
        configurarListeners()
    }

    private fun configurarListeners() {
        // Regresar a Inicio para elegir otro equipo
        btnBack.setOnClickListener { finish() }
        tvChangeEquipment.setOnClickListener { finish() }

        // Calendarios y relojes
        cardLoanDate.setOnClickListener { elegirFecha(fechaInicio) }
        cardReturnDate.setOnClickListener { elegirFecha(fechaFin) }
        cardPickupTime.setOnClickListener { elegirHora(fechaInicio) }
        cardReturnTime.setOnClickListener { elegirHora(fechaFin) }

        btnContinue.setOnClickListener {
            presenter.enviarSolicitud(
                idUsuario,
                idEquipo,
                fechaInicio,
                fechaFin,
                etLoanReason.text.toString().trim()
            )
        }
    }

    // Calendario: cambia solo el día y conserva la hora que ya tenía
    private fun elegirFecha(fecha: Calendar) {
        val dialogo = DatePickerDialog(
            this,
            { _, anio, mes, dia ->
                fecha.set(anio, mes, dia)
                mostrarFechas()
            },
            fecha.get(Calendar.YEAR),
            fecha.get(Calendar.MONTH),
            fecha.get(Calendar.DAY_OF_MONTH)
        )
        // No deja elegir días pasados
        dialogo.datePicker.minDate = System.currentTimeMillis() - 1000
        dialogo.show()
    }

    // Reloj: cambia solo la hora y conserva el día
    private fun elegirHora(fecha: Calendar) {
        TimePickerDialog(
            this,
            { _, hora, minuto ->
                fecha.set(Calendar.HOUR_OF_DAY, hora)
                fecha.set(Calendar.MINUTE, minuto)
                mostrarFechas()
            },
            fecha.get(Calendar.HOUR_OF_DAY),
            fecha.get(Calendar.MINUTE),
            false   // reloj de 12 horas (AM/PM), como el diseño
        ).show()
    }

    // Pone en las 4 tarjetas la fecha y hora elegidas
    private fun mostrarFechas() {
        tvLoanDate.text = formatoFecha.format(fechaInicio.time).replaceFirstChar { it.uppercase() }
        tvReturnDate.text = formatoFecha.format(fechaFin.time).replaceFirstChar { it.uppercase() }
        tvPickupTime.text = formatoHora.format(fechaInicio.time)
        tvReturnTime.text = formatoHora.format(fechaFin.time)
    }

    // Los llama el presentador
    fun mostrarCargando(cargando: Boolean) {
        btnContinue.isEnabled = !cargando
        btnContinue.text = if (cargando) "Enviando..." else "Continuar"
    }

    fun solicitudEnviada(mensaje: String, folio: String) {
        // Aviso con el folio; al aceptar regresa a Inicio
        AlertDialog.Builder(this)
            .setTitle("Solicitud enviada")
            .setMessage("$mensaje\n\nTu folio es: $folio")
            .setCancelable(false)
            .setPositiveButton("Aceptar") { _, _ -> finish() }
            .show()
    }

    fun mostrarError(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
    }

}