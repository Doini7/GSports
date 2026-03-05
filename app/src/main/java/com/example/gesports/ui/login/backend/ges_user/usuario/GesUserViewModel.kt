package com.example.gesports.ui.login.backend.ges_user.usuario

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gesports.models.User
import com.example.gesports.repository.UserRepository
import com.example.gesports.repository.FacilityRepository // Asegúrate de importar tu repositorio de equipos
import com.example.gesports.repository.TeamRepository // Importante
import kotlinx.coroutines.launch

// Añadimos teamRepository al constructor
class GesUserViewModel(
    val userRepository: UserRepository,
    val teamRepository: TeamRepository
) : ViewModel() {

    private val _loginResult = mutableStateOf<User?>(null)
    val loginResult: State<User?> get() = _loginResult

    private val _loginError = mutableStateOf("")
    val loginError: State<String> get() = _loginError

    private var _users by mutableStateOf<List<User>>(emptyList())
    val users: List<User> get() = _users

    private var _selectedRole by mutableStateOf<String?>(null)
    val selectedRole: String? get() = _selectedRole

    init {
        viewModelScope.launch {
            userRepository.getAllUsers().collect { lista ->
                _users = lista
            }
        }
    }

    fun onRoleSelected(rol: String?) {
        _selectedRole = rol
        viewModelScope.launch {
            val flow = if (rol == null) userRepository.getAllUsers() else userRepository.getUsersByRole(rol)
            flow.collect { lista -> _users = lista }
        }
    }

    fun addUser(user: User) = viewModelScope.launch { userRepository.addUser(user) }
    fun deleteUser(user: User) = viewModelScope.launch { userRepository.deleteUser(user.id) }
    fun updateUser(user: User) = viewModelScope.launch { userRepository.updateUser(user) }

    suspend fun getUserById(id: Int): User? = userRepository.getUserById(id)

    fun login(email: String, password: String, onResult: (User?) -> Unit) {
        viewModelScope.launch {
            val user = userRepository.getUserByEmail(email.trim())
            if (user != null && user.password == password) {
                _loginResult.value = user
                _loginError.value = ""
                onResult(user) // Avisamos que ya terminó con éxito
            } else {
                _loginResult.value = null
                _loginError.value = "Usuario o contraseña incorrectos"
                onResult(null) // Avisamos que falló
            }
        }
    }

    // NUEVO: Función para cerrar sesión y limpiar datos
    fun logout() {
        _loginResult.value = null
        _loginError.value = ""
    }


}