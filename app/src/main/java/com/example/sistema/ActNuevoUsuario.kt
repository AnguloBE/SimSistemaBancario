package com.example.sistema

import User
import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CalendarView
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.Timestamp
import java.util.Calendar
import java.util.Date

class ActNuevoUsuario : AppCompatActivity() {
    lateinit var etNUNombreUsuario :EditText
    lateinit var etNUFechaNac: EditText
    lateinit var etNUDireccion: EditText
    lateinit var btnNURegistrar: Button
    lateinit var tsFechaNac:Timestamp
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_act_nuevo_usuario)
        etNUNombreUsuario = findViewById(R.id.etNUNombreUsuario)
        etNUFechaNac = findViewById(R.id.etNUFechaNac)
        etNUDireccion = findViewById(R.id.etNUDireccion)
        btnNURegistrar = findViewById(R.id.btnNURegistrar)

        etNUFechaNac.setOnClickListener {
            showDatePickerDialog()
        }

        btnNURegistrar.setOnClickListener {
            var user = User(etNUNombreUsuario.text.toString(), tsFechaNac,etNUDireccion.text.toString())
            user.userToFirebase { mensaje ->
                Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
            }

        }



    }

    private fun showDatePickerDialog(){
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { view: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDay: Int ->
                val fechaSeleccionada = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                etNUFechaNac.setText(fechaSeleccionada)
                val calendar = Calendar.getInstance()
                calendar.set(selectedYear,selectedMonth,selectedDay)
                tsFechaNac = Timestamp(calendar.time)
            },
            year,
            month,
            day
        )

        // Configurar la fecha mínima (puedes ajustarla según tus necesidades)
        datePickerDialog.datePicker.maxDate = System.currentTimeMillis()

        datePickerDialog.show()
    }
}