package com.example.appstequipo2.Adaptador
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appstequipo2.Modelo.dataEquipo
import com.example.appstequipo2.R
import com.example.appstequipo2.Vista.InicioActivity

class EquipoAdaptador(private val activity: InicioActivity, private var listaEquipos: List<dataEquipo>) : RecyclerView.Adapter<EquipoAdaptador.EquipoViewHolder>() {
    class EquipoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivIconoEquipo: ImageView = itemView.findViewById(R.id.ivIconoEquipo)
        val tvNombreEquipo: TextView = itemView.findViewById(R.id.tvNombreEquipo)
        val tvCodigoEquipo: TextView = itemView.findViewById(R.id.tvCodigoEquipo)
        val tvDisponibles: TextView = itemView.findViewById(R.id.tvDisponibles)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EquipoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_equipo_disponible, parent, false)
        return EquipoViewHolder(view)
    }

    override fun onBindViewHolder(holder: EquipoViewHolder, position: Int) {
        val equipo = listaEquipos[position]

        holder.tvNombreEquipo.text = equipo.nombre

        // Ej. "Almacén de Tecnología · EQ-001" (si no tiene ubicación, muestra la categoría)
        val lugar = if (!equipo.ubicacion.isNullOrEmpty()) equipo.ubicacion else equipo.categoria
        holder.tvCodigoEquipo.text = "$lugar · ${equipo.codigo}"

        holder.tvDisponibles.text = "• ${equipo.estado}"
        holder.ivIconoEquipo.setImageResource(IconosAdaptador.obtenerIcono(equipo.categoria))

        // Al tocar la tarjeta se abre Solicitar préstamo con este equipo
        holder.itemView.setOnClickListener {
            activity.seleccionarEquipo(equipo)
        }
    }

    override fun getItemCount(): Int = listaEquipos.size

    fun actualizarLista(nuevaLista: List<dataEquipo>) {
        listaEquipos = nuevaLista
        notifyDataSetChanged()
    }
}