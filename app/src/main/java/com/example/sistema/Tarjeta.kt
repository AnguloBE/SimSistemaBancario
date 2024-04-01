import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Calendar
import java.util.Date
import kotlin.random.Random

class Tarjeta(
    var numTarjeta:String,
    var fechaVencimiento:Date,
    var cvv:String,
    var dineroDisponible:Double = 0.0,
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

    fun toFirebase(tarjeta:Tarjeta, callback: (String) -> Unit){
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
                callback.invoke("Tarjeta registrada correctamente")
            }
            .addOnFailureListener {e ->
                callback.invoke("Error al registrar la tarjeta: $e")
            }

    }

    public fun getTarjetasUsuario(nombreUsuario: String, callback: (List<Tarjeta>) -> Unit){
        val db = FirebaseFirestore.getInstance()

        db.collection("tarjetas")
            .whereEqualTo("nombreUsuario", nombreUsuario)
            .get()
            .addOnSuccessListener { documents ->
                val tarjetas = mutableListOf<Tarjeta>()
                for (document in documents) {
                    val numTarjeta = document.getString("numTarjeta") ?: ""
                    val fechaVencimiento = document.getDate("fechaVencimiento") ?: Date()
                    val cvv = document.getString("cvv") ?: ""
                    val dineroDisponible = document.getDouble("saldo") ?: 0.0
                    val pinSeguridad = document.getString("pinSeguridad") ?: ""

                    val tarjeta = Tarjeta(numTarjeta,fechaVencimiento,cvv,dineroDisponible,pinSeguridad,nombreUsuario)
                    tarjetas.add(tarjeta)
                }
                callback.invoke(tarjetas)
            }
            .addOnFailureListener { exception ->
                // Maneja el caso en que ocurra un error al obtener las tarjetas
                println("Error al obtener las tarjetas del usuario: $exception")
                callback.invoke(emptyList()) // Devuelve una lista vacía en caso de error
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

    fun ingresarDinero(montoAIngresar:Double){
        dineroDisponible += montoAIngresar
        actualizarDatos()
    }

    fun retirarDinero(montoARetirar:Double){
        if (dineroDisponible>=montoARetirar){
            dineroDisponible-=montoARetirar
            actualizarDatos()
        }else{
            Toast.makeText(null, "No cuenta con tanto dinero", Toast.LENGTH_SHORT).show()
        }
    }


    fun actualizarDatos() {
        val tarjetaData = mapOf(
            "fechaVencimiento" to fechaVencimiento,
            "cvv" to cvv,
            "saldo" to dineroDisponible,
            "pinSeguridad" to pinSeguridad
        )

        val tarjetaCollection = db.collection("tarjetas")

        tarjetaCollection.whereEqualTo("numTarjeta", numTarjeta)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val tarjetaDocumentId = document.id
                    val tarjetaDocumentReference = tarjetaCollection.document(tarjetaDocumentId)

                    tarjetaDocumentReference.update(tarjetaData)
                        .addOnSuccessListener {
                            // Aquí puedes manejar el caso de éxito si es necesario
                        }
                        .addOnFailureListener { e ->
                            // Aquí puedes manejar el caso de fallo si es necesario
                        }
                }
            }
            .addOnFailureListener { e ->
                // Aquí puedes manejar el caso de fallo si es necesario
            }
    }
}