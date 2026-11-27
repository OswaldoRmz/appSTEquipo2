package com.example.appstequipo2.Vista

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.appstequipo2.Modelo.dataPerfilUsuario
import com.example.appstequipo2.R
import com.google.android.material.card.MaterialCardView

class MiCuentaActivity : AppCompatActivity() {

    private lateinit var tvInicialesPerfil: TextView
    private lateinit var tvNombreCompletoPerfil: TextView
    private lateinit var tvUsuarioPerfil: TextView
    private lateinit var tvCorreoPerfil: TextView
    private lateinit var tvRolPerfil: TextView
    private lateinit var tvDireccionPerfil: TextView
    private lateinit var tvTelefonoPerfil: TextView

    private var idUsuario = 0
    private var nombres = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_mi_cuenta)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        idUsuario = intent.getIntExtra("ID_USUARIO", 0)
        nombres = intent.getStringExtra("NOMBRES") ?: ""

        // Enlazamos los campos dinámicos del XML
        tvInicialesPerfil = findViewById(R.id.tvInicialesPerfil)
        tvNombreCompletoPerfil = findViewById(R.id.tvNombreCompletoPerfil)
        tvUsuarioPerfil = findViewById(R.id.tvUsuarioPerfil)
        tvCorreoPerfil = findViewById(R.id.tvCorreoPerfil)
        tvRolPerfil = findViewById(R.id.tvRolPerfil)
        tvDireccionPerfil = findViewById(R.id.tvDireccionPerfil)
        tvTelefonoPerfil = findViewById(R.id.tvTelefonoPerfil)

        // Muestra el nombre preliminar mientras responde la base de datos
        if (nombres.isNotEmpty()) {
            tvNombreCompletoPerfil.text = nombres
            tvInicialesPerfil.text = obtenerIniciales(nombres, "")
        }

        configurarListeners()

        // Aquí llamas a tu presentador para que pida los datos del usuario por idUsuario:
        // presenter.cargarPerfil(idUsuario)
    }

    private fun configurarListeners() {
        // Opción de seguridad y contraseña
        findViewById<LinearLayout>(R.id.btnSeguridadContrasena).setOnClickListener {
            Toast.makeText(this, "Próximamente: Cambiar contraseña", Toast.LENGTH_SHORT).show()
        }

        // Botón Cerrar Sesión: regresa al Login y borra el historial de pantallas
        findViewById<MaterialCardView>(R.id.btnCerrarSesion).setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }

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

        findViewById<LinearLayout>(R.id.navSolicitudes).setOnClickListener {
            val intent = Intent(this, SolicitudesActivity::class.java)
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

    // --- MÉTODO QUE LLAMA EL PRESENTADOR CON LOS DATOS REALES DE LA BD ---
    fun mostrarPerfil(perfil: dataPerfilUsuario) {
        val nombreCompleto = "${perfil.nombres} ${perfil.apellidos}".trim()
        nombres = nombreCompleto

        tvInicialesPerfil.text = obtenerIniciales(perfil.nombres, perfil.apellidos)
        tvNombreCompletoPerfil.text = nombreCompleto
        tvUsuarioPerfil.text = if (perfil.usuario.isNotEmpty()) {
            if (perfil.usuario.startsWith("@")) perfil.usuario else "@${perfil.usuario}"
        } else {
            "@${perfil.nombres.substringBefore(" ").lowercase()}"
        }
        tvCorreoPerfil.text = perfil.correo.ifEmpty { "No registrado" }
        tvRolPerfil.text = perfil.rol.ifEmpty { "Usuario" }
        tvDireccionPerfil.text = perfil.direccion.ifEmpty { "Sin dirección registrada" }
        tvTelefonoPerfil.text = perfil.telefono.ifEmpty { "Sin teléfono registrado" }
    }

    private fun obtenerIniciales(nombres: String, apellidos: String): String {
        val i1 = nombres.trim().firstOrNull()?.uppercase() ?: ""
        val i2 = apellidos.trim().firstOrNull()?.uppercase()
            ?: nombres.trim().substringAfter(" ", "").firstOrNull()?.uppercase()
            ?: ""
        return (i1 + i2).ifEmpty { "U" }
    }

    fun mostrarError(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
    }
}