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

        val db = AppDatabase.getDatabase(this)
        val userDao = db.userDao()

        val nombreInput = findViewById<EditText>(R.id.nombreInput)
        val correoInput = findViewById<EditText>(R.id.correoInput)
        val contraseñaInput = findViewById<EditText>(R.id.contraseñaInput)
        val botonRegistrar = findViewById<Button>(R.id.botonRegistrar)

        botonRegistrar.setOnClickListener {
            val nombre = nombreInput.text.toString()
            val correo = correoInput.text.toString()
            val contraseña = contraseñaInput.text.toString()

            // Validar si los campos están completos
            if (nombre.isNotEmpty() && correo.isNotEmpty() && contraseña.isNotEmpty()) {
                lifecycleScope.launch {
                    val usuarioExistente = userDao.buscarUsuarioPorCorreo(correo)
                    if (usuarioExistente == null) {
                        val usuario = User(nombre = nombre, correo = correo, contraseña = contraseña)
                        userDao.registrarUsuario(usuario)

                        runOnUiThread {
                            Toast.makeText(this@RegisterActivity, "Registro exitoso", Toast.LENGTH_SHORT).show()
                            finish() // Cierra la actividad después de registrar
                        }
                    } else {
                        runOnUiThread {
                            Toast.makeText(this@RegisterActivity, "Correo ya registrado", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            } else {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
            }
        }
    }
}