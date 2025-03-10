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

        val db = AppDatabase.getDatabase(this)
        val userDao = db.userDao()

        val correoInput = findViewById<EditText>(R.id.correoInput)
        val contraseñaInput = findViewById<EditText>(R.id.contraseñaInput)
        val botonLogin = findViewById<Button>(R.id.botonLogin)

        botonLogin.setOnClickListener {
            val correo = correoInput.text.toString()
            val contraseña = contraseñaInput.text.toString()

            if (correo.isNotEmpty() && contraseña.isNotEmpty()) {
                lifecycleScope.launch {
                    val usuario = userDao.iniciarSesion(correo, contraseña)
                    if (usuario != null) {
                        runOnUiThread {
                            Toast.makeText(this@LoginActivity, "Bienvenido, ${usuario.nombre}", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this@LoginActivity, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                    } else {
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