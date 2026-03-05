package com.example.gesports.ui.login.screens.dashboard.form

import CustomTopBar
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.gesports.R
import com.example.gesports.models.Team
import com.example.gesports.ui.components.SportTextField
import com.example.gesports.ui.login.backend.ges_user.equipo.GesTeamViewModel
import com.example.gesports.ui.login.backend.ges_user.equipo.GesTeamViewModelFactory
import com.example.gesports.ui.login.components.RoundedButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormTeamScreen(navController: NavHostController, equipoId: Int) {
    val context = LocalContext.current
    val viewModel: GesTeamViewModel = viewModel(factory = GesTeamViewModelFactory(context))

    // Estados para los campos del formulario
    var nombre by rememberSaveable { mutableStateOf("") }
    var deporte by rememberSaveable { mutableStateOf("") }

    // Objeto para mantener la referencia original al editar
    var equipoCompleto by remember { mutableStateOf<Team?>(null) }
    var cargado by remember { mutableStateOf(false) }

    LaunchedEffect(equipoId) {
        if (equipoId != 0 && !cargado) {
            val team = viewModel.teamRepository.getTeamById(equipoId)
            team?.let {
                equipoCompleto = it
                nombre = it.name
                deporte = it.sport
            }
            cargado = true
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        // Fondo
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
                    title = if (equipoId == 0) "Nuevo Equipo" else "Editar Equipo",
                    isMainScreen = false,
                    onBackClick = {
                        navController.popBackStack()
                    }
                )
            }
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(horizontal = 32.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(Modifier.height(40.dp))

                SportTextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    placeholder = "Nombre del Equipo"
                )

                Spacer(Modifier.height(16.dp))

                SportTextField(
                    value = deporte,
                    onValueChange = { deporte = it },
                    placeholder = "Deporte (Ej: Fútbol, Tenis)"
                )

                Spacer(Modifier.height(40.dp))

                // Botón de acción
                RoundedButton(
                    text = if (equipoId == 0) "Crear Equipo" else "Guardar Cambios",
                    onClick = {
                        if (nombre.isNotBlank() && deporte.isNotBlank()) {
                            if (equipoId == 0) {
                                // Crear nuevo
                                viewModel.addTeam(
                                    Team(
                                        id = 0,
                                        name = nombre,
                                        sport = deporte
                                    )
                                )
                            } else {
                                // Actualizar existente
                                equipoCompleto?.let {
                                    viewModel.updateTeam(it.copy(name = nombre, sport = deporte))
                                }
                            }
                            navController.popBackStack()
                        }
                    }
                )
            }
        }
    }
}