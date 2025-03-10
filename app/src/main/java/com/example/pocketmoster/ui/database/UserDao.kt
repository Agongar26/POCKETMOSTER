package com.example.pocketmoster.ui.database

import androidx.room.*

@Dao
interface UserDao { //Crear la interfaz para interactuar con la base de datos
    @Insert //Indicar que es una consulta de inserción
    suspend fun registrarUsuario(user: User)    //Insertar un usuario

    @Query("SELECT * FROM usuarios WHERE correo = :correo AND contraseña = :contraseña")    //Definir una consulta para buscar el correo y contraseña proporcionados
    suspend fun iniciarSesion(correo: String, contraseña: String): User?    //Devolver el usuario encontrado en el caso de que exista

    @Query("SELECT * FROM usuarios WHERE correo = :correo") //Definir una consulta para buscar el correo proporcionado
    suspend fun buscarUsuarioPorCorreo(correo: String): User?   //Devolver el usuario encontrado en el caso de que exista

    @Update //Indicar que es una consulta para actualizar
    suspend fun actualizarUsuario(user: User) //Actualizar los datos de un usuario

    @Delete //Indicar que es una consulta para eliminar
    suspend fun eliminarUsuario(user: User) //Eliminar un usuario
}
