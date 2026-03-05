package com.example.gesports.ui.login.screens.dashboard

import CustomTopBar
import GlassCard
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.gesports.R
import com.example.gesports.ui.login.backend.ges_user.reserva.GesReservationViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GesReserveScreen(navController: NavHostController, viewModel: GesReservationViewModel) {
    val reservations by viewModel.allReservations.collectAsState(initial = emptyList())
    Scaffold(
        topBar = {
            CustomTopBar(
                title = "Gestión de Reservas",
                isMainScreen = false,
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
                    .padding(padding)
                    .padding(16.dp)
            ) {

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(reservations) { res ->
                        GlassCard(modifier = Modifier.fillMaxWidth()) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    text = res.facilityId,
                                    color = Color(0xFF003FBC),
                                    fontWeight = FontWeight.ExtraBold,
                                    style = MaterialTheme.typography.titleLarge
                                )

                                Spacer(modifier = Modifier.height(4.dp))

                                // Fecha
                                Text(
                                    text = "Fecha: ${res.fechaReserva}",
                                    color = Color(0xFF424242),
                                    style = MaterialTheme.typography.bodyMedium
                                )

                                // Horario
                                Text(
                                    text = "Horario: ${res.horaInicio} - ${res.horaFin}",
                                    color = Color(0xFF1976D2), // Azul brillante
                                    fontWeight = FontWeight.Bold,
                                    style = MaterialTheme.typography.bodyMedium
                                )

                                // ID de Usuario Directo
                                Text(
                                    text = "ID Usuario: ${res.userId ?: "N/A"}",
                                    color = Color(0xFF757575), // Gris
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}