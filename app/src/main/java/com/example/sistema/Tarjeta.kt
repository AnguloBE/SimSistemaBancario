package com.example.sistemabancario

import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.temporal.TemporalAdjuster
import java.time.temporal.TemporalAdjusters
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
    constructor() : this(
        numTarjeta = 0,
        fechaVencimiento = LocalDate.now(),
        cvv = 0,
        dineroDisponible = 0.0,
        pinSeguridad = 0,
        nombreUsuario = ""
    )



     fun newTarjeta(nombreUsuario: String, pinSeguridad: Int){
         var numTarjeta: String = ""
         var fechaVencimiento: LocalDate = LocalDate.now().plusMonths(15).with(TemporalAdjusters.lastDayOfMonth())
         var cvv: String= ""
         val random = Random
         for (i in 0 until 16){
            numTarjeta += random.nextInt(10)
         }

         for (i in 0 until 3){
             cvv += random.nextInt(10)
         }
         val tarjeta: Tarjeta= Tarjeta(numTarjeta.toInt(),fechaVencimiento,cvv.toInt(),0.0,pinSeguridad,nombreUsuario)


        Log.d("numTarjeta: ", "numTarjeta: $numTarjeta fecha vencimiento: $fechaVencimiento cvv: $cvv")



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