package com.example.appstequipo2.Adaptador

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appstequipo2.Modelo.dataServicio
import com.example.appstequipo2.R
import com.example.appstequipo2.Vista.ServiciosActivity

class ServiciosAdaptador(
    private val activity: ServiciosActivity,
    private var listaServicios: List<dataServicio>
) : RecyclerView.Adapter<ServiciosAdaptador.ServicioViewHolder>() {

    class ServicioViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvCategoria: TextView = itemView.findViewById(R.id.tvCategoriaServicio)
        val tvNombre: TextView = itemView.findViewById(R.id.tvNombreServicio)
        val tvDescripcion: TextView = itemView.findViewById(R.id.tvDescripcionServicio)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServicioViewHolder {
        val vista = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_servicio, parent, false)
        return ServicioViewHolder(vista)
    }

    override fun onBindViewHolder(holder: ServicioViewHolder, position: Int) {
        val servicio = listaServicios[position]

        holder.tvCategoria.text = servicio.categoria.uppercase()
        holder.tvNombre.text = servicio.nombre
        holder.tvDescripcion.text = servicio.descripcion

        // Al tocar un servicio disponible, abre la pantalla para describir la falla
        holder.itemView.setOnClickListener {
            activity.abrirSolicitarServicio(servicio)
        }
    }

    override fun getItemCount(): Int = listaServicios.size

    fun actualizarLista(nuevaLista: List<dataServicio>) {
        listaServicios = nuevaLista
        notifyDataSetChanged()
    }
}