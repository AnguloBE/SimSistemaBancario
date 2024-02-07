package com.example.sistema

import Tarjeta
import User
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
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

        actvIDNombreUsuario = findViewById(R.id.actvIDNombreUsuario)
        rvIDSelecionTarjeta = findViewById(R.id.rvIDSelecionTarjeta)
        etIDMontoIngresar = findViewById(R.id.etIDMontoIngresar)
        btnIDIngresar = findViewById(R.id.btnIDIngresar)

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
            val adapter = TarjetaAdapter(tarjetas)
            rvIDSelecionTarjeta.adapter = adapter
            adapter.notifyDataSetChanged()
        } else {
            // Aquí puedes manejar el caso en el que no hay tarjetas para mostrar
        }
    }

}