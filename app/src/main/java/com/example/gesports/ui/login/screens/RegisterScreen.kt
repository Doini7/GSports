package com.example.gesports.ui.login.screens

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
import com.example.gesports.ui.login.backend.ges_user.GesUserViewModel
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

                    // Validaciones básicas
                    if (username.isBlank() || email.isBlank() || password.isBlank() || repeatPassword.isBlank()) {
                        error = "Rellena todos los campos."
                        return@RoundedButton
                    }
                    if (password != repeatPassword) {
                        error = "Las contraseñas no coinciden."
                        return@RoundedButton
                    }

                    val nuevo = User(
                        id = 0,
                        nombre = username,
                        email = email,
                        password = password,
                        rol = "JUGADOR" // rol por defecto en registro
                    )

                    // Guardar en Room
                    scope.launch {
                        viewModel.addUser(nuevo)
                        // ir a login
                        navController.navigate("login") {
                            popUpTo("register") { inclusive = true }
                        }
                    }
                }
            )

            if (error.isNotEmpty()) {
                Spacer(Modifier.height(8.dp))
                Text(text = error, color = MaterialTheme.colorScheme.error)
            }
        }
    }
}
