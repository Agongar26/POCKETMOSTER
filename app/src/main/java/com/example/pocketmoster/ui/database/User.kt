package com.example.pocketmoster.ui.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "usuarios") //Convertir la clase en entidad Room
data class User(    //Crear clase de datos para usuarios
    @PrimaryKey(autoGenerate = true)    //Indicar que será la clave primaria de la tabla
    val id: Int = 0,    //Variable para almacenar el id
    var nombre: String, //Variable para almacenar el nombre
    var correo: String, //Variable para almacenar el correo
    val contraseña: String  //Variable para almacenar la contraseña
)