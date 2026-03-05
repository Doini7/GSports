package com.example.gesports.ui.login.screens.home

import CustomTopBar
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.gesports.ui.login.components.ActionCard
import com.example.gesports.R // Asegúrate de tener tus iconos aquí

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, nombre: String?, role: String = "jugador", onLogout: () -> Unit) {
    // Definimos si es entrenador para la lógica dinámica
    val isEntrenador = role.lowercase() == "entrenador"

    Scaffold(
        topBar = {
            CustomTopBar(
                title = "Gesports - Home",
                isMainScreen = true,
                onLogoutClick = onLogout
            )

        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(20.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "¡Bienvenido al centro deportivo!",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                ActionCard(
                    title = "Mis reservas",
                    iconRes = R.drawable.ic_reservas, // Asegúrate de que el nombre coincida
                    gradient = listOf(Color(0xFF2196F3), Color(0xFF03A9F4)),
                    modifier = Modifier.weight(1f),
                    onClick = {
                        // Navegación similar a Flutter: userPistasScreen
                        navController.navigate("history")
                    }
                )

                ActionCard(
                    title = if (isEntrenador) "Pista Equipo" else "Reservar pista",
                    iconRes = R.drawable.ic_reservas,
                    gradient = if (isEntrenador)
                        listOf(Color(0xFFFF9800), Color(0xFFFF5722)) // Naranja para entrenador
                    else
                        listOf(Color(0xFF4CAF50), Color(0xFF8BC34A)), // Verde para jugador
                    modifier = Modifier.weight(1f),
                    onClick = {
                        navController.navigate("reserve_user")
                    }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                ActionCard(
                    title = "Ver Ubicación",
                    iconRes = R.drawable.ic_location,
                    gradient = listOf(Color(0xFF9C27B0), Color(0xFF673AB7)),
                    modifier = Modifier.weight(1f),
                    onClick = {
                        navController.navigate("map_screen")
                    }
                )

                Spacer(modifier = Modifier.weight(1f))
            }
        }
    }
}