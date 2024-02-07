package com.example.sistemabancario

import android.util.Log
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Calendar
import java.util.Date
import kotlin.random.Random

class Tarjeta(
    private var numTarjeta:String,
    private var fechaVencimiento:Date,
    private var cvv:String,
    private var dineroDisponible:Double = 0.0,
    private var pinSeguridad:String,
    private var nombreUsuario:String
)
{
    constructor() : this(
        numTarjeta = "",
        fechaVencimiento = Date(),
        cvv = "",
        dineroDisponible = 0.0,
        pinSeguridad = "",
        nombreUsuario = ""
    )
    private val db = FirebaseFirestore.getInstance()

    fun toFirebase(tarjeta:Tarjeta){
        val tarjetaData = mapOf(
            "numTarjeta" to tarjeta.numTarjeta,
            "fechaVencimiento" to tarjeta.fechaVencimiento,
            "cvv" to tarjeta.cvv,
            "saldo" to tarjeta.dineroDisponible,
            "pinSeguridad" to tarjeta.pinSeguridad,
            "nombreUsuario" to tarjeta.nombreUsuario
        )

        val tarjetaCollection = db.collection("tarjetas")

        tarjetaCollection.add(tarjetaData)
            .addOnSuccessListener { documentReference ->

            }
            .addOnFailureListener {

            }

    }



     fun newTarjeta(nombreUsuario: String, pinSeguridad: String):Tarjeta{
         var numTarjeta = ""


         val fechaActual: Calendar = Calendar.getInstance()

// Calcular la fecha de vencimiento para ser 15 meses después y el último día del mes
         fechaActual.add(Calendar.MONTH, 15)
         fechaActual.set(Calendar.DAY_OF_MONTH, fechaActual.getActualMaximum(Calendar.DAY_OF_MONTH))

// Obtener la fecha de vencimiento como un objeto Date
         val fechaVencimiento: Date = fechaActual.time

         var cvv= ""
         val random = Random
         for (i in 0 until 16){
            numTarjeta += random.nextInt(10)
         }

         for (i in 0 until 3){
             cvv += random.nextInt(10)
         }
         val tarjeta= Tarjeta(numTarjeta,fechaVencimiento,cvv,0.0,pinSeguridad,nombreUsuario)
         return tarjeta
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