package com.example.gesports.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity(tableName = "usuarios")
@Serializable
data class User (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nombre: String,
    val email: String,
    val password: String,
    val rol: String,
/*    val edad: Int,
    val telefono: String,
    val posicion: String,
    val equipoId: Int*/
)