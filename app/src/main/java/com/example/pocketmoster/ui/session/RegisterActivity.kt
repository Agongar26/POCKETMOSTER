package com.example.pocketmoster.ui.session

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.pocketmoster.R
import com.example.pocketmoster.ui.database.AppDatabase
import com.example.pocketmoster.ui.database.User
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        //Obtención de las instancias de la base de datos y el DAO
        val db = AppDatabase.getDatabase(this)
        val userDao = db.userDao()

        //Vinculación de las vistas de entrada en la interfaz
        val nombreInput = findViewById<EditText>(R.id.nombreInput)
        val correoInput = findViewById<EditText>(R.id.correoInput)
        val contraseñaInput = findViewById<EditText>(R.id.contraseñaInput)
        val botonRegistrar = findViewById<Button>(R.id.botonRegistrar)

        botonRegistrar.setOnClickListener { //Acción al hacer clic en el botón de registro
            val nombre = nombreInput.text.toString()    //Obtiene el texto ingresado en el campo nombre
            val correo = correoInput.text.toString()    //Obtiene el correo ingresado
            val contraseña = contraseñaInput.text.toString()    //Obtiene la contraseña ingresada

            // Validar si los campos están completos
            if (nombre.isNotEmpty() && correo.isNotEmpty() && contraseña.isNotEmpty()) {
                lifecycleScope.launch {
                    val usuarioExistente = userDao.buscarUsuarioPorCorreo(correo)
                    if (usuarioExistente == null) {
                        val usuario = User(nombre = nombre, correo = correo, contraseña = contraseña)   //Si no existe, crea un nuevo objeto usuario
                        userDao.registrarUsuario(usuario)   //Guarda el usuario en la base de datos

                        runOnUiThread { //Ejecuta en el hilo principal para mostrar un mensaje de éxito y finalizar la actividad
                            Toast.makeText(this@RegisterActivity, "Registro exitoso", Toast.LENGTH_SHORT).show()
                            finish() // Cierra la actividad después de registrar
                        }
                    } else {    //Si ya existe un usuario con el mismo correo, muestra un mensaje de error
                        runOnUiThread {
                            Toast.makeText(this@RegisterActivity, "Correo ya registrado", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            } else {    //Si algún campo está vacío, muestra un mensaje de error
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
            }
        }
    }
}