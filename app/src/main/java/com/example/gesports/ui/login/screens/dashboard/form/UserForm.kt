package com.example.gesports.ui.login.screens.dashboard.form

import CustomTopBar
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.gesports.R
import com.example.gesports.models.User
import com.example.gesports.models.Team
import com.example.gesports.models.UserRoles
import com.example.gesports.ui.components.SportTextField
import com.example.gesports.ui.login.components.RoundedButton
import com.example.gesports.ui.login.backend.ges_user.usuario.GesUserViewModel
import com.example.gesports.ui.login.backend.ges_user.usuario.GesUserViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormUserScreen(navController: NavHostController, userId: Int) {
    val context = LocalContext.current
    val viewModel: GesUserViewModel = viewModel(factory = GesUserViewModelFactory(context))

    var name        by rememberSaveable { mutableStateOf("") }
    var email       by rememberSaveable { mutableStateOf("") }
    var password    by rememberSaveable { mutableStateOf("") }
    var role        by rememberSaveable { mutableStateOf("jugador") }
    var equipoId    by rememberSaveable { mutableStateOf<Int?>(null) }

    var expandedRoles   by remember { mutableStateOf(false) }
    var expandedEquipos by remember { mutableStateOf(false) }
    var equipos         by remember { mutableStateOf<List<Team>>(emptyList()) }
    var usuarioCompleto by remember { mutableStateOf<User?>(null) }
    var cargado         by remember { mutableStateOf(false) }

    // Etiqueta legible del rol actual usando UserRoles
    val rolLabel = UserRoles.allRoles.firstOrNull { it.first == role }?.second ?: "Jugador"

    LaunchedEffect(userId) {
        if (!cargado) {
            if (userId != 0) {
                viewModel.userRepository.getUserById(userId)?.let {
                    usuarioCompleto = it
                    name     = it.name
                    email    = it.email
                    password = it.password
                    role     = it.role
                    equipoId = it.equipoId
                }
            }
            viewModel.teamRepository.getAllTeams().collect { list ->
                equipos = list
            }
            cargado = true
        }
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
                    title = if (userId == 0) "Nuevo Usuario" else "Editar Usuario",
                    isMainScreen = false,
                    onBackClick = { navController.popBackStack() }
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
                Spacer(Modifier.height(24.dp))

                SportTextField(
                    value = name,
                    onValueChange = { name = it },
                    placeholder = "Nombre",
                    trailingIcon = { Icon(Icons.Filled.Person, null, tint = Color.White) }
                )
                Spacer(Modifier.height(16.dp))

                SportTextField(
                    value = email,
                    onValueChange = { email = it },
                    placeholder = "Email",
                    trailingIcon = { Icon(Icons.Filled.Email, null, tint = Color.White) }
                )
                Spacer(Modifier.height(16.dp))

                SportTextField(
                    value = password,
                    onValueChange = { password = it },
                    placeholder = "Contraseña",
                    visualTransformation = PasswordVisualTransformation(),
                    trailingIcon = { Icon(Icons.Filled.Visibility, null, tint = Color.White) }
                )
                Spacer(Modifier.height(24.dp))

                // ── Dropdown Rol ──────────────────────────────────────────────
                Text("Rol", color = Color.Black)
                Spacer(Modifier.height(8.dp))
                ExposedDropdownMenuBox(
                    expanded = expandedRoles,
                    onExpandedChange = { expandedRoles = !expandedRoles }
                ) {
                    OutlinedTextField(
                        value = rolLabel,
                        onValueChange = {},
                        readOnly = true,
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedRoles)
                        },
                        modifier = Modifier
                            .menuAnchor()
                            .width(300.dp)
                            .height(54.dp),
                        shape = RoundedCornerShape(15.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor      = Color(0xFF7BB8FD),
                            unfocusedContainerColor    = Color(0xFF7BB8FD),
                            focusedBorderColor         = Color(0xFF0059FF),
                            unfocusedBorderColor       = Color(0xFF0059FF),
                            focusedTextColor           = Color.White,
                            unfocusedTextColor         = Color.White,
                            focusedTrailingIconColor   = Color.White,
                            unfocusedTrailingIconColor = Color.White
                        )
                    )
                    ExposedDropdownMenu(
                        expanded = expandedRoles,
                        onDismissRequest = { expandedRoles = false }
                    ) {
                        UserRoles.allRoles.forEach { (clave, etiqueta) ->
                            DropdownMenuItem(
                                text = { Text(etiqueta) },
                                onClick = {
                                    role = clave
                                    expandedRoles = false
                                },
                                leadingIcon = {
                                    if (role == clave) {
                                        Icon(Icons.Filled.Check, null, tint = Color(0xFF003FBC))
                                    }
                                }
                            )
                        }
                    }
                }

                Spacer(Modifier.height(16.dp))

                // ── Dropdown Equipo ───────────────────────────────────────────
                Text("Equipo", color = Color.Black)
                Spacer(Modifier.height(8.dp))
                ExposedDropdownMenuBox(
                    expanded = expandedEquipos,
                    onExpandedChange = { expandedEquipos = !expandedEquipos }
                ) {
                    OutlinedTextField(
                        value = equipos.firstOrNull { it.id == equipoId }?.name ?: "Sin equipo",
                        onValueChange = {},
                        readOnly = true,
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedEquipos)
                        },
                        modifier = Modifier
                            .menuAnchor()
                            .width(300.dp)
                            .height(54.dp),
                        shape = RoundedCornerShape(15.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor      = Color(0xFF7BB8FD),
                            unfocusedContainerColor    = Color(0xFF7BB8FD),
                            focusedBorderColor         = Color(0xFF0059FF),
                            unfocusedBorderColor       = Color(0xFF0059FF),
                            focusedTextColor           = Color.White,
                            unfocusedTextColor         = Color.White,
                            focusedTrailingIconColor   = Color.White,
                            unfocusedTrailingIconColor = Color.White
                        )
                    )
                    ExposedDropdownMenu(
                        expanded = expandedEquipos,
                        onDismissRequest = { expandedEquipos = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Sin equipo") },
                            onClick = {
                                equipoId = null
                                expandedEquipos = false
                            }
                        )
                        equipos.forEach { equipo ->
                            DropdownMenuItem(
                                text = { Text(equipo.name) },
                                onClick = {
                                    equipoId = equipo.id
                                    expandedEquipos = false
                                },
                                leadingIcon = {
                                    if (equipoId == equipo.id) {
                                        Icon(Icons.Filled.Check, null, tint = Color(0xFF003FBC))
                                    }
                                }
                            )
                        }
                    }
                }

                Spacer(Modifier.height(40.dp))

                RoundedButton(
                    text = if (userId == 0) "Crear usuario" else "Guardar cambios",
                    onClick = {
                        if (name.isNotBlank() && email.isNotBlank()) {
                            if (userId == 0) {
                                viewModel.addUser(
                                    User(
                                        id       = 0,
                                        name     = name,
                                        email    = email,
                                        password = password,
                                        role     = role,
                                        equipoId = equipoId,
                                        activo   = true
                                    )
                                )
                            } else {
                                usuarioCompleto?.let {
                                    viewModel.updateUser(
                                        it.copy(
                                            name     = name,
                                            email    = email,
                                            password = password,
                                            role     = role,
                                            equipoId = equipoId
                                        )
                                    )
                                }
                            }
                            navController.popBackStack()
                        }
                    }
                )

                Spacer(Modifier.height(24.dp))
            }
        }
    }
}