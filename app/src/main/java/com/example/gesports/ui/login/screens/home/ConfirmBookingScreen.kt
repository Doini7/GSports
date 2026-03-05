package com.example.gesports.ui.login.screens.home

import CustomTopBar
import androidx.compose.foundation.BorderStroke
import com.example.gesports.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.gesports.models.Reservation
import com.example.gesports.ui.login.backend.ges_user.pista.GesFacilitiesViewModel
import com.example.gesports.ui.login.backend.ges_user.reserva.GesReservationViewModel
import com.example.gesports.ui.login.components.RoundedButton
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfirmBookingScreen(
    navController: NavHostController,
    facilityId: Int,
    facilityViewModel: GesFacilitiesViewModel,
    gesReservationViewModel: GesReservationViewModel,
    currentUserId: Int,
    userRole: String,
    userTeamId: Int?
) {
    val facility = facilityViewModel.facilities.find { it.id == facilityId }
    val allReservations by gesReservationViewModel.allReservations.collectAsState(initial = emptyList())

    var isBooking         by remember { mutableStateOf(false) }
    var showDatePicker    by remember { mutableStateOf(false) }
    val datePickerState   = rememberDatePickerState()
    var selectedDate      by rememberSaveable { mutableStateOf("Seleccionar Fecha") }
    var selectedTime      by rememberSaveable { mutableStateOf("") }

    // Entrenadores entran con reserva de equipo activada por defecto
    var isTeamReservation by rememberSaveable { mutableStateOf(userRole == "entrenador") }

    val intervalos = (9..20).map { hour ->
        val inicio = hour.toString().padStart(2, '0') + ":00"
        val fin    = (hour + 1).toString().padStart(2, '0') + ":00"
        inicio to fin
    }

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    val date = datePickerState.selectedDateMillis?.let {
                        SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date(it))
                    } ?: "Seleccionar Fecha"
                    selectedDate = date
                    selectedTime = ""
                    showDatePicker = false
                }) { Text("Aceptar") }
            }
        ) { DatePicker(state = datePickerState) }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.fondo2),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                CustomTopBar(
                    title = "Confirmar Reserva",
                    isMainScreen = false,
                    onBackClick = { navController.popBackStack() }
                )
            }
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (facility == null) return@Column

                // ── Card pista ───────────────────────────────────────────
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF0059FF))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            facility.nombre,
                            style = MaterialTheme.typography.titleLarge,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                        Text(facility.category, color = Color.White)
                    }
                }

                Spacer(Modifier.height(15.dp))

                // ── Switch solo para Entrenador ──────────────────────────
                if (userRole == "entrenador") {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Color(0x66FFFFFF))
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text("Tipo de Reserva", fontWeight = FontWeight.Bold)
                                Text(
                                    text = if (isTeamReservation) "Para todo el equipo" else "Solo para mí",
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                            Switch(
                                checked = isTeamReservation,
                                onCheckedChange = { isTeamReservation = it }
                            )
                        }
                    }
                    Spacer(Modifier.height(15.dp))
                }

                // ── Botón fecha ──────────────────────────────────────────
                Button(
                    onClick = { showDatePicker = true },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0059FF))
                ) {
                    Text(if (selectedDate == "Seleccionar Fecha") "📅 Elegir Día" else "📅 $selectedDate")
                }

                Spacer(Modifier.height(10.dp))
                Text("Horarios Disponibles:", color = Color.Black, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(8.dp))

                // ── Grid de horarios ─────────────────────────────────────
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    items(intervalos) { (inicio, fin) ->
                        val estaOcupado = allReservations.any { res ->
                            res.facilityId == facility.nombre &&
                                    res.fechaReserva == selectedDate &&
                                    res.horaInicio == inicio
                        }
                        val isSelected = selectedTime == inicio

                        OutlinedButton(
                            onClick = { if (!estaOcupado) selectedTime = inicio },
                            enabled = !estaOcupado && selectedDate != "Seleccionar Fecha",
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            border = BorderStroke(
                                width = if (isSelected) 2.dp else 1.dp,
                                color = when {
                                    estaOcupado -> Color(0xFFEF4444)
                                    isSelected  -> Color(0xFFF59E0B)
                                    else        -> Color(0xFF3B82F6)
                                }
                            ),
                            colors = ButtonDefaults.outlinedButtonColors(
                                containerColor = when {
                                    estaOcupado -> Color(0x33EF4444)
                                    isSelected  -> Color(0x33F59E0B)
                                    else        -> Color(0x220059FF)
                                },
                                contentColor = when {
                                    estaOcupado -> Color(0xFFEF4444)
                                    isSelected  -> Color(0xFFF59E0B)
                                    else        -> Color.Black
                                },
                                disabledContainerColor = Color(0x11FFFFFF),
                                disabledContentColor   = Color(0xFF475569)
                            )
                        ) {
                            Text(
                                text = if (estaOcupado) "Ocupado" else "$inicio - $fin",
                                fontWeight = FontWeight.Normal,
                                fontSize = 16.sp
                            )
                        }
                    }
                }

                Spacer(Modifier.height(16.dp))

                // ── Botón confirmar ──────────────────────────────────────
                RoundedButton(
                    text = if (isTeamReservation) "Reservar para Equipo" else "Confirmar Reserva",
                    onClick = {
                        if (!isBooking &&
                            selectedDate != "Seleccionar Fecha" &&
                            selectedTime.isNotEmpty()
                        ) {
                            isBooking = true
                            val hourInt      = selectedTime.split(":")[0].toInt()
                            val horaFinFinal = "${(hourInt + 1).toString().padStart(2, '0')}:00"

                            val reserva = Reservation(
                                facilityId   = facility.nombre,
                                userId       = currentUserId,
                                teamId       = if (isTeamReservation) userTeamId else null,
                                fechaReserva = selectedDate,
                                horaInicio   = selectedTime,
                                horaFin      = horaFinFinal,
                                category     = facility.category
                            )

                            gesReservationViewModel.addReservation(reserva)
                            navController.popBackStack()
                        }
                    }
                )
            }
        }
    }
}