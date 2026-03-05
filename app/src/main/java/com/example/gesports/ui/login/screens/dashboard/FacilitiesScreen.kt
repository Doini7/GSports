package com.example.gesports.ui.login.screens.dashboard

import CustomTopBar
import GlassCard
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.gesports.R
import com.example.gesports.models.Facility
import com.example.gesports.ui.login.backend.ges_user.pista.GesFacilitiesViewModel
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GesFacilityScreen(navController: NavHostController, viewModel: GesFacilitiesViewModel) {
    val facilities = viewModel.facilities
    val selectedCategory = viewModel.selectedCategory
    var facilityToDelete by remember { mutableStateOf<Facility?>(null) }

    Scaffold(
        topBar = {
            CustomTopBar(
                title = "Gestión de Pistas",
                isMainScreen = false,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("facility_form/0") },
                containerColor = Color(0xFF003FBC),
                contentColor = Color.White
            ) {
                Icon(Icons.Default.Add, contentDescription = "Añadir Pista")
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

            Column(modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp)) {
                Spacer(modifier = Modifier.height(16.dp))

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    items(facilities) { facility ->
                        // USAMOS EL GLASSCARD
                        GlassCard(modifier = Modifier.fillMaxWidth()) {
                            Row(
                                modifier = Modifier.padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                // Icono decorativo como el CircleAvatar de tu Flutter
                                Surface(
                                    modifier = Modifier.size(40.dp),
                                    shape = CircleShape,
                                    color = Color(0xFF2196F3)
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_reservas), // o icono de tenis
                                        contentDescription = null,
                                        tint = Color.White,
                                        modifier = Modifier.padding(8.dp)
                                    )
                                }

                                Spacer(modifier = Modifier.width(12.dp))

                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        facility.nombre,
                                        color = Color.Black, // Texto oscuro sobre blanco
                                        fontWeight = FontWeight.Bold,
                                        style = MaterialTheme.typography.titleMedium
                                    )
                                    Text(
                                        "${facility.ubicacion} • ${facility.precioHora}€/h",
                                        color = Color.Gray,
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                    Text(
                                        if(facility.disponible) "Activa" else "Inactiva",
                                        color = if(facility.disponible) Color(0xFF2E7D32) else Color.Red,
                                        style = MaterialTheme.typography.bodySmall,
                                        fontWeight = FontWeight.Bold
                                    )
                                }

                                // Botones de acción
                                Row {
                                    IconButton(onClick = { navController.navigate("facility_form/${facility.id}") }) {
                                        Icon(Icons.Default.Edit, contentDescription = null, tint = Color(0xFF2196F3))
                                    }
                                    IconButton(onClick = { facilityToDelete = facility }) {
                                        Icon(Icons.Default.Delete, contentDescription = null, tint = Color.Red)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    if (facilityToDelete != null) {
        AlertDialog(
            onDismissRequest = { facilityToDelete = null },
            title = { Text("Eliminar Pista") },
            text = { Text("¿Estás seguro de eliminar ${facilityToDelete?.nombre}?") },
            confirmButton = {
                TextButton(onClick = {
                    facilityToDelete?.let { viewModel.deleteFacility(it) }
                    facilityToDelete = null
                }) { Text("Eliminar", color = Color.Red) }
            },
            dismissButton = { TextButton(onClick = { facilityToDelete = null }) { Text("Cancelar") } }
        )
    }
}