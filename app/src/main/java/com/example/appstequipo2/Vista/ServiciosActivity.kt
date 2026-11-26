package com.example.appstequipo2.Vista

import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
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
import com.example.appstequipo2.Adaptador.CategoriasAdaptador
import com.example.appstequipo2.Adaptador.ServiciosAdaptador
import com.example.appstequipo2.Modelo.dataCategoria
import com.example.appstequipo2.Modelo.dataServicio
import com.example.appstequipo2.R

class ServiciosActivity : AppCompatActivity() {

    private lateinit var tvGreeting: TextView
    private lateinit var tvAvatarInitial: TextView
    private lateinit var etSearchServicios: EditText
    private lateinit var tvTotalServicios: TextView
    private lateinit var rvCategoriasServicios: RecyclerView
    private lateinit var rvServicios: RecyclerView
    private lateinit var adapterCategorias: CategoriasAdaptador
    private lateinit var adapterServicios: ServiciosAdaptador

    // Datos de sesión del usuario
    private var idUsuario = 0
    private var nombres = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_servicios)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        tvGreeting = findViewById(R.id.tvGreeting)
        tvAvatarInitial = findViewById(R.id.tvAvatarInitial)
        etSearchServicios = findViewById(R.id.etSearchServicios)
        tvTotalServicios = findViewById(R.id.tvTotalServicios)
        rvCategoriasServicios = findViewById(R.id.rvCategoriasServicios)
        rvServicios = findViewById(R.id.rvServicios)

        // Saludo con el primer nombre y la inicial
        idUsuario = intent.getIntExtra("ID_USUARIO", 0)
        nombres = intent.getStringExtra("NOMBRES") ?: ""
        val primerNombre = nombres.substringBefore(" ")
        tvGreeting.text = "Hola, $primerNombre"
        tvAvatarInitial.text = primerNombre.take(1).uppercase()

        // Categorías en fila deslizable (reutilizando CategoriasAdaptador)
        rvCategoriasServicios.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        adapterCategorias = CategoriasAdaptador(emptyList()) { categoria ->
            seleccionarCategoria(categoria)
        }
        rvCategoriasServicios.adapter = adapterCategorias

        // Servicios disponibles en lista hacia abajo
        rvServicios.layoutManager = LinearLayoutManager(this)
        adapterServicios = ServiciosAdaptador(this, emptyList())
        rvServicios.adapter = adapterServicios

        configurarListeners()

        // Aquí llamas a tu presentador para traer categorías y servicios de la base:
        // presenter.iniciar()
    }

    private fun configurarListeners() {
        // Buscar al presionar la lupa del teclado
        etSearchServicios.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                ocultarTeclado()
                // presenter.buscar(etSearchServicios.text.toString().trim())
                true
            } else {
                false
            }
        }

        // Si borra todo el texto, vuelve a mostrar todos los servicios
        etSearchServicios.doAfterTextChanged { texto ->
            if (texto.isNullOrEmpty()) {
                // presenter.buscar("")
            }
        }

        findViewById<LinearLayout>(R.id.navInicio).setOnClickListener {
            val intent = Intent(this, InicioActivity::class.java)
            intent.putExtra("ID_USUARIO", idUsuario)
            intent.putExtra("NOMBRES", nombres)
            startActivity(intent)
        }

        findViewById<LinearLayout>(R.id.navSolicitudes).setOnClickListener {
            val intent = Intent(this, SolicitudesActivity::class.java) //nos lleva a las solicitudes
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

    private fun ocultarTeclado() {
        val teclado = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        teclado.hideSoftInputFromWindow(etSearchServicios.windowToken, 0)
    }

    // Lo llama el adaptador de categorías al tocar una
    fun seleccionarCategoria(categoria: dataCategoria) {
        // presenter.seleccionarCategoria(categoria)
    }

    // Al tocar un servicio disponible, abre la pantalla para describir la falla
    fun abrirSolicitarServicio(servicio: dataServicio) {
        val intent = Intent(this, SolicitarServicioActivity::class.java)
        intent.putExtra("ID_USUARIO", idUsuario)
        intent.putExtra("ID_SERVICIO", servicio.id_servicio)
        intent.putExtra("NOMBRE_SERVICIO", servicio.nombre)
        intent.putExtra("CATEGORIA_SERVICIO", servicio.categoria)
        startActivity(intent)
    }

    // Los llama el presentador al recibir datos de la base
    fun mostrarCategorias(categorias: List<dataCategoria>) {
        adapterCategorias.actualizarLista(categorias)
    }

    fun mostrarServicios(servicios: List<dataServicio>) {
        adapterServicios.actualizarLista(servicios)
        actualizarTotal(servicios.size)
    }

    fun actualizarTotal(total: Int) {
        tvTotalServicios.text = if (total == 1) "1 servicio" else "$total servicios"
    }

    fun mostrarError(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
    }
}