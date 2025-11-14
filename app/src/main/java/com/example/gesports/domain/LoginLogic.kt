package com.example.gesports.domain

import com.example.gesports.data.LoginRepository
import com.example.gesports.models.User


class LoginLogic {
    fun comprobarLogin(username: String, password: String): User {
        if (username.isBlank() || password.isBlank()) {
            throw IllegalArgumentException("Los campos no pueden estar vacíos.")
        }

        val user = LoginRepository.obtenerUsuarios()
            .find { it.username == username && it.password == password }
            ?: throw IllegalArgumentException("Email o contraseña incorrectos.")

        return user
    }
}