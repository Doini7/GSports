package com.example.gesports.models

// Para filtrar por roles de usuario
object UserRoles {
    val allRoles = listOf(
        "ADMIN_DEPORTIVO" to "Admin",
        "ENTRENADOR"      to "Entrenador",
        "ARBITRO"         to "Árbitro",
        "JUGADOR"         to "Jugador"
    )

    val routes = mapOf(
        "ADMIN_DEPORTIVO" to "dashboard",
        "ENTRENADOR"      to "home",
        "ARBITRO"         to "home",
        "JUGADOR"         to "home"
    )

}

