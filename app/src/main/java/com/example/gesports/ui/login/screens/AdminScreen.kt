package com.example.gesports.ui.login.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.gesports.R
import com.example.gesports.ui.login.components.ActionCard

@Composable
fun AdminScreen(navController: NavController) {

    Scaffold { padding ->

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
                    .padding(20.dp)
            ) {

                Text("Dashboard", style = MaterialTheme.typography.headlineMedium)
                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    ActionCard(
                        modifier = Modifier.weight(1f),
                        title = "Usuarios",
                        iconRes = R.drawable.ic_jugadores,
                        gradient = listOf(Color(0xFFFFA726), Color(0xFFFF7043)),
                        onClick = { navController.navigate("ges_user") }
                    )
                    ActionCard(
                        modifier = Modifier.weight(1f),
                        title = "Reservas",
                        iconRes = R.drawable.ic_reservas,
                        gradient = listOf(Color(0xFFEF5350), Color(0xFFE53935)),
                        onClick = {  }
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    ActionCard(
                        modifier = Modifier.weight(1f),
                        title = "Partidos",
                        iconRes = R.drawable.ic_partidos,
                        gradient = listOf(Color(0xFFAB47BC), Color(0xFF8E24AA)),
                        onClick = {  }
                    )
                    ActionCard(
                        modifier = Modifier.weight(1f),
                        title = "Pistas",
                        iconRes = R.drawable.ic_pistas,
                        gradient = listOf(Color(0xFF42A5F5), Color(0xFF1E88E5)),
                        onClick = { }
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    ActionCard(
                        modifier = Modifier.weight(1f),
                        title = "Equipos",
                        iconRes = R.drawable.ic_equipos,
                        gradient = listOf(Color(0xFF66BB6A), Color(0xFF43A047)),
                        onClick = {  }
                    )
                    ActionCard(
                        modifier = Modifier.weight(1f),
                        title = "Ajustes",
                        iconRes = R.drawable.ic_settings,
                        gradient = listOf(Color(0xFF9E9E9E), Color(0xFF616161)),
                        onClick = { }
                    )
                }
            }
        }
    }
}
