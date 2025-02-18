package com.example.pocketmoster

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.pocketmoster.databinding.CreditActivityBinding

class CreditActivity : AppCompatActivity() {

    private lateinit var binding: CreditActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CreditActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val nombreApp = getString(R.string.app_name)

        // Configurar botón de volver para enviar email
        binding.emailButton.setOnClickListener {
            enviarCorreo(nombreApp)
        }
    }

    private fun enviarCorreo(nombreApp: String) {
        val emailIntent = Intent(Intent.ACTION_SEND).apply {
            type = "message/rfc822"  // Especifica que solo queremos apps de correo
            putExtra(Intent.EXTRA_EMAIL, arrayOf("agongar2601@g.educaand.es")) // Email a enviar
            putExtra(Intent.EXTRA_SUBJECT, "Consulta de la app $nombreApp")
        }

        // Verificar si hay una app de correo instalada antes de iniciar el intent
        if (emailIntent.resolveActivity(packageManager) != null) {
            startActivity(Intent.createChooser(emailIntent, "Elige una aplicación de correo"))
        } else {
            // Si no hay apps de correo instaladas, muestra un mensaje
            showToast("No se encontró una aplicación de correo")
        }
    }

    private fun showToast(message: String) {
        runOnUiThread {
            android.widget.Toast.makeText(this, message, android.widget.Toast.LENGTH_SHORT).show()
        }
    }
}