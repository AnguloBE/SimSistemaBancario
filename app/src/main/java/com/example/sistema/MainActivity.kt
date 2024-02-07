package com.example.sistema

import User
import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.sistemabancario.Tarjeta
import com.google.firebase.Timestamp
import java.util.Calendar
import java.util.GregorianCalendar
import java.util.Locale

class MainActivity : AppCompatActivity() {

    lateinit var btnNuevoUsuario:Button
    lateinit var btnNuevaTarjeta:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnNuevoUsuario = findViewById(R.id.btnNuevoUsuario)
        btnNuevaTarjeta = findViewById(R.id.btnNuevaTarjeta)
        setLocale("es")

        btnNuevoUsuario.setOnClickListener {
            val intent = Intent(this@MainActivity,ActNuevoUsuario::class.java)
            startActivity(intent)
        }

        btnNuevaTarjeta.setOnClickListener {
            val intent = Intent(this@MainActivity,ActNuevaTarjeta::class.java)
            startActivity(intent)
        }



        val fechaNacimiento = GregorianCalendar(2001, Calendar.DECEMBER, 19).time
        val timestampFechaNacimiento = Timestamp(fechaNacimiento)

        var user: User
        user = User("Bryan",timestampFechaNacimiento,"Constitucion #13 Angostura Sinaloa")
        //user.userToFirebase()

    }

    private fun setLocale(languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)

        val configuration = Configuration()
        configuration.locale = locale

        baseContext.resources.updateConfiguration(
            configuration,
            baseContext.resources.displayMetrics
        )
    }
}