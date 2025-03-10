package com.example.pocketmoster

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.pocketmoster.databinding.SplashActivityBinding
import com.example.pocketmoster.ui.session.SessionActivity

class SplashWindow : AppCompatActivity() {  //Declara la actividad SplashWindow que hereda de AppCompatActivity

    override fun onCreate(savedInstanceState: Bundle?) {    //Método que se ejecuta cuando la actividad es creada
        super.onCreate(savedInstanceState)  //Llama al método de la superclase para conservar el estado

        // Usamos ViewBinding
        val binding = SplashActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)    //Establece el contenido de la vista con la raíz del binding

        // Configuramos el Listener del botón con el binding
        binding.StartButton.setOnClickListener {
            // Cuando se presiona el botón, redirigir a MainActivity
            val intent = Intent(this, SessionActivity::class.java)
            startActivity(intent)   //Iniciar la actividad principal
            //finish()  // Termina SplashActivity para que el usuario no pueda regresar a ella
        }
    }
}
