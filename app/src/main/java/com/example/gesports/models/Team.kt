package com.example.gesports.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity(tableName = "teams")
@Serializable
data class Team(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val sport: String,
)