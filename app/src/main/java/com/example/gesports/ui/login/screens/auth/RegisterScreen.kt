package com.example.gesports.ui.login.screens.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.gesports.R
import com.example.gesports.models.User
import com.example.gesports.ui.components.SportTextField
import com.example.gesports.ui.login.backend.ges_user.usuario.GesUserViewModel
import com.example.gesports.ui.login.components.RoundedButton
import kotlinx.coroutines.launch

@Composable
fun RegisterScreen(
    navController: NavHostController,
    viewModel: GesUserViewModel
) {
    var username by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var repeatPassword by rememberSaveable { mutableStateOf("") }
    var error by rememberSaveable { mutableStateOf("") }

    val scope = rememberCoroutineScope()

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.fondo2),
            contentDescription = "Fondo",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.gsportslogo),
                contentDescription = "GSports Logo",
                modifier = Modifier.size(124.dp),
                contentScale = ContentScale.Fit
            )

            Spacer(Modifier.height(10.dp))

            SportTextField(
                value = username,
                onValueChange = { username = it },
                placeholder = "Usuario",
                trailingIcon = { Icon(Icons.Filled.Person, contentDescription = null, tint = Color.White) },
            )

            Spacer(Modifier.height(10.dp))

            SportTextField(
                value = email,
                onValueChange = { email = it },
                placeholder = "Email",
            )

            Spacer(Modifier.height(10.dp))

            SportTextField(
                value = password,
                onValueChange = { password = it },
                placeholder = "Contraseña",
                trailingIcon = { Icon(Icons.Filled.Visibility, contentDescription = null, tint = Color.White) },
                visualTransformation = PasswordVisualTransformation()
            )

            Spacer(Modifier.height(10.dp))

            SportTextField(
                value = repeatPassword,
                onValueChange = { repeatPassword = it },
                placeholder = "Repetir Contraseña",
                trailingIcon = { Icon(Icons.Filled.Visibility, contentDescription = null, tint = Color.White) },
                visualTransformation = PasswordVisualTransformation()
            )

            Spacer(Modifier.height(32.dp))

            RoundedButton(
                text = "Registrarse",
                onClick = {
                    error = ""

                    // 1. Validaciones básicas
                    if (username.isBlank() || email.isBlank() || password.isBlank() || repeatPassword.isBlank()) {
                        error = "Rellena todos los campos."
                        return@RoundedButton
                    }
                    if (password != repeatPassword) {
                        error = "Las contraseñas no coinciden."
                        return@RoundedButton
                    }

                    // 2. Crear objeto User coincidiendo con tu Data Class actual
                    val nuevoUsuario = User(
                        id = 0,
                        name = username,
                        email = email,
                        password = password,
                        role = "admin_deportivo",
                        equipoId = null,
                        activo = true,
                    )

                    // 3. Guardar en Room usando el ViewModel
                    scope.launch {
                        try {
                            viewModel.addUser(nuevoUsuario)
                            // Ir a login tras éxito
                            navController.navigate("login") {
                                popUpTo("register") { inclusive = true }
                            }
                        } catch (e: Exception) {
                            error = "Error al registrar: ${e.message}"
                        }
                    }
                }
            )

            if (error.isNotEmpty()) {
                Spacer(Modifier.height(12.dp))
                Text(
                    text = error,
                    color = Color.Yellow, // Se ve mejor sobre fondos oscuros
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}