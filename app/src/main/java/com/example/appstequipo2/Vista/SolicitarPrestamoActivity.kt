package com.example.appstequipo2.Vista

import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.appstequipo2.R

class SolicitarPrestamoActivity: AppCompatActivity() {
    private lateinit var btnBack: ImageButton
    private lateinit var tvSelectedName: TextView
    private lateinit var tvSelectedStatus: TextView
    private lateinit var tvChangeEquipment: TextView

    // Datos que manda Inicio (los usaremos al enviar la solicitud)
    private var idUsuario = 0
    private var idEquipo = 0

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

        idUsuario = intent.getIntExtra("ID_USUARIO", 0)
        idEquipo = intent.getIntExtra("ID_EQUIPO", 0)
        val nombre = intent.getStringExtra("NOMBRE_EQUIPO") ?: ""
        val codigo = intent.getStringExtra("CODIGO_EQUIPO") ?: ""

        // Equipo seleccionado en Inicio
        tvSelectedName.text = nombre
        tvSelectedStatus.text = "$codigo · Disponible"

        // Regresar a Inicio para elegir otro equipo
        btnBack.setOnClickListener { finish() }
        tvChangeEquipment.setOnClickListener { finish() }
    }
}