package com.example.gesports.ui.login.screens.dashboard

import CustomTopBar
import GlassCard
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.gesports.R
import com.example.gesports.ui.login.backend.ges_user.usuario.GesUserViewModel
import com.example.gesports.ui.login.components.UserCard
import com.example.gesports.models.User

@Composable
fun GesUserScreen(navController: NavHostController, viewModel: GesUserViewModel) {

    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }
    var userToDelete by remember { mutableStateOf<User?>(null) }

    // Observamos el estado del ViewModel
    val users = viewModel.users
    val selectedRole = viewModel.selectedRole

    Scaffold(
        topBar = {
            CustomTopBar(
                title = "Gestión de Usuarios",
                isMainScreen = false,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButton = {
            // Navega al formulario con id 0 para crear nuevo
            FloatingActionButton(
                onClick = { navController.navigate("user_form/0") },
                containerColor = Color(0xFF003FBC),
                contentColor = Color.White
            ) {
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

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    val roles = listOf("Todos", "admin_deportivo", "entrenador", "jugador")
                    roles.forEach { role ->
                        val isSelected = if (role == "Todos") selectedRole == null else selectedRole == role
                        FilterChip(
                            selected = isSelected,
                            onClick = { viewModel.onRoleSelected(if (role == "Todos") null else role) },
                            label = { Text(role) }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Lista de usuarios
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(users) { user ->
                        val isAdmin = user.role.lowercase() == "admin"

                        GlassCard(modifier = Modifier.fillMaxWidth()) {
                            Row(
                                modifier = Modifier
                                    .padding(16.dp)
                                    .fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                // Info del usuario (Textos oscuros para que se lean sobre el cristal blanco)
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = user.name,
                                        color = Color(0xFF212121), // Negro/Gris oscuro
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 16.sp
                                    )
                                    Text(
                                        text = "${user.email} • ${user.role}",
                                        color = Color(0xFF616161), // Gris subtítulo
                                        fontSize = 14.sp
                                    )
                                }

                                // Botones de acción (Trailing)
                                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                    if (!isAdmin) {
                                        IconButton(onClick = { navController.navigate("user_form/${user.id}") }) {
                                            Icon(Icons.Default.Edit, contentDescription = null, tint = Color(0xFF2196F3))
                                        }
                                    }
                                    IconButton(onClick = { userToDelete = user }) {
                                        Icon(Icons.Default.Delete, contentDescription = null, tint = Color(0xFFF44336))
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    // Diálogo de Confirmación
    userToDelete?.let { user ->
        AlertDialog(
            onDismissRequest = { userToDelete = null },
            title = { Text("Confirmación") },
            text = { Text("¿Seguro que deseas eliminar a ${user.name}?") },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.deleteUser(user)
                    userToDelete = null
                }) {
                    Text("Eliminar", color = MaterialTheme.colorScheme.error)
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