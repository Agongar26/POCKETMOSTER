package com.example.pocketmoster.ui.database

import androidx.room.*

@Dao
interface UserDao {
    @Insert
    suspend fun registrarUsuario(user: User)

    @Query("SELECT * FROM usuarios WHERE correo = :correo AND contraseña = :contraseña")
    suspend fun iniciarSesion(correo: String, contraseña: String): User?

    @Query("SELECT * FROM usuarios WHERE correo = :correo")
    suspend fun buscarUsuarioPorCorreo(correo: String): User?
}
