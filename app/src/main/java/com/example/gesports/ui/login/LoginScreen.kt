package com.example.gesports.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.gesports.R
import com.example.gesports.models.User
import com.example.gesports.models.UserRoles
import com.example.gesports.ui.components.SportTextField
import com.example.gesports.ui.login.backend.ges_user.GesUserViewModel
import com.example.gesports.ui.login.components.LogoIcon
import com.example.gesports.ui.login.components.RoundedButton
import kotlinx.coroutines.launch


@Composable
fun LoginScreen(navController: NavHostController , viewModel: GesUserViewModel) {
    //val logic = remember { LoginLogic() }
    var username by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    //var error by rememberSaveable { mutableStateOf("") }
    var rememberMe by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf("") }

    val scope = rememberCoroutineScope()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
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
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painter = painterResource(id = R.drawable.gsportslogo),
                contentDescription = "GSports Logo",
                modifier = Modifier.size(124.dp),
                contentScale = ContentScale.Fit
            )

            Spacer(Modifier.height(60.dp))

            Text("Iniciar sesion", style = MaterialTheme.typography.titleMedium)

            Spacer(Modifier.height(26.dp))

            SportTextField(
                value = username,
                onValueChange = { username = it },
                placeholder = "Usuario",
                trailingIcon = { Icon(Icons.Filled.Person, contentDescription = null, tint = Color.White) }
            )

            Spacer(Modifier.height(25.dp))

            SportTextField(
                value = password,
                onValueChange = { password = it },
                placeholder = "Contraseña",
                trailingIcon = { Icon(Icons.Filled.Visibility, contentDescription = null, tint = Color.White) },
                visualTransformation = PasswordVisualTransformation()
            )

            Spacer(Modifier.height(8.dp))
            Row(
                modifier = Modifier
                    .width(300.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = rememberMe,
                        onCheckedChange = { rememberMe = it },
                        colors = CheckboxDefaults.colors(
                            checkedColor = Color(0xFF0059FF)
                        ),
                        modifier = Modifier
                            .scale(0.8f)
                            .size(20.dp)
                    )
                    Text(
                        text = "Recordarme",
                        fontSize = 10.sp,
                        color = Color.Black,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }
                Text(
                    text = "¿Has olvidado tu contraseña?",
                    fontSize = 10.sp,
                    color = Color(0xFF003FBC),
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.clickable {
                    }
                )
            }

            if (error.isNotEmpty()) {
                Spacer(Modifier.height(8.dp))
                Text(text = error, color = MaterialTheme.colorScheme.error)
            }

            Spacer(Modifier.height(101.dp))

            RoundedButton(
                text = "Iniciar sesión",
                onClick = {
                    error = ""
                    scope.launch {
                        viewModel.login(username, password)
                        val user = viewModel.loginResult.value
                        val loginError = viewModel.loginError.value
                        if (user != null) {
                            val route = UserRoles.routes[user.rol] ?: "login"
                            val finalRoute = if (route == "home") "home/${user.nombre}" else route
                            navController.navigate(finalRoute) {
                                popUpTo("login") { inclusive = true }
                            }
                        } else if (loginError.isNotEmpty()) {
                            error = loginError
                        }
                    }
                }


                    /*                    try {
                                            val user = logic.comprobarLogin(username, password)
                                            val route = UserRoles.routes[user.rol] ?: "login"

                                            val finalRoute =
                                                if (route == "home") {
                                                    "home/${user.nombre}"
                                                } else {
                                                    route
                                                }
                                            navController.navigate(finalRoute) {
                                                popUpTo("login") { inclusive = true }
                                            }
                                        } catch (e: IllegalArgumentException) {
                                            error = e.message.toString()
                                        }*/



            )
            Spacer(Modifier.height(16.dp))
            Row {
                Text(
                    text = "¿No tienes cuenta? ",
                    color = Color.Black,
                    fontSize = MaterialTheme.typography.bodySmall.fontSize
                )
                Text(
                    text = "Crear una cuenta",
                    color = Color(0xFF003FBC),
                    fontSize = MaterialTheme.typography.bodySmall.fontSize,
                    modifier = Modifier.clickable {
                        navController.navigate("register") {
                            popUpTo("login") { inclusive = true }
                        }
                    }
                )
            }

            Spacer(Modifier.height(24.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                HorizontalDivider(
                    color = Color.Black,
                    thickness = 1.dp,
                    modifier = Modifier.width(100.dp)
                )
                Text(
                    text = "  o  ",
                    color = Color.Black,
                    style = MaterialTheme.typography.bodySmall
                )
                HorizontalDivider(
                    modifier = Modifier.width(100.dp),
                    thickness = 1.dp,
                    color = Color.Black
                )
            }

            Spacer(Modifier.height(20.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                LogoIcon(logoRes = R.drawable.google, contentDescription = "Google") {
                }
                LogoIcon(logoRes = R.drawable.twitter, contentDescription = "Twitter") {
                }
                LogoIcon(logoRes = R.drawable.facebook, contentDescription = "Facebook") {
                }
                LogoIcon(logoRes = R.drawable.apple, contentDescription = "Apple") {
                }
            }

        }
    }
}
