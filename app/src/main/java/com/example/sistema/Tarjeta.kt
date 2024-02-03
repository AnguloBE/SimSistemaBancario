package com.example.sistemabancario

import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import java.time.LocalDate
import kotlin.random.Random

class Tarjeta(
    private var numTarjeta:Int,
    private var fechaVencimiento:LocalDate,
    private var cvv:Int,
    private var dineroDisponible:Double = 0.0,
    private var pinSeguridad:Int,
    private var nombreUsuario:String
)
{



    private fun newTarjeta(nombreUsuario: String, pinSeguridad: Int){
        var numTarjeta: String = ""
        val random = Random
        for (i in 0 until 16){
            numTarjeta += random.nextInt(10)
        }
        Log.d("numTarjeta: ", numTarjeta)



    }

    private fun ingresarDinero(montoAIngresar:Double){
        dineroDisponible += montoAIngresar
        actualizarDatos()
    }

    private fun retirarDinero(montoARetirar:Double){
        if (dineroDisponible>=montoARetirar){
            dineroDisponible-=montoARetirar
            actualizarDatos()
        }else{
            Toast.makeText(null, "No cuenta con tanto dinero", Toast.LENGTH_SHORT).show()
        }
    }


    private fun actualizarDatos(){
        //aqui se mandara la informacion actualizada de la tarjeta
    }
}