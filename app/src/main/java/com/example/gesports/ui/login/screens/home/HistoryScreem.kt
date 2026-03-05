package com.example.gesports.ui.login.screens.home

import CustomTopBar
import GlassCard
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.example.gesports.models.Reservation
import com.example.gesports.ui.login.backend.ges_user.pista.GesFacilitiesViewModel
import com.example.gesports.ui.login.backend.ges_user.reserva.GesReservationViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(
    navController: NavHostController,
    viewModel: GesReservationViewModel,
    facilityViewModel: GesFacilitiesViewModel,
    currentUserId: Int,
    userRole: String,
    userTeamId: Int?
) {
    val myHistory by viewModel
        .getReservationsByUser(currentUserId, userTeamId)
        .collectAsState(initial = emptyList())

    var reservaAEliminar by remember { mutableStateOf<Reservation?>(null) }

    if (reservaAEliminar != null) {
        AlertDialog(
            onDismissRequest = { reservaAEliminar = null },
            title = { Text("Cancelar reserva") },
            text = {
                Text("¿Estás seguro de eliminar la reserva de ${reservaAEliminar?.facilityId} el ${reservaAEliminar?.fechaReserva}?")
            },
            confirmButton = {
                TextButton(onClick = {
                    reservaAEliminar?.let { viewModel.deleteReservation(it) }
                    reservaAEliminar = null
                }) {
                    Text("Eliminar", color = Color.Red)
                }
            },
            dismissButton = {
                TextButton(onClick = { reservaAEliminar = null }) {
                    Text("Cancelar")
                }
            }
        )
    }

    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            CustomTopBar(
                title = "Historial de Reservas",
                isMainScreen = false,
                onBackClick = { navController.popBackStack() }
            )
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
                    .padding(16.dp)
                    .padding(padding)
            ) {
                if (myHistory.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Aún no tienes reservas.", color = Color.White)
                    }
                } else {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(myHistory) { res ->
                            val isTeamRes = res.teamId != null
                            val isMine    = res.userId == currentUserId

                            GlassCard(modifier = Modifier.fillMaxWidth()) {
                                Row(
                                    modifier = Modifier.padding(16.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column(modifier = Modifier.weight(1f)) {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Text(
                                                res.facilityId,
                                                fontWeight = FontWeight.Bold,
                                                color = Color.Black
                                            )
                                            if (isTeamRes) {
                                                Badge(
                                                    containerColor = Color.Blue,
                                                    modifier = Modifier.padding(start = 8.dp)
                                                ) {
                                                    Text("EQUIPO", color = Color.White)
                                                }
                                            }
                                        }
                                        Text(
                                            "${res.fechaReserva} | ${res.horaInicio} - ${res.horaFin}",
                                            color = if (isTeamRes) Color.LightGray else Color.DarkGray,
                                            style = MaterialTheme.typography.bodySmall
                                        )
                                    }

                                    val puedeEliminar = isMine ||
                                            userRole.equals("entrenador", ignoreCase = true)

                                    if (puedeEliminar) {
                                        IconButton(onClick = { reservaAEliminar = res }) {
                                            Icon(
                                                painter = painterResource(
                                                    id = android.R.drawable.ic_menu_delete
                                                ),
                                                contentDescription = "Eliminar",
                                                tint = if (isTeamRes) Color.Blue else Color.Red
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
    }
}