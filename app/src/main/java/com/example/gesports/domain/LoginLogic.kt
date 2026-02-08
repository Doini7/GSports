/*
package com.example.ges_sports.domain

import com.example.gesports.data.LoginRepository
import com.example.gesports.models.User

class LoginLogic {
    fun comprobarLogin(email: String, password: String): User {
        if (email.isBlank() || password.isBlank()) {
            throw IllegalArgumentException("Los campos no pueden estar vacíos.")
        }

        val user = LoginRepository.obtenerUsuarios()
            .find { it.email == email && it.password == password }
            ?: throw IllegalArgumentException("Email o contraseña incorrectos.")

        return user
    }
}*/
