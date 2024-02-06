import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore

class User(
    var nombreUsuario: String,
    private var fechaNac: Timestamp,
    private var domicilio: String
) {
    private val db = FirebaseFirestore.getInstance()

    fun userToFirebase(callback: (String) -> Unit) {
        // Convertir el nombre del usuario a minúsculas
        val nombreUsuarioLowerCase = nombreUsuario.lowercase()

        // Verificar si ya existe un usuario con el mismo nombre (insensible a mayúsculas y minúsculas)
        db.collection("users")
            .whereEqualTo("nombreUsuarioLowerCase", nombreUsuarioLowerCase)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val querySnapshot = task.result
                    if (querySnapshot != null && !querySnapshot.isEmpty) {
                        // Usuario con el mismo nombre ya existe
                        callback.invoke("Ya existe un usuario con el mismo nombre")
                    } else {
                        // No hay un usuario con el mismo nombre, proceder con la creación
                        val userData = hashMapOf(
                            "nombreUsuario" to nombreUsuario,
                            "nombreUsuarioLowerCase" to nombreUsuarioLowerCase,
                            "fechaNac" to fechaNac,
                            "domicilio" to domicilio
                        )

                        db.collection("users")
                            .add(userData)
                            .addOnSuccessListener { documentReference ->
                                callback.invoke("Usuario registrado correctamente")
                            }
                            .addOnFailureListener { e ->
                                callback.invoke("Error al registrar el usuario")
                            }
                    }
                } else {
                    // Error al realizar la consulta
                    Log.w(TAG, "Error al consultar la base de datos", task.exception)
                    callback.invoke("Error al verificar el nombre de usuario")
                }
            }
    }
}
