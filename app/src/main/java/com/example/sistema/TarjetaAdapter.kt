package com.example.sistema

import Tarjeta
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TarjetaAdapter(val tarjetas: List<Tarjeta>, var selectedPosition: Int): RecyclerView.Adapter<TarjetaAdapter.TarjetaViewHolder>(){

    inner class TarjetaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val numTarjetaTextView: TextView = itemView.findViewById(R.id.numTarjetaTextView)
        val fechaVencimientoTextView: TextView = itemView.findViewById(R.id.fechaVencimientoTextView)
        val cvvTextView: TextView = itemView.findViewById(R.id.cvvTextView)
        val saldoTextView: TextView = itemView.findViewById(R.id.saldoTextView)

        init {
            itemView.setOnClickListener {
                val previousSelectedPosition = selectedPosition
                selectedPosition = adapterPosition
                notifyItemChanged(previousSelectedPosition)
                notifyItemChanged(selectedPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TarjetaViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_tarjeta,parent,false)
        return TarjetaViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TarjetaViewHolder, position: Int) {
        val currentItem = tarjetas[position]
        holder.numTarjetaTextView.text = "Numero de tarjeta: ${currentItem.numTarjeta}"
        holder.fechaVencimientoTextView.text = "Fecha de vencimiento: ${currentItem.fechaVencimiento.toString()}"
        holder.cvvTextView.text = "CVV: ${currentItem.cvv}"
        holder.saldoTextView.text = "Saldo: ${currentItem.dineroDisponible.toString()}"

        // Cambiar el fondo del elemento de tarjeta según su estado de selección
        if (selectedPosition == position) {
            holder.itemView.setBackgroundResource(R.drawable.border_selected)
        } else {
            holder.itemView.setBackgroundResource(R.drawable.border_custom)
        }
    }
    fun obtenerSelectedPosition(): Int {
        return selectedPosition
    }

    override fun getItemCount() = tarjetas.size
}

