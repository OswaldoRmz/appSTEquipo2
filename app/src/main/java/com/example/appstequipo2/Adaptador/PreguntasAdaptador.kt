package com.example.appstequipo2.Adaptador

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appstequipo2.Modelo.dataPregunta
import com.example.appstequipo2.R

class PreguntasAdaptador(
    private var listaPreguntas: List<dataPregunta>
) : RecyclerView.Adapter<PreguntasAdaptador.PreguntaViewHolder>() {

    class PreguntaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvPregunta: TextView = itemView.findViewById(R.id.tvPregunta)
        val tvRespuesta: TextView = itemView.findViewById(R.id.tvRespuesta)
        val contenedorRespuesta: LinearLayout = itemView.findViewById(R.id.contenedorRespuesta)
        val ivFlecha: ImageView = itemView.findViewById(R.id.ivFlecha)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PreguntaViewHolder {
        val vista = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_pregunta, parent, false)
        return PreguntaViewHolder(vista)
    }

    override fun onBindViewHolder(holder: PreguntaViewHolder, position: Int) {
        val item = listaPreguntas[position]

        holder.tvPregunta.text = item.pregunta
        holder.tvRespuesta.text = item.respuesta

        // Muestra u oculta la respuesta según el estado de la tarjeta
        if (item.desplegada) {
            holder.contenedorRespuesta.visibility = View.VISIBLE
            holder.ivFlecha.rotation = 180f
        } else {
            holder.contenedorRespuesta.visibility = View.GONE
            holder.ivFlecha.rotation = 0f
        }

        // Al tocar la tarjeta, cambia de abierto a cerrado (o al revés) y refresca esa fila
        holder.itemView.setOnClickListener {
            item.desplegada = !item.desplegada
            notifyItemChanged(position)
        }
    }

    override fun getItemCount(): Int = listaPreguntas.size

    fun actualizarLista(nuevaLista: List<dataPregunta>) {
        listaPreguntas = nuevaLista
        notifyDataSetChanged()
    }
}