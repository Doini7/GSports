package com.example.gesports.ui.login.backend.ges_user

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gesports.repository.UserRepository
import com.example.gesports.models.User
import kotlinx.coroutines.launch
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf

class GesUserViewModel (val userRepository: UserRepository): ViewModel() {

    // Login result observable
    private val _loginResult = mutableStateOf<User?>(null)
    val loginResult: State<User?> get() = _loginResult

    // Login error observable
    private val _loginError = mutableStateOf("")
    val loginError: State<String> get() = _loginError

    // Lista de usuarios
    private var _users by mutableStateOf<List<User>>(emptyList())
    val users: List<User> get() = _users

    // Rol seleccionado
    private var _selectedRole by mutableStateOf<String?>(null)
    val selectedRole: String? get() = _selectedRole



    init {
        // CAMBIO: Colectamos el Flow de Room
        viewModelScope.launch {
            userRepository.getAllUsers().collect { lista ->
                _users = lista
            }
        }
    }

    /*init {
        //podemos utilizar directamente loadUsers()
        viewModelScope.launch {
            _users = userRepository.getAllUsers()
        }
    }*/

    /* fun loadUsers(){
         viewModelScope.launch {
             if(_selectedRole==null){
                 _users=userRepository.getAllUsers()
             }else{
                 _users=userRepository.getUsersByRole(_selectedRole!!)
             }
         }
     }
     fun onRoleSelected(rol: String?) {
         _selectedRole = rol
         viewModelScope.launch {
             _users = if (rol == null) {
                 userRepository.getAllUsers()
             } else {
                 userRepository.getUsersByRole(rol)
             }
         }
     }*/
    fun onRoleSelected(rol: String?) {
        _selectedRole = rol
        viewModelScope.launch {
            //CAMBIO: también colectamos el Flow filtrado
            val flow = if (rol == null)
                userRepository.getAllUsers()
            else
                userRepository.getUsersByRole(rol)

            flow.collect { lista ->
                _users = lista
            }
        }
    }
    fun addUser(user: User){
        viewModelScope.launch{
            userRepository.addUser(user)
            //  loadUsers()  NO HACE FALTA YA LO HACE FLOW
        }
    }
    fun deleteUser(user: User) {
         viewModelScope.launch {
             userRepository.deleteUser(user.id)
             //Igual: Flow actualiza automáticamente _users
         }
     }

    fun updateUser(user: User) {
        viewModelScope.launch {
            userRepository.updateUser(user)
        }
    }

    suspend fun getUserById(id: Int): User? {
        return userRepository.getUserById(id)
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            val user = userRepository.getUserByEmail(email.trim())
            if (user != null && user.password == password) {
                _loginResult.value = user
                _loginError.value = ""
            } else {
                _loginResult.value = null
                _loginError.value = "Usuario o contraseña incorrectos"
            }
        }
    }

/*    suspend fun login(email: String, password: String): User? {
        val user = userRepository.getUserByEmail(email.trim())
        return if (user != null && user.password == password) user else null
    }*/
}