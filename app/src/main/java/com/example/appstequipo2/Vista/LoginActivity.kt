package com.example.appstequipo2.Vista

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.appstequipo2.Presentador.clsLoginPresentador
import com.example.appstequipo2.R

class LoginActivity : AppCompatActivity() {
    private lateinit var presenter: clsLoginPresentador
    private lateinit var etCorreo: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        etCorreo = findViewById(R.id.etCorreo)
        etPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnLogin)

        // Si viene de crear cuenta, el correo ya aparece escrito
        val correo = intent.getStringExtra("CORREO") ?: ""
        if (correo.isNotEmpty()) {
            etCorreo.setText(correo)
            etPassword.requestFocus()
        }

        presenter = clsLoginPresentador(this)
        configurarListeners()
    }

    private fun configurarListeners() {
        btnLogin.setOnClickListener {
            presenter.iniciarSesion(
                etCorreo.text.toString().trim(),
                etPassword.text.toString()
            )
        }
    }

    fun mostrarCargando(cargando: Boolean) {
        btnLogin.isEnabled = !cargando
        btnLogin.text = if (cargando) "Ingresando..." else "Iniciar sesión"
    }

    // Abre la principal de la app y cierra las pantallas anteriores
    // para que "atrás" no regrese al login
    fun abrirInicio(idUsuario: Int, nombres: String) {
        val intent = Intent(this, InicioActivity::class.java)
        intent.putExtra("ID_USUARIO", idUsuario)
        intent.putExtra("NOMBRES", nombres)
        startActivity(intent)
        finishAffinity()
    }

    fun mostrarError(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
    }
}