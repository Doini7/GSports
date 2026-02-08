package com.example.gesports.ui.login.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.gesports.R
import com.example.gesports.models.User
import com.example.gesports.models.UserRoles
import com.example.gesports.ui.components.SportTextField
import com.example.gesports.ui.login.backend.ges_user.GesUserViewModel
import com.example.gesports.ui.login.components.RoundedButton
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import com.example.gesports.data.RoomUserRepository
import com.example.gesports.database.AppDatabase

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormUserScreen(navController: NavHostController, userId: Int) {
    val context = LocalContext.current

    // ViewModel
    val viewModel: GesUserViewModel = viewModel(
        factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val db = AppDatabase.getDatabase(context)
                val repo = RoomUserRepository(db.userDao())
                return GesUserViewModel(repo) as T
            }
        }
    )

    // Obtener usuario para editar
    var usuarioEditando by remember { mutableStateOf<User?>(null) }

    LaunchedEffect(userId) {
        if (userId != 0) {
            usuarioEditando = viewModel.userRepository.getUserById(userId)
        }
    }

    var nombre by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var rol by rememberSaveable { mutableStateOf("JUGADOR") }

    usuarioEditando?.let {
        nombre = it.nombre
        email = it.email
        password = it.password
        rol = it.rol
    }

    Box(modifier = Modifier.fillMaxSize()) {

        // Fondo
        Image(
            painter = painterResource(id = R.drawable.fondo2),
            contentDescription = "Fondo",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            if (userId == 0) "Nuevo Usuario" else "Editar Usuario",
                            color = Color.White
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Volver",
                                tint = Color.White
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color(0x55000000)
                    )
                )
            }
        ) { padding ->

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(32.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Spacer(Modifier.height(20.dp))

                // Campo nombre
                SportTextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    placeholder = "Nombre",
                    trailingIcon = { Icon(Icons.Filled.Person, null, tint = Color.White) }
                )

                Spacer(Modifier.height(16.dp))

                // Campo email
                SportTextField(
                    value = email,
                    onValueChange = { email = it },
                    placeholder = "Email"
                )

                Spacer(Modifier.height(16.dp))

                // Campo contraseña
                SportTextField(
                    value = password,
                    onValueChange = { password = it },
                    placeholder = "Contraseña",
                    visualTransformation = PasswordVisualTransformation(),
                    trailingIcon = {
                        Icon(Icons.Filled.Visibility, null, tint = Color.White)
                    }
                )

                Spacer(Modifier.height(26.dp))

                // Roles
                Text("Seleccionar rol", color = Color.Black)
                Spacer(Modifier.height(10.dp))

                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    UserRoles.allRoles.forEach { (clave, etiqueta) ->

                        val isSelected = rol == clave

                        Button(
                            onClick = { rol = clave },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp),
                            shape = RoundedCornerShape(16.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (isSelected)
                                    Color(0xFF003FBC)
                                else
                                    Color(0x66000000),
                                contentColor = Color.White
                            )
                        ) {
                            Text(etiqueta)
                        }
                    }
                }

                Spacer(Modifier.height(40.dp))

                // Botón guardar
                RoundedButton(
                    text = if (userId == 0) "Crear usuario" else "Guardar cambios",
                    onClick = {

                        if (nombre.isNotBlank() && email.isNotBlank() && password.isNotBlank()) {

                            if (userId == 0) {
                                // Crear usuario
                                viewModel.addUser(
                                    User(
                                        id = 0,
                                        nombre = nombre,
                                        email = email,
                                        password = password,
                                        rol = rol
                                    )
                                )
                            } else {
                                // Editar usuario
                                usuarioEditando?.let {
                                    viewModel.updateUser(
                                        it.copy(
                                            nombre = nombre,
                                            email = email,
                                            password = password,
                                            rol = rol
                                        )
                                    )
                                }
                            }
                            navController.popBackStack()
                        }
                    }
                )
            }
        }
    }
}
