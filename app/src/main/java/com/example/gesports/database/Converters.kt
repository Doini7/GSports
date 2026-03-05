package com.example.gesports.database

import androidx.room.TypeConverter
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class Converters {
    // Para Listas de Integers (Ids de jugadores, etc.)
    @TypeConverter
    fun fromIntList(value: List<Int>): String {
        return Json.encodeToString(value)
    }

    @TypeConverter
    fun toIntList(value: String): List<Int> {
        return try {
            Json.decodeFromString(value)
        } catch (e: Exception) {
            emptyList()
        }
    }

    // Para Listas de Strings (Roles, categorías, etc.)
    @TypeConverter
    fun fromStringList(value: List<String>): String {
        return Json.encodeToString(value)
    }

    @TypeConverter
    fun toStringList(value: String): List<String> {
        return try {
            Json.decodeFromString(value)
        } catch (e: Exception) {
            emptyList()
        }
    }

    // Para Listas de Mapas (Horarios)
    @TypeConverter
    fun fromHorarios(horarios: List<Map<String, String>>): String {
        return Json.encodeToString(horarios)
    }

    @TypeConverter
    fun toHorarios(data: String): List<Map<String, String>> {
        return try {
            Json.decodeFromString(data)
        } catch (e: Exception) {
            emptyList()
        }
    }

    private val dateFormatter = DateTimeFormatter.ISO_LOCAL_DATE
    private val timeFormatter = DateTimeFormatter.ISO_LOCAL_TIME

    @TypeConverter
    fun fromLocalDate(date: LocalDate?): String? {
        return date?.format(dateFormatter)
    }

    @TypeConverter
    fun toLocalDate(dateString: String?): LocalDate? {
        return dateString?.let { LocalDate.parse(it, dateFormatter) }
    }

    @TypeConverter
    fun fromLocalTime(time: LocalTime?): String? {
        return time?.format(timeFormatter)
    }

    @TypeConverter
    fun toLocalTime(timeString: String?): LocalTime? {
        return timeString?.let { LocalTime.parse(it, timeFormatter) }
    }
}