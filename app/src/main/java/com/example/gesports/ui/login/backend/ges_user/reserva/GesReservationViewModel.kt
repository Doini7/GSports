package com.example.gesports.ui.login.backend.ges_user.reserva

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gesports.data.room.RoomReservesRepository
import com.example.gesports.models.Reservation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class GesReservationViewModel(private val repository: RoomReservesRepository) : ViewModel() {

    // Flow de todas las reservas
    val allReservations: Flow<List<Reservation>> = repository.getAllReservations()

    // Función para filtrar reservas por usuario

    fun getReservationsByUser(userId: Int, userTeamId: Int?): Flow<List<Reservation>> {
        return allReservations.map { list ->
            list.filter { reservation ->
                reservation.userId == userId ||
                        (userTeamId != null && reservation.teamId == userTeamId)
            }
        }
    }

    fun deleteReservation(reservation: Reservation) {
        viewModelScope.launch {
            repository.deleteReservation(reservation.id)
        }
    }

    fun addReservation(reservation: Reservation) {
        viewModelScope.launch {
            repository.addReservation(reservation)
        }
    }
}