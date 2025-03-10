package com.example.pocketmoster.ui.session

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.pocketmoster.MainActivity
import com.example.pocketmoster.R
import com.example.pocketmoster.ui.database.AppDatabase
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Inicializar la base de datos y DAO para interactuar con la base de datos
        val db = AppDatabase.getDatabase(this)
        val userDao = db.userDao()

        // Vinculación de los elementos de la interfaz con las vistas
        val correoInput = findViewById<EditText>(R.id.correoInput)
        val contraseñaInput = findViewById<EditText>(R.id.contraseñaInput)
        val botonLogin = findViewById<Button>(R.id.botonLogin)

        // Acción del botón de login cuando se hace clic
        botonLogin.setOnClickListener {
            val correo = correoInput.text.toString()
            val contraseña = contraseñaInput.text.toString()

            // Verificar si los campos no están vacíos
            if (correo.isNotEmpty() && contraseña.isNotEmpty()) {
                lifecycleScope.launch {
                    val usuario = userDao.iniciarSesion(correo, contraseña)
                    if (usuario != null) {  // Si las credenciales son correctas
                        // Guardar el correo en SharedPreferences
                        val sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE)
                        val editor = sharedPreferences.edit()
                        editor.putString("user_email", correo) // Guardar el correo
                        editor.apply()  // Aplicar los cambios

                        runOnUiThread { // Mostrar un mensaje de bienvenida
                            Toast.makeText(this@LoginActivity, "Bienvenido, ${usuario.nombre}", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this@LoginActivity, MainActivity::class.java)
                            startActivity(intent)   // Iniciar la actividad principal
                            finish()    // Finalizar la actividad de login para que no se pueda regresar
                        }
                    } else {    // Si el usuario no se encuentra o las credenciales son incorrectas
                        runOnUiThread {
                            Toast.makeText(this@LoginActivity, "Credenciales incorrectas", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            } else {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
            }
        }
    }
}