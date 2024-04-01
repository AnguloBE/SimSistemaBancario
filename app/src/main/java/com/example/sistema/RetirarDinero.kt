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


class RetirarDinero : AppCompatActivity() {
    private lateinit var actvRDNombreUsuario : AutoCompleteTextView
    private lateinit var rvRDSelecionTarjeta : RecyclerView
    private lateinit var etRDMontoIngresar : EditText
    private lateinit var btnRDIngresar : Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_retirar_dinero)

        actvRDNombreUsuario = findViewById(R.id.actvRDNombreUsuario)
        rvRDSelecionTarjeta = findViewById(R.id.rvRDSelecionTarjeta)
        etRDMontoIngresar = findViewById(R.id.etRDMontoRetirar)
        btnRDIngresar = findViewById(R.id.btnRDRetirar)

        //ponemos el adapater de todos los posibles nombres de personas al autocomplete textview
        listaUsuarios()

        // Configurar el LinearLayoutManager para el RecyclerView
        rvRDSelecionTarjeta.layoutManager = LinearLayoutManager(this)

        actvRDNombreUsuario.setOnItemClickListener { _, _, position, _ ->
            val nombreUsuarioSelecionado = actvRDNombreUsuario.adapter.getItem(position) as String

            val tarjetai = Tarjeta()
            tarjetai.getTarjetasUsuario(nombreUsuarioSelecionado) {tarjetas ->
                mostrarTarjetas(tarjetas)
            }
        }

        btnRDIngresar.setOnClickListener {
            val montoIngresarText = etRDMontoIngresar.text.toString()
            if (montoIngresarText.isNotEmpty()) {
                val montoIngresar = montoIngresarText.toDouble()

                if (rvRDSelecionTarjeta.adapter != null && rvRDSelecionTarjeta.adapter!!.itemCount > 0) {
                    val tarjetaAdapter = rvRDSelecionTarjeta.adapter as TarjetaAdapter
                    val tarjetaSeleccionada = tarjetaAdapter.tarjetas[tarjetaAdapter.obtenerSelectedPosition()]
                    tarjetaSeleccionada.retirarDinero(montoIngresar)
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
                    this@RetirarDinero,
                    android.R.layout.simple_dropdown_item_1line,
                    listaUsuarios
                )
                actvRDNombreUsuario.setAdapter(adapter)
            }
        }
    }


    private fun mostrarTarjetas(tarjetas: List<Tarjeta>) {
        if (tarjetas.isNotEmpty()) {
            val adapter = TarjetaAdapter(tarjetas,RecyclerView.NO_POSITION)
            rvRDSelecionTarjeta.adapter = adapter
            adapter.notifyDataSetChanged()
        } else {
            // Aquí puedes manejar el caso en el que no hay tarjetas para mostrar
        }
    }

}