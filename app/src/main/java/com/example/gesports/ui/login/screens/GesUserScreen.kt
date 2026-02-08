package com.example.gesports.ui.login.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.gesports.R
import com.example.gesports.ui.login.backend.ges_user.GesUserViewModel
import com.example.gesports.ui.login.components.UserCard
import com.example.gesports.models.User


@Composable
fun GesUserScreen(navController: NavHostController, viewModel : GesUserViewModel) {

    val context = LocalContext.current

    val snackbarHostState = remember { SnackbarHostState() }
    var userToDelete by remember { mutableStateOf<User?>(null) }



    val users = viewModel.users
    val selectedRole = viewModel.selectedRole



    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate("user_form/0")}) {
                Icon(Icons.Default.Add, contentDescription = "Agregar usuario")
            }
        }
    ) { padding ->

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
                    .padding(padding)
                    .padding(16.dp)
            ) {
                Text("Usuarios", style = MaterialTheme.typography.headlineMedium)
                Spacer(modifier = Modifier.height(16.dp))

                // Filtros
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        FilterChip(
                            selected = selectedRole == null,
                            onClick = { viewModel.onRoleSelected(null) },
                            label = { Text("Todos") }
                        )
                        FilterChip(
                            selected = selectedRole == "ADMIN_DEPORTIVO",
                            onClick = { viewModel.onRoleSelected("ADMIN_DEPORTIVO") },
                            label = { Text("Administrador") }
                        )
                        FilterChip(
                            selected = selectedRole == "ENTRENADOR",
                            onClick = { viewModel.onRoleSelected("ENTRENADOR") },
                            label = { Text("Entrenador") }
                        )
                    }
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        FilterChip(
                            selected = selectedRole == "ARBITRO",
                            onClick = { viewModel.onRoleSelected("ARBITRO") },
                            label = { Text("Árbitro") }
                        )
                        FilterChip(
                            selected = selectedRole == "JUGADOR",
                            onClick = { viewModel.onRoleSelected("JUGADOR") },
                            label = { Text("Jugador") }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Lista de usuarios
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(users) { user ->
                        UserCard(
                            nombre = user.nombre,
                            rol = user.rol,
                            modifier = Modifier.fillMaxWidth(),
                            onEdit = { navController.navigate("user_form/${user.id}") },
                            onDelete = { userToDelete = user }
                        )
                    }
                }
            }
        }
    }

    // Confirmacion
    userToDelete?.let { user ->
        AlertDialog(
            onDismissRequest = { userToDelete = null },

            title = { Text("Confirmación") },
            text = { Text("¿Seguro que deseas eliminar a ${user.nombre}?") },

            confirmButton = {
                TextButton(onClick = {
                    viewModel.deleteUser(user)
                    userToDelete = null
                }) {
                    Text("Eliminar")
                }
            },

            dismissButton = {
                TextButton(onClick = { userToDelete = null }) {
                    Text("Cancelar")
                }
            }
        )
    }
}

