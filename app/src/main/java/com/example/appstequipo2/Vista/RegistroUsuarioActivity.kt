package com.example.appstequipo2.Vista

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.appstequipo2.Presentador.clsRegistroUsuarioPresentador
import com.example.appstequipo2.R

class RegistroUsuarioActivity : AppCompatActivity() {
    private lateinit var presenter: clsRegistroUsuarioPresentador
    private lateinit var etNombres: EditText
    private lateinit var etApellidos: EditText
    private lateinit var etTelefono: EditText
    private lateinit var etCorreo: EditText
    private lateinit var etDireccion: EditText
    private lateinit var etPassword: EditText
    private lateinit var cbTerms: CheckBox
    private lateinit var btnCreateAccount: Button
    private lateinit var tvLoginLink: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registro_usuarios)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        etNombres = findViewById(R.id.etNombres)
        etApellidos = findViewById(R.id.etApellidos)
        etTelefono = findViewById(R.id.etTelefono)
        etCorreo = findViewById(R.id.etCorreo)
        etDireccion = findViewById(R.id.etDireccion)
        etPassword = findViewById(R.id.etPassword)
        cbTerms = findViewById(R.id.cbTerms)
        btnCreateAccount = findViewById(R.id.btnCreateAccount)
        tvLoginLink = findViewById(R.id.tvLoginLink)

        presenter = clsRegistroUsuarioPresentador(this)
        configurarListeners()
    }

    private fun configurarListeners() {
        btnCreateAccount.setOnClickListener {
            presenter.crearCuenta(
                etNombres.text.toString().trim(),
                etApellidos.text.toString().trim(),
                etTelefono.text.toString().trim(),
                etCorreo.text.toString().trim(),
                etPassword.text.toString(),
                etDireccion.text.toString().trim(),
                cbTerms.isChecked
            )
        }

        tvLoginLink.setOnClickListener { presenter.irALogin() }
    }

    fun mostrarCargando(cargando: Boolean) {
        btnCreateAccount.isEnabled = !cargando
        btnCreateAccount.text = if (cargando) "Creando cuenta..." else "Crear mi cuenta"
    }

    // La llama el presentador cuando la API creó la cuenta
    fun cuentaCreada(mensaje: String, correo: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
        abrirLogin(correo)
    }

    // Abre el login (con el correo para escribirlo ahí) y cierra el registro
    fun abrirLogin(correo: String) {
        val intent = Intent(this, LoginActivity::class.java)
        intent.putExtra("CORREO", correo)
        startActivity(intent)
        finish()
    }

    fun mostrarError(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
    }
}