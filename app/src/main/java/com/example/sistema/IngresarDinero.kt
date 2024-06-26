package com.example.sistema

import Tarjeta
import User

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.Toast


import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class IngresarDinero : AppCompatActivity() {
    private lateinit var actvIDNombreUsuario : AutoCompleteTextView
    private lateinit var rvIDSelecionTarjeta : RecyclerView
    private lateinit var etIDMontoIngresar : EditText
    private lateinit var btnIDIngresar : Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ingresar_dinero)

        actvIDNombreUsuario = findViewById(R.id.actvRDNombreUsuario)
        rvIDSelecionTarjeta = findViewById(R.id.rvRDSelecionTarjeta)
        etIDMontoIngresar = findViewById(R.id.etRDMontoRetirar)
        btnIDIngresar = findViewById(R.id.btnRDRetirar)

        //ponemos el adapater de todos los posibles nombres de personas al autocomplete textview
        listaUsuarios()

        // Configurar el LinearLayoutManager para el RecyclerView
        rvIDSelecionTarjeta.layoutManager = LinearLayoutManager(this)

        actvIDNombreUsuario.setOnItemClickListener { _, _, position, _ ->
            val nombreUsuarioSelecionado = actvIDNombreUsuario.adapter.getItem(position) as String

            val tarjetai = Tarjeta()
            tarjetai.getTarjetasUsuario(nombreUsuarioSelecionado) {tarjetas ->
                mostrarTarjetas(tarjetas)
            }
        }

        btnIDIngresar.setOnClickListener {
            val montoIngresarText = etIDMontoIngresar.text.toString()
            if (montoIngresarText.isNotEmpty()) {
                val montoIngresar = montoIngresarText.toDouble()

                if (rvIDSelecionTarjeta.adapter != null && rvIDSelecionTarjeta.adapter!!.itemCount > 0) {
                    val tarjetaAdapter = rvIDSelecionTarjeta.adapter as TarjetaAdapter
                    val tarjetaSeleccionada = tarjetaAdapter.tarjetas[tarjetaAdapter.obtenerSelectedPosition()]
                    tarjetaSeleccionada.ingresarDinero(montoIngresar)
                    // Notificar al adaptador de que los datos han cambiado
                    tarjetaAdapter.notifyDataSetChanged()
                    // Aquí puedes agregar cualquier otra acción después de ingresar el dinero, como actualizar la interfaz de usuario, etc.
                } else {
                    Toast.makeText(this, "No hay tarjetas seleccionadas", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Ingrese un monto válido", Toast.LENGTH_SHORT).show()
            }
        }

    }

    fun listaUsuarios(){
        val user = User()
        user.getListaUsuarios { listaUsuarios ->
            runOnUiThread {
                val adapter = ArrayAdapter(
                    this@IngresarDinero,
                    android.R.layout.simple_dropdown_item_1line,
                    listaUsuarios
                )
                actvIDNombreUsuario.setAdapter(adapter)
            }
        }
    }


    private fun mostrarTarjetas(tarjetas: List<Tarjeta>) {
        if (tarjetas.isNotEmpty()) {
            val adapter = TarjetaAdapter(tarjetas,RecyclerView.NO_POSITION)
            rvIDSelecionTarjeta.adapter = adapter
            adapter.notifyDataSetChanged()
        } else {
            // Aquí puedes manejar el caso en el que no hay tarjetas para mostrar
        }
    }

}