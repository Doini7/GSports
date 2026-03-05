package com.example.gesports.models

// Para filtrar por roles de usuario
object UserRoles {
    val allRoles = listOf(
        "admin_deportivo" to "Admin",
        "entrenador"      to "Entrenador",
        "jugador"         to "Jugador"
    )

    val routes = mapOf(
        "admin_deportivo" to "dashboard",
        "entrenador"      to "home",
        "jugador"         to "home"
    )

}

