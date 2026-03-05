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
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.gesports.R
import com.example.gesports.models.Team
import com.example.gesports.ui.login.backend.ges_user.equipo.GesTeamViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GesTeamScreen(navController: NavHostController, viewModel: GesTeamViewModel) {
    val teams = viewModel.teams
    val selectedSport = viewModel.selectedSport
    var teamToDelete by remember { mutableStateOf<Team?>(null) }

    Scaffold(
        topBar = {
            CustomTopBar(
                title = "Equipos Deportivos",
                isMainScreen = false,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("team_form/0") },
                containerColor = Color(0xFF003FBC),
                contentColor = Color.White
            ) {
                Icon(Icons.Default.Add, contentDescription = "Nuevo Equipo")
            }
        }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = R.drawable.fondo2),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp)
            ) {
                // Lista de Equipos
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(teams) { team ->
                        GlassCard(modifier = Modifier.fillMaxWidth()) {
                            Row(
                                modifier = Modifier
                                    .padding(16.dp)
                                    .fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                // Info del equipo (Texto oscuro para legibilidad)
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = team.name,
                                        color = Color(0xFF212121), // Gris muy oscuro
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 16.sp
                                    )
                                    Text(
                                        text = "Deporte: ${team.sport}",
                                        color = Color(0xFF616161), // Gris intermedio
                                        fontSize = 14.sp
                                    )
                                }

                                // Botones de acción (Como el Wrap trailing de Flutter)
                                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                    IconButton(onClick = { navController.navigate("team_form/${team.id}") }) {
                                        Icon(
                                            Icons.Default.Edit,
                                            contentDescription = null,
                                            tint = Color(0xFF2196F3)
                                        )
                                    }
                                    IconButton(onClick = { teamToDelete = team }) {
                                        Icon(
                                            Icons.Default.Delete,
                                            contentDescription = null,
                                            tint = Color(0xFFF44336)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // Diálogo de confirmación para borrar
    if (teamToDelete != null) {
        AlertDialog(
            onDismissRequest = { teamToDelete = null },
            title = { Text("Eliminar Equipo") },
            text = { Text("¿Estás seguro de que deseas eliminar al equipo '${teamToDelete?.name}'?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        teamToDelete?.let { viewModel.deleteTeam(it) }
                        teamToDelete = null
                    }
                ) {
                    Text("Eliminar", color = Color.Red)
                }
            },
            dismissButton = {
                TextButton(onClick = { teamToDelete = null }) {
                    Text("Cancelar")
                }
            }
        )
    }
}