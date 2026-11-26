package com.example.appstequipo2.Adaptador

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appstequipo2.Modelo.dataSolicitudHistorial
import com.example.appstequipo2.R

class SolicitudesAdaptador(
    private var listaSolicitudes: List<dataSolicitudHistorial>
) : RecyclerView.Adapter<SolicitudesAdaptador.SolicitudViewHolder>() {

    class SolicitudViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvFolio: TextView = itemView.findViewById(R.id.tvFolioSolicitud)
        val tvEstado: TextView = itemView.findViewById(R.id.tvEstadoSolicitud)
        val tvTitulo: TextView = itemView.findViewById(R.id.tvTituloSolicitud)
        val tvSubtitulo: TextView = itemView.findViewById(R.id.tvSubtituloSolicitud)
        val tvFecha: TextView = itemView.findViewById(R.id.tvFechaSolicitud)
        val tvVerMas: TextView = itemView.findViewById(R.id.tvVerMas)
        val ivFlecha: ImageView = itemView.findViewById(R.id.ivFlechaSolicitud)
        val contenedorDetalles: LinearLayout = itemView.findViewById(R.id.contenedorDetallesSolicitud)
        val tvDetalle1: TextView = itemView.findViewById(R.id.tvDetalle1)
        val tvDetalle2: TextView = itemView.findViewById(R.id.tvDetalle2)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SolicitudViewHolder {
        val vista = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_solicitud, parent, false)
        return SolicitudViewHolder(vista)
    }

    override fun onBindViewHolder(holder: SolicitudViewHolder, position: Int) {
        val item = listaSolicitudes[position]

        holder.tvFolio.text = "${item.tipo} • ${item.folio}"
        holder.tvEstado.text = item.estado
        holder.tvTitulo.text = item.titulo
        holder.tvSubtitulo.text = item.subtitulo
        holder.tvFecha.text = item.fecha
        holder.tvDetalle1.text = item.detalleExtra1
        holder.tvDetalle2.text = item.detalleExtra2

        // Color del texto de estado según si está resuelto o activo
        val esResuelta = item.estado.equals("Entregado", true) ||
                item.estado.equals("Devuelto", true) ||
                item.estado.equals("Finalizado", true) ||
                item.estado.equals("Cancelado", true)

        if (esResuelta) {
            holder.tvEstado.setTextColor(Color.parseColor("#475569"))
        } else {
            holder.tvEstado.setTextColor(Color.parseColor("#188653"))
        }

        // Control de despliegue al darle clic en "Ver más"
        if (item.desplegada) {
            holder.contenedorDetalles.visibility = View.VISIBLE
            holder.tvVerMas.text = "Ver menos"
            holder.ivFlecha.rotation = 180f
        } else {
            holder.contenedorDetalles.visibility = View.GONE
            holder.tvVerMas.text = "Ver más"
            holder.ivFlecha.rotation = 0f
        }

        holder.itemView.setOnClickListener {
            item.desplegada = !item.desplegada
            notifyItemChanged(position)
        }
    }

    override fun getItemCount(): Int = listaSolicitudes.size

    fun actualizarLista(nuevaLista: List<dataSolicitudHistorial>) {
        listaSolicitudes = nuevaLista
        notifyDataSetChanged()
    }
}