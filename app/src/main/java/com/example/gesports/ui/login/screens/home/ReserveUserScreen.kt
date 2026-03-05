package com.example.gesports.ui.login.screens.home

import CustomTopBar
import GlassCard
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.gesports.R
import com.example.gesports.ui.login.backend.ges_user.pista.GesFacilitiesViewModel
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReserveUserScreen(navController: NavHostController, facilityViewModel: GesFacilitiesViewModel) {
    // Solo mostramos las pistas que están marcadas como disponibles
    val facilities = facilityViewModel.facilities.filter { it.disponible }

    Scaffold(
        topBar = {
            CustomTopBar(
                title = "Nueva Reserva",
                isMainScreen = false, // Mostrará la flecha de volver a Home
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.fondo2),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp).padding(padding)
            ) {
                Spacer(Modifier.height(20.dp))

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(facilities) { facility ->
                        // USAMOS GLASSCARD (Efecto translúcido de Flutter)
                        GlassCard(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                // Icono representativo (como en tu Flutter: sports_tennis)
                                Icon(
                                    imageVector = Icons.Default.DateRange,
                                    contentDescription = null,
                                    tint = Color(0xFF003FBC), // Azul corporativo
                                    modifier = Modifier.size(40.dp)
                                )

                                Spacer(Modifier.width(16.dp))

                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = facility.nombre,
                                        color = Color.Black, // Texto oscuro para la GlassCard blanca
                                        style = MaterialTheme.typography.titleLarge,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = "Ubicación: ${facility.ubicacion}",
                                        color = Color.DarkGray,
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                    Text(
                                        text = "${facility.precioHora}€/hora",
                                        color = Color(0xFF1976D2), // Azul para resaltar el precio
                                        fontWeight = FontWeight.Bold,
                                        style = MaterialTheme.typography.bodyLarge
                                    )
                                }
                                Button(
                                    onClick = { navController.navigate("confirm_booking/${facility.id}") },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color(0xFF003FBC)
                                    ),
                                    shape = MaterialTheme.shapes.medium,
                                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp)
                                ) {
                                    Text("Reservar", fontSize = 12.sp)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}