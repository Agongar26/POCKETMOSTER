package com.example.pocketmoster.ui.session

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import com.example.pocketmoster.R


class SessionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_session)

        // Referencias a los botones
        val botonLogin = findViewById<Button>(R.id.botonLogin)
        val botonRegistro = findViewById<Button>(R.id.botonRegistro)

        // Navegar a la pantalla de Login
        botonLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        // Navegar a la pantalla de Registro
        botonRegistro.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}
