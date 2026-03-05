package com.example.gesports.ui.login.screens.dashboard.form

import CustomTopBar
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import com.example.gesports.models.Facility
import com.example.gesports.ui.components.SportTextField
import com.example.gesports.ui.login.backend.ges_user.pista.GesFacilitiesViewModel
import com.example.gesports.ui.login.backend.ges_user.pista.GesFacilitiesViewModelFactory
import com.example.gesports.ui.login.components.RoundedButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormFacilityScreen(navController: NavHostController, facilityId: Int) {
    val context = LocalContext.current
    val viewModel: GesFacilitiesViewModel = viewModel(factory = GesFacilitiesViewModelFactory(context))

    // Estados de los campos
    var nombre by rememberSaveable { mutableStateOf("") }
    var ubicacion by rememberSaveable { mutableStateOf("") }
    var precioHora by rememberSaveable { mutableStateOf("") }
    var disponible by rememberSaveable { mutableStateOf(true) }
    var categoria by rememberSaveable { mutableStateOf("") }

    var facilityCompleta by remember { mutableStateOf<Facility?>(null) }
    var cargado by remember { mutableStateOf(false) }

    // Cargar datos si es edición
    LaunchedEffect(facilityId) {
        if (facilityId != 0 && !cargado) {
            val facility = viewModel.facilityRepository.getFacilityById(facilityId)
            facility?.let {
                facilityCompleta = it
                nombre = it.nombre
                ubicacion = it.ubicacion
                precioHora = it.precioHora.toString()
                disponible = it.disponible
                categoria = it.category
            }
            cargado = true
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        // Fondo de pantalla
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
                    title = if (facilityId == 0) "Nueva Pista" else "Editar Pista",
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
                Spacer(Modifier.height(20.dp))

                // Campos de texto personalizados
                SportTextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    placeholder = "Nombre de la Pista"
                )
                Spacer(Modifier.height(16.dp))

                SportTextField(
                    value = ubicacion,
                    onValueChange = { ubicacion = it },
                    placeholder = "Ubicación"
                )
                Spacer(Modifier.height(16.dp))

                SportTextField(
                    value = precioHora,
                    onValueChange = { if (it.all { char -> char.isDigit() || char == '.' }) precioHora = it },
                    placeholder = "Precio por hora"
                )
                Spacer(Modifier.height(16.dp))

                SportTextField(
                    value = categoria,
                    onValueChange = { categoria = it },
                    placeholder = "Categoría (Ej: Tenis, Pádel)"
                )

                Spacer(Modifier.height(24.dp))

                // Switch de disponibilidad
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Activa", color = Color.Black, modifier = Modifier.weight(1f))
                    Switch(
                        checked = disponible,
                        onCheckedChange = { disponible = it },
                    )
                }

                Spacer(Modifier.height(40.dp))

                // Botón de acción
                RoundedButton(
                    text = if (facilityId == 0) "Añadir Pista" else "Guardar Cambios",
                    onClick = {
                        val precio = precioHora.toDoubleOrNull() ?: 0.0
                        if (nombre.isNotBlank()) {
                            // Creamos el objeto con los horarios fijos de 9 a 21
                            val nuevaPista = Facility(
                                id = if (facilityId == 0) 0 else facilityId,
                                nombre = nombre,
                                ubicacion = ubicacion,
                                precioHora = precio,
                                disponible = disponible,
                                category = categoria,
                                horarioApertura = 9,  // FIJO
                                horarioCierre = 21    // FIJO
                            )

                            if (facilityId == 0) {
                                viewModel.addFacility(nuevaPista)
                            } else {
                                viewModel.updateFacility(nuevaPista)
                            }
                            navController.popBackStack()
                        }
                    }
                )
                Spacer(Modifier.height(20.dp))
            }
        }
    }
}