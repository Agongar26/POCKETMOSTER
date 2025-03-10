package com.example.pocketmoster.ui.account

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.pocketmoster.CreditActivity
import com.example.pocketmoster.databinding.FragmentAccountBinding
import com.example.pocketmoster.ui.database.AppDatabase
import com.example.pocketmoster.ui.session.SessionActivity
import kotlinx.coroutines.launch

class AccountFragment : Fragment() {

    private var _binding: FragmentAccountBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAccountBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val db = AppDatabase.getDatabase(requireContext())
        val userDao = db.userDao()

        // Recuperar el correo desde SharedPreferences
        val sharedPreferences = requireContext().getSharedPreferences("user_prefs", 0)
        val userEmail = sharedPreferences.getString("user_email", "")

        // Verificamos si el correo está disponible
        if (!userEmail.isNullOrEmpty()) {
            lifecycleScope.launch {
                val user = userDao.buscarUsuarioPorCorreo(userEmail)
                user?.let {
                    requireActivity().runOnUiThread {
                        binding.textUserName.text = "Nombre: ${it.nombre}"  // Mostrar nombre
                        binding.textUserEmail.text = "Correo: ${it.correo}" // Mostrar correo
                    }
                }
            }
        } else {
            // Si no hay correo guardado, mostramos un mensaje de usuario no autenticado
            binding.textUserName.text = "Nombre: Usuario no autenticado"
            binding.textUserEmail.text = "Correo: No disponible"
        }

        // Guardar los cambios en la base de datos cuando se presione el botón
        binding.buttonSaveChanges.setOnClickListener {
            val nuevoNombre = binding.editTextUserName.text.toString()
            val nuevoCorreo = binding.editTextUserEmail.text.toString()

            if (nuevoNombre.isNotEmpty() && nuevoCorreo.isNotEmpty()) {
                lifecycleScope.launch {
                    // Comprobar si el nuevo correo ya está registrado
                    val correoExistente = userDao.buscarUsuarioPorCorreo(nuevoCorreo)

                    if (correoExistente != null && correoExistente.correo != userEmail) {
                        // Si el correo ya existe y no es el mismo que el actual, mostrar mensaje de error
                        requireActivity().runOnUiThread {
                            Toast.makeText(requireContext(), "Este correo ya está registrado", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        // Si no existe, actualizar el usuario
                        val user = userEmail?.let { it1 -> userDao.buscarUsuarioPorCorreo(it1) }
                        if (user != null) {
                            user.nombre = nuevoNombre
                            user.correo = nuevoCorreo
                            userDao.actualizarUsuario(user) // Actualizar usuario en la base de datos

                            // Actualizar los datos en SharedPreferences
                            val editor = sharedPreferences.edit()
                            editor.putString("user_email", nuevoCorreo)
                            editor.apply()

                            requireActivity().runOnUiThread {
                                // Mostrar un mensaje de éxito
                                Toast.makeText(requireContext(), "Datos actualizados correctamente", Toast.LENGTH_SHORT).show()
                                // Actualizar la interfaz para reflejar los cambios
                                binding.textUserName.text = "Nombre: $nuevoNombre"
                                binding.textUserEmail.text = "Correo: $nuevoCorreo"
                            }
                        }
                    }
                }
            } else {
                // Mostrar mensaje de error si los campos están vacíos
                Toast.makeText(requireContext(), "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
            }
        }

        // Configurar el botón para abrir CreditActivity y pasar el nombre del usuario registrado
        binding.button.setOnClickListener {
            if (!userEmail.isNullOrEmpty()) {
                lifecycleScope.launch {
                    val user = userDao.buscarUsuarioPorCorreo(userEmail)
                    user?.let {
                        val nombreUsuario = it.nombre
                        val intent = Intent(requireContext(), CreditActivity::class.java)
                        intent.putExtra("NOMBRE", nombreUsuario) // Pasar el nombre del usuario registrado
                        startActivity(intent)
                    }
                }
            } else {
                Toast.makeText(requireContext(), "No estás autenticado", Toast.LENGTH_SHORT).show()
            }
        }

        // Configurar el botón para eliminar la cuenta
        binding.buttonDeleteAccount.setOnClickListener {
            val sharedPreferences = requireContext().getSharedPreferences("user_prefs", 0)
            val userEmail = sharedPreferences.getString("user_email", "")

            if (!userEmail.isNullOrEmpty()) {
                lifecycleScope.launch {
                    val userDao = AppDatabase.getDatabase(requireContext()).userDao()
                    val user = userDao.buscarUsuarioPorCorreo(userEmail)

                    user?.let {
                        userDao.eliminarUsuario(it)  // Método para eliminar el usuario de la base de datos

                        // Eliminar la información en SharedPreferences
                        val editor = sharedPreferences.edit()
                        editor.remove("user_email")
                        editor.apply()

                        requireActivity().runOnUiThread {
                            // Mostrar mensaje de éxito
                            Toast.makeText(requireContext(), "Cuenta eliminada exitosamente", Toast.LENGTH_SHORT).show()

                            // Volver a la pantalla para el registro o inicio de sesión
                            val intent = Intent(requireContext(), SessionActivity::class.java) // SessionActivity es la actividad a la que deseas redirigir
                            startActivity(intent)
                            requireActivity().finish() // Finalizar la actividad actual para que no se pueda volver a ella
                        }
                    } ?: run {
                        // Mostrar mensaje si no se encuentra el usuario
                        requireActivity().runOnUiThread {
                            Toast.makeText(requireContext(), "Usuario no encontrado", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            } else {
                // Si el correo no está disponible (usuario no autenticado)
                Toast.makeText(requireContext(), "No estás autenticado", Toast.LENGTH_SHORT).show()
            }
        }

        // Configurar el botón para cerrar sesión
        binding.buttonCerrarSesion.setOnClickListener {
            // Eliminar la información de usuario en SharedPreferences (cerrar sesión)
            val editor = sharedPreferences.edit()
            editor.remove("user_email")
            editor.apply()

            // Redirigir a la actividad de sesión (inicio de sesión)
            val intent = Intent(requireContext(), SessionActivity::class.java)
            startActivity(intent)
            requireActivity().finish()  // Finalizar la actividad actual para evitar volver a la pantalla anterior
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}