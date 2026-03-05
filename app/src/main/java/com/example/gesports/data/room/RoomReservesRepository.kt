package com.example.gesports.data.room

import com.example.gesports.database.ReservationDao
import com.example.gesports.models.Reservation
import com.example.gesports.repository.ReservationRepository
import kotlinx.coroutines.flow.Flow

class RoomReservesRepository(private val reservationDao: ReservationDao) : ReservationRepository {
    override fun getAllReservations(): Flow<List<Reservation>> = reservationDao.getAll()

    override suspend fun getReservationById(id: Int): Reservation? = reservationDao.getById(id)

    override fun getByFacility(facilityId: Int): Flow<List<Reservation>> =
        reservationDao.getByFacility(facilityId.toString())

    override suspend fun addReservation(reservation: Reservation): Reservation {
        val id = reservationDao.insert(reservation)
        return reservation.copy(id = id.toInt())
    }

    override suspend fun updateReservation(reservation: Reservation): Int =
        reservationDao.update(reservation)

    override suspend fun deleteReservation(id: Int): Boolean {
        val reservation = reservationDao.getById(id)
        return if (reservation != null) {
            reservationDao.delete(reservation)
            true
        } else false
    }
}