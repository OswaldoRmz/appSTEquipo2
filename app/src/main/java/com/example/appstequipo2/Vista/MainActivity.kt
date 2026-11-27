package com.example.appstequipo2.Vista

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.appstequipo2.Presentador.clsPrincipalPresentador
import com.example.appstequipo2.R

class MainActivity : AppCompatActivity() {
    private lateinit var presenter: clsPrincipalPresentador
    private lateinit var btnIniciarSesion: LinearLayout
    private lateinit var btnCrearCuenta: TextView
    private lateinit var btnAyuda: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        btnIniciarSesion = findViewById(R.id.btnIniciarSesion)
        btnCrearCuenta = findViewById(R.id.btnCrearCuenta)
        btnAyuda = findViewById (R.id.btnAyuda)

        presenter = clsPrincipalPresentador(this)
        configurarListeners()
    }

    // Eventos de los botones de la pantalla principal
    private fun configurarListeners() {
        btnIniciarSesion.setOnClickListener { presenter.irALogin() }
        btnCrearCuenta.setOnClickListener { presenter.irARegistro() }
        btnAyuda.setOnClickListener { presenter.irAyuda() }
    }


    // Los llama el presentador
    fun abrirLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

    fun abrirRegistro() {
        val intent = Intent(this, RegistroUsuarioActivity::class.java)
        startActivity(intent)
    }
    fun abrirAyuda() {
        val intent = Intent(this, PreguntasFrecuentesActivity::class.java)
        startActivity(intent)
    }
}