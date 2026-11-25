package com.example.appstequipo2.Vista

import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
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
import com.example.appstequipo2.Adaptador.EquipoAdaptador
import com.example.appstequipo2.Modelo.dataCategoria
import com.example.appstequipo2.Modelo.dataEquipo
import com.example.appstequipo2.Presentador.clsInicioPresentador
import com.example.appstequipo2.R

class InicioActivity : AppCompatActivity() {
    private lateinit var presenter: clsInicioPresentador
    private lateinit var tvGreeting: TextView
    private lateinit var tvAvatarInitial: TextView
    private lateinit var etSearch: EditText
    private lateinit var tvTotalCount: TextView
    private lateinit var rvCategorias: RecyclerView
    private lateinit var rvEquipment: RecyclerView
    private lateinit var adapterCategorias: CategoriasAdaptador
    private lateinit var adapterEquipos: EquipoAdaptador

    // Datos del usuario que inició sesión
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
        etSearch = findViewById(R.id.etSearch)
        tvTotalCount = findViewById(R.id.tvTotalCount)
        rvCategorias = findViewById(R.id.rvCategorias)
        rvEquipment = findViewById(R.id.rvEquipment)

        // Saludo con el primer nombre y la inicial
        idUsuario = intent.getIntExtra("ID_USUARIO", 0)
        val nombres = intent.getStringExtra("NOMBRES") ?: ""
        val primerNombre = nombres.substringBefore(" ")
        tvGreeting.text = "Hola, $primerNombre"
        tvAvatarInitial.text = primerNombre.take(1).uppercase()

        // Categorías en fila deslizable hacia los lados
        rvCategorias.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        adapterCategorias = CategoriasAdaptador(this, emptyList())
        rvCategorias.adapter = adapterCategorias

        // Equipos en lista hacia abajo
        rvEquipment.layoutManager = LinearLayoutManager(this)
        adapterEquipos = EquipoAdaptador(this, emptyList())
        rvEquipment.adapter = adapterEquipos

        presenter = clsInicioPresentador(this)
        configurarListeners()
        presenter.iniciar()
    }

    private fun configurarListeners() {
        // Buscar al presionar la lupa del teclado
        etSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                ocultarTeclado()
                presenter.buscar(etSearch.text.toString().trim())
                true
            } else {
                false
            }
        }

        // Si borra todo el texto, vuelve a mostrar todos los equipos
        etSearch.doAfterTextChanged { texto ->
            if (texto.isNullOrEmpty()) {
                presenter.buscar("")
            }
        }
    }

    private fun ocultarTeclado() {
        val teclado = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        teclado.hideSoftInputFromWindow(etSearch.windowToken, 0)
    }

    // Los llaman los adaptadores
    fun seleccionarCategoria(categoria: dataCategoria) {
        presenter.seleccionarCategoria(categoria)
    }

    fun seleccionarEquipo(equipo: dataEquipo) {
        presenter.seleccionarEquipo(equipo)
    }

    // Los llama el presentador
    fun mostrarCategorias(categorias: List<dataCategoria>) {
        adapterCategorias.actualizarLista(categorias)
    }

    fun mostrarEquipos(equipos: List<dataEquipo>) {
        adapterEquipos.actualizarLista(equipos)
    }

    fun actualizarTotal(total: Int) {
        tvTotalCount.text = if (total == 1) "1 equipo" else "$total equipos"
    }

    fun abrirSolicitarPrestamo(idEquipo: Int, nombre: String, codigo: String) {
        val intent = Intent(this, SolicitarPrestamoActivity::class.java)
        intent.putExtra("ID_USUARIO", idUsuario)
        intent.putExtra("ID_EQUIPO", idEquipo)
        intent.putExtra("NOMBRE_EQUIPO", nombre)
        intent.putExtra("CODIGO_EQUIPO", codigo)
        startActivity(intent)
    }

    fun mostrarError(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
    }
}