package com.example.gesports.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.gesports.database.Converters
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.time.LocalDate
import java.time.LocalTime

@Entity(tableName = "reservations")
@Serializable
data class Reservation(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val facilityId: String,
    val userId: Int? = null,
    val teamId: Int? = null,
    val fechaReserva: String,
    val horaInicio: String,
    val horaFin: String,
    val category: String? = null
)