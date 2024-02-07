package com.example.sistema

import User
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.sistemabancario.Tarjeta

class ActNuevaTarjeta : AppCompatActivity() {
    private lateinit var actvNTNombreUsuario: AutoCompleteTextView
    private lateinit var btnNTCrearTarjeta: Button
    private lateinit var etNTPinSeguridad:EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_act_nueva_tarjeta)

        actvNTNombreUsuario = findViewById(R.id.actvNTNombreUsuario)
        btnNTCrearTarjeta = findViewById(R.id.btnNTCrearTarjeta)
        etNTPinSeguridad = findViewById(R.id.etNTPinSeguridad)

        listaUsuarios()

        btnNTCrearTarjeta.setOnClickListener {
            val tarjetainit = Tarjeta()

            val tarjeta = tarjetainit.newTarjeta(actvNTNombreUsuario.text.toString(),etNTPinSeguridad.text.toString())
            tarjetainit.toFirebase(tarjeta)
        }





    }
    fun listaUsuarios(){
        val user = User()
        user.getListaUsuarios { listaUsuarios ->
            runOnUiThread {
                val adapter = ArrayAdapter(
                    this@ActNuevaTarjeta,
                    android.R.layout.simple_dropdown_item_1line,
                    listaUsuarios
                )
                actvNTNombreUsuario.setAdapter(adapter)
            }


        }
    }
}