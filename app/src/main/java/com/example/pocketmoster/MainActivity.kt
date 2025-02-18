package com.example.pocketmoster

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.pocketmoster.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

private lateinit var binding: ActivityMainBinding   //Variable para manejar el ViewBinding de la actividad

    override fun onCreate(savedInstanceState: Bundle?) {    //Método que se ejecuta cuando la actividad es creada
        super.onCreate(savedInstanceState)  //Llama al método de la superclase para conservar el estado

        binding = ActivityMainBinding.inflate(layoutInflater)   //Infla el diseño de la actividad usando ViewBinding
        setContentView(binding.root)    //Establece el contenido de la vista con la raíz del binding

        val navView: BottomNavigationView = binding.navView //Obtiene la referencia al BottomNavigationView

        val navController = findNavController(R.id.nav_host_fragment_activity_main) //Obtiene el controlador de navegación

        val appBarConfiguration = AppBarConfiguration(setOf(    //Configura la barra de navegación con los destinos principales
            R.id.navigation_catalog, R.id.navigation_market, R.id.navigation_quiz, R.id.navigation_secret, R.id.navigation_account))
        setupActionBarWithNavController(navController, appBarConfiguration) //Vincula el ActionBar con el NavController y la configuración
        navView.setupWithNavController(navController)   //Configura el BottomNavigationView para que funcione con NavController
    }
}