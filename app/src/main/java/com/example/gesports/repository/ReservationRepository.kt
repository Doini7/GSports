package com.example.gesports.repository

import com.example.gesports.models.Reservation
import kotlinx.coroutines.flow.Flow

interface ReservationRepository {
    fun getAllReservations(): Flow<List<Reservation>>
    suspend fun getReservationById(id: Int): Reservation?
    fun getByFacility(facilityId: Int): Flow<List<Reservation>>
    suspend fun addReservation(reservation: Reservation): Reservation
    suspend fun updateReservation(reservation: Reservation): Int
    suspend fun deleteReservation(id: Int): Boolean
}