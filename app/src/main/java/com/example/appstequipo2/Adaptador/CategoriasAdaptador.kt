package com.example.appstequipo2.Adaptador

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appstequipo2.Modelo.dataCategoria
import com.example.appstequipo2.R
import com.google.android.material.card.MaterialCardView

class CategoriasAdaptador(
    private var listaCategorias: List<dataCategoria>,
    private val alSeleccionar: (dataCategoria) -> Unit
) : RecyclerView.Adapter<CategoriasAdaptador.CategoriaViewHolder>() {

    private var idSeleccionada = 0

    class CategoriaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardCategoria: MaterialCardView = itemView.findViewById(R.id.cardCategoria)
        val ivIconoCategoria: ImageView = itemView.findViewById(R.id.ivIconoCategoria)
        val tvNombreCategoria: TextView = itemView.findViewById(R.id.tvNombreCategoria)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_categoria, parent, false)
        return CategoriaViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoriaViewHolder, position: Int) {
        val categoria = listaCategorias[position]

        holder.tvNombreCategoria.text = categoria.nombre
        holder.ivIconoCategoria.setImageResource(IconosAdaptador.obtenerIcono(categoria.nombre))

        if (categoria.idCategoria == idSeleccionada) {
            holder.cardCategoria.setCardBackgroundColor(Color.parseColor("#0D2847"))
            holder.cardCategoria.setStrokeColor(Color.parseColor("#0D2847"))
            holder.ivIconoCategoria.imageTintList = ColorStateList.valueOf(Color.parseColor("#FFFFFF"))
            holder.tvNombreCategoria.setTextColor(Color.parseColor("#188653"))
            holder.tvNombreCategoria.setTypeface(null, Typeface.BOLD)
        } else {
            holder.cardCategoria.setCardBackgroundColor(Color.parseColor("#FFFFFF"))
            holder.cardCategoria.setStrokeColor(Color.parseColor("#E2E8F0"))
            holder.ivIconoCategoria.imageTintList = ColorStateList.valueOf(Color.parseColor("#475569"))
            holder.tvNombreCategoria.setTextColor(Color.parseColor("#64748B"))
            holder.tvNombreCategoria.setTypeface(null, Typeface.NORMAL)
        }

        holder.itemView.setOnClickListener {
            idSeleccionada = categoria.idCategoria
            notifyDataSetChanged()
            alSeleccionar(categoria)
        }
    }

    override fun getItemCount(): Int = listaCategorias.size

    fun actualizarLista(nuevaLista: List<dataCategoria>) {
        listaCategorias = nuevaLista
        notifyDataSetChanged()
    }
}