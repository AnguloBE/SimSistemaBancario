package com.example.sistema

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.sistemabancario.Tarjeta

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var tarjeta: Tarjeta = Tarjeta()

        tarjeta.newTarjeta("a",1234)

    }
}