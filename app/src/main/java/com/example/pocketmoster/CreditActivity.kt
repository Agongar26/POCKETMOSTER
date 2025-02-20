package com.example.pocketmoster

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.pocketmoster.databinding.CreditActivityBinding

class CreditActivity : AppCompatActivity() {    //Define la actividad de créditos

    private lateinit var binding: CreditActivityBinding //Variable para manejar ViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {    //Método que se ejecuta al crear la actividad
        super.onCreate(savedInstanceState)  //Llama al método de la superclase para mantener el estado
        binding = CreditActivityBinding.inflate(layoutInflater) //Infla el layout con ViewBinding
        setContentView(binding.root)    //Establece el contenido de la vista con el binding

        val nombreApp = getString(R.string.app_name)    //Obtiene el nombre de la app desde los recursos

        // Recuperar el nombre ingresado en el EditText de AccountFragment
        val nombreIngresado = intent.getStringExtra("NOMBRE") ?: "Usuario"

        // Asignar el nombre recuperado al TextView de CreditActivity
        binding.creditText.text = "hola, "+ nombreIngresado + "\n\n" + getString(R.string.credit_Activity)

        // Configurar botón para enviar email
        binding.emailButton.setOnClickListener {
            enviarCorreo(nombreApp, nombreIngresado) //Llama a la función para enviar el email
        }
    }

    private fun enviarCorreo(nombreApp: String, nombreIngresado: String) {
        val emailIntent = Intent(Intent.ACTION_SEND).apply {    //Crea un intent para enviar un email
            type = "message/rfc822"  // Especifica que solo queremos apps de correo
            putExtra(Intent.EXTRA_EMAIL, arrayOf("mailto:agongar2601@g.educaand.es")) //Email a enviar
            putExtra(Intent.EXTRA_SUBJECT, "Consulta de la app $nombreApp") //Asuno del correo
        }

        // Verificar si hay una app de correo instalada antes de iniciar el intent
        if (emailIntent.resolveActivity(packageManager) != null) {
            startActivity(Intent.createChooser(emailIntent, "Elige una aplicación de correo"))  //Muestra un selector de apps de correo
        } else {
            // Si no hay apps de correo instaladas, muestra un mensaje
            showToast("No se encontró una aplicación de correo")
        }
    }

    private fun showToast(message: String) {
        runOnUiThread {
            android.widget.Toast.makeText(this, message, android.widget.Toast.LENGTH_SHORT).show()  //Muestra un mensaje emergente
        }
    }
}