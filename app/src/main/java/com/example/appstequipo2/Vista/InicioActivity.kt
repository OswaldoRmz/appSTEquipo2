package com.example.appstequipo2.Vista

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.appstequipo2.R

class InicioActivity : AppCompatActivity() {
    private lateinit var tvGreeting: TextView
    private lateinit var tvAvatarInitial: TextView

    // Datos del usuario que inició sesión (el id lo usaremos en préstamos y solicitudes)
    private var idUsuario = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_inicio)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        tvGreeting = findViewById(R.id.tvGreeting)
        tvAvatarInitial = findViewById(R.id.tvAvatarInitial)

        idUsuario = intent.getIntExtra("ID_USUARIO", 0)
        val nombres = intent.getStringExtra("NOMBRES") ?: ""

        val primerNombre = nombres.substringBefore(" ")
        tvGreeting.text = "Hola, $primerNombre"
        tvAvatarInitial.text = primerNombre.take(1).uppercase()
    }
}