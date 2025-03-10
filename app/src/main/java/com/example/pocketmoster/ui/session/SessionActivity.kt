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

        //Referencias a los botones
        val botonLogin = findViewById<Button>(R.id.botonLogin)
        val botonRegistro = findViewById<Button>(R.id.botonRegistro)

        //Navegar a la pantalla de Login
        botonLogin.setOnClickListener {  //Si el usuario ya está autenticado, redirigirlo a la actividad principal
            val intent = Intent(this, LoginActivity::class.java)   //Crear un intent para abrir LoginActivity
            startActivity(intent)   //Iniciar la actividad de Login
        }

        //Navegar a la pantalla de Registro
        botonRegistro.setOnClickListener {  //Crear un intent para abrir RegisterActivity
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)   //Iniciar la actividad de Registro
        }
    }
}
