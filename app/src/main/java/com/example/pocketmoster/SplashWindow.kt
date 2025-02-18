package com.example.pocketmoster

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.pocketmoster.databinding.SplashActivityBinding

class SplashWindow : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Usamos ViewBinding
        val binding = SplashActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configuramos el Listener del botón con el binding
        binding.StartButton.setOnClickListener {
            // Cuando se presiona el botón, redirigir a MainActivity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)   //Iniciar la actividad principal
            //finish()  // Termina SplashActivity para que el usuario no pueda regresar a ella
        }
    }
}
