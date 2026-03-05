package com.example.gesports.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity(tableName = "facility")
@Serializable
data class Facility(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nombre: String,
    val ubicacion: String,
    val precioHora: Double,
    val disponible: Boolean = true,
    val category: String,
    val horarioApertura: Int = 9,
    val horarioCierre: Int = 21
)