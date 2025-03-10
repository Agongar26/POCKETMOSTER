package com.example.pocketmoster.ui.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [User::class], version = 1)    //Indicar que será una base de datos
abstract class AppDatabase : RoomDatabase() {   //Crear la clase abstracta
    abstract fun userDao(): UserDao //Método abstracto para obtener el userDao, que contiene el CRUD

    companion object {  //Permite hacer un singleton, para que solo haya una instancia en toda la app
        @Volatile   //Asegura que los cambios a esta variable sean visibles para todos los hilos inmediatamente
        private var INSTANCE: AppDatabase? = null   //Variable que almacena la instancia de la base de datos

        fun getDatabase(context: Context): AppDatabase {    //Función para obtener la base de datos
            return INSTANCE ?: synchronized(this) {     //Verificar si la base de datos ya existe
                //Crear la base de datos
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "pocketmonster.db"
                ).build()
                INSTANCE = instance //Guardar la instancia
                instance    //Devolver la instancia
            }
        }
    }
}