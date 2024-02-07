package com.example.sistema

import Tarjeta
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TarjetaAdapter(private val tarjetas: List<Tarjeta>): RecyclerView.Adapter<TarjetaAdapter.TarjetaViewHolder>(){

    inner class TarjetaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val numTarjetaTextView: TextView = itemView.findViewById(R.id.numTarjetaTextView)
        val fechaVencimientoTextView: TextView = itemView.findViewById(R.id.fechaVencimientoTextView)
        val cvvTextView: TextView = itemView.findViewById(R.id.cvvTextView)
        val saldoTextView: TextView = itemView.findViewById(R.id.saldoTextView)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TarjetaViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_tarjeta,parent,false)
        return TarjetaViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TarjetaViewHolder, position: Int) {
        val currentItem = tarjetas[position]
        holder.numTarjetaTextView.text = currentItem.numTarjeta
        holder.fechaVencimientoTextView.text = currentItem.fechaVencimiento.toString()
        holder.cvvTextView.text = currentItem.cvv
        holder.saldoTextView.text = currentItem.dineroDisponible.toString()
    }

    override fun getItemCount() = tarjetas.size

}