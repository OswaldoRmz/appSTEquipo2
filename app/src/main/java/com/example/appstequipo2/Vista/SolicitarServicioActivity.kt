package com.example.appstequipo2.Vista

import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.appstequipo2.Modelo.dataOrdenServicio
import com.example.appstequipo2.R
import com.google.android.material.button.MaterialButton

class SolicitarServicioActivity : AppCompatActivity() {

    private var idUsuario = 0
    private var idServicio = 0
    private var nombreServicio = ""
    private var categoriaServicio = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_solicitar_servicio)

        // Recibimos los datos del servicio seleccionado en la pantalla anterior
        idUsuario = intent.getIntExtra("ID_USUARIO", 0)
        idServicio = intent.getIntExtra("ID_SERVICIO", 0)
        nombreServicio = intent.getStringExtra("NOMBRE_SERVICIO") ?: "Servicio técnico"
        categoriaServicio = intent.getStringExtra("CATEGORIA_SERVICIO") ?: "SOPORTE"

        // Mostramos arriba el servicio elegido
        val tvCategoria = findViewById<TextView>(R.id.tvCategoriaSeleccionada)
        val tvServicio = findViewById<TextView>(R.id.tvServicioSeleccionado)
        tvCategoria.text = categoriaServicio.uppercase()
        tvServicio.text = nombreServicio

        val etEquipoReceptor = findViewById<EditText>(R.id.etEquipoReceptor)
        val etFallaReportada = findViewById<EditText>(R.id.etFallaReportada)
        val btnEnviar = findViewById<MaterialButton>(R.id.btnEnviarServicio)

        // Flecha de regresar
        findViewById<ImageButton>(R.id.btnRegresarServicio).setOnClickListener {
            finish()
        }

        // Al presionar el botón verde de enviar
        btnEnviar.setOnClickListener {
            val equipo = etEquipoReceptor.text.toString().trim()
            val falla = etFallaReportada.text.toString().trim()

            if (equipo.isEmpty()) {
                etEquipoReceptor.error = "Indica qué equipo o cosa tiene el problema"
                return@setOnClickListener
            }
            if (falla.isEmpty()) {
                etFallaReportada.error = "Describe la falla reportada"
                return@setOnClickListener
            }

            // Armamos el objeto exacto que espera tu PHP
            val nuevaOrden = dataOrdenServicio(
                id_cliente = idUsuario,
                id_servicio = if (idServicio > 0) idServicio else null,
                equipo_receptor = equipo,
                falla_reportada = falla
            )

            // Aquí llamas a tu presentador para hacer el POST:
            // presenter.crearOrden(nuevaOrden)
            Toast.makeText(this, "Solicitud enviada para: ${nuevaOrden.equipo_receptor}", Toast.LENGTH_LONG).show()
            finish()
        }
    }

    //metodos que llama el presentador
    fun ordenCreadaConExito(folio: String) {
        Toast.makeText(this, "Orden creada. Folio: $folio", Toast.LENGTH_LONG).show()
        finish()
    }

    fun mostrarError(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
    }
}