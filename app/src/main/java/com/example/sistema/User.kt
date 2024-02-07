import android.content.ContentValues.TAG
import android.util.Log
import com.google.android.gms.tasks.Continuation
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot

class User(
    var nombreUsuario: String,
    private var fechaNac: Timestamp,
    private var domicilio: String
) {
    constructor() : this("", Timestamp.now(), "") {

    }

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

    public fun getListaUsuarios(callback: (MutableList<String>) -> Unit) {
        val listaUsuarios = mutableListOf<String>()

        db.collection("users")
            .get()
            .continueWith(Continuation<QuerySnapshot, Unit> { task ->
                if (task.isSuccessful) {
                    val querySnapshot = task.result
                    if (querySnapshot != null) {
                        for (document in querySnapshot.documents) {
                            val nombreUsuario = document.getString("nombreUsuario")
                            if (nombreUsuario != null) {
                                listaUsuarios.add(nombreUsuario)
                            }
                        }
                        // Llamar al callback con la lista completa
                        callback.invoke(listaUsuarios)
                    } else {
                        Log.d(TAG, "No hay documentos en la colección 'users'")
                    }
                } else {
                    Log.w(TAG, "Error al consultar la base de datos", task.exception)
                }
            })
    }
}
