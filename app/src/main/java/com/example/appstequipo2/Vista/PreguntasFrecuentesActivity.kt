package com.example.appstequipo2.Vista

import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appstequipo2.Adaptador.PreguntasAdaptador
import com.example.appstequipo2.Modelo.dataPregunta
import com.example.appstequipo2.R

class PreguntasFrecuentesActivity : AppCompatActivity() {

    private lateinit var rvPreguntasFrecuentes: RecyclerView
    private lateinit var adapterPreguntas: PreguntasAdaptador
    private lateinit var etBuscarPregunta: EditText
    private lateinit var tvContadorPreguntas: TextView

    // Lista original recibida desde la base de datos para filtrar al escribir
    private var listaCompleta: List<dataPregunta> = emptyList()

    // Datos de sesión del usuario
    private var idUsuario = 0
    private var nombres = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preguntas_frecuentes)

        // Recibimos los datos del usuario que inició sesión
        idUsuario = intent.getIntExtra("ID_USUARIO", 0)
        nombres = intent.getStringExtra("NOMBRES") ?: ""

        // Enlazamos los elementos del XML
        rvPreguntasFrecuentes = findViewById(R.id.rvPreguntasFrecuentes)
        etBuscarPregunta = findViewById(R.id.etBuscarPregunta)
        tvContadorPreguntas = findViewById(R.id.tvContadorPreguntas)

        // Configuramos el RecyclerView
        rvPreguntasFrecuentes.layoutManager = LinearLayoutManager(this)
        adapterPreguntas = PreguntasAdaptador(emptyList())
        rvPreguntasFrecuentes.adapter = adapterPreguntas

        configurarListeners()

        // Aquí llamas a tu presentador para que pida las preguntas al PHP:
        // presenter.iniciar()
    }

    private fun configurarListeners() {
        // Flecha de regresar
        findViewById<ImageButton>(R.id.btnRegresarFaq).setOnClickListener {
            finish()
        }

        // Ícono de audífonos de arriba a la derecha
        findViewById<ImageButton>(R.id.btnSoporteDirecto).setOnClickListener {
            Toast.makeText(this, "Contactando a soporte técnico...", Toast.LENGTH_SHORT).show()
        }

        // Ocultar teclado al presionar la lupa
        etBuscarPregunta.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                ocultarTeclado()
                filtrarPreguntas(etBuscarPregunta.text.toString().trim())
                true
            } else {
                false
            }
        }

        // Filtrar en tiempo real mientras se escribe o borra texto
        etBuscarPregunta.doAfterTextChanged { texto ->
            filtrarPreguntas(texto.toString().trim())
        }

        // --- NAVEGACIÓN DEL MENÚ INFERIOR ---
        // Cada botón abre su pantalla y reenvía ID_USUARIO y NOMBRES para no perder la sesión.
        // Al botón "Soporte" no le ponemos clic porque ya estamos en esta pantalla.

        findViewById<LinearLayout>(R.id.navInicio).setOnClickListener {
            val intent = Intent(this, InicioActivity::class.java)//lleva al inicio
            intent.putExtra("ID_USUARIO", idUsuario)
            intent.putExtra("NOMBRES", nombres)
            startActivity(intent)
        }

        findViewById<LinearLayout>(R.id.navServicios).setOnClickListener {
            val intent = Intent(this, ServiciosActivity::class.java)//lleva a los servicios
            intent.putExtra("ID_USUARIO", idUsuario)
            intent.putExtra("NOMBRES", nombres)
            startActivity(intent)
        }

        findViewById<LinearLayout>(R.id.navSolicitudes).setOnClickListener {
            val intent = Intent(this, SolicitudesActivity::class.java)//lleva a las solicitudes
            intent.putExtra("ID_USUARIO", idUsuario)
            intent.putExtra("NOMBRES", nombres)
            startActivity(intent)
        }
    }

    private fun filtrarPreguntas(texto: String) {
        val filtradas = if (texto.isEmpty()) {
            listaCompleta
        } else {
            listaCompleta.filter {
                it.pregunta.contains(texto, ignoreCase = true) ||
                        it.respuesta.contains(texto, ignoreCase = true)
            }
        }
        adapterPreguntas.actualizarLista(filtradas)
        actualizarTotal(filtradas.size)
    }

    private fun ocultarTeclado() {
        val teclado = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        teclado.hideSoftInputFromWindow(etBuscarPregunta.windowToken, 0)
    }

    // --- MÉTODOS QUE LLAMA EL PRESENTADOR AL RECIBIR EL JSON DEL PHP ---
    fun mostrarPreguntas(preguntas: List<dataPregunta>) {
        listaCompleta = preguntas
        adapterPreguntas.actualizarLista(preguntas)
        actualizarTotal(preguntas.size)
    }

    fun actualizarTotal(total: Int) {
        tvContadorPreguntas.text = if (total == 1) "1 pregunta" else "$total preguntas"
    }

    fun mostrarError(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
    }
}