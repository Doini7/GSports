package com.example.gesports.database

import androidx.room.*
import com.example.gesports.models.Reservation
import kotlinx.coroutines.flow.Flow

@Dao
interface ReservationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(reservation: Reservation): Long

    @Query("SELECT * FROM reservations ORDER BY fechaReserva ASC")
    fun getAll(): Flow<List<Reservation>>

    @Query("SELECT * FROM reservations WHERE id = :id LIMIT 1")
    suspend fun getById(id: Int): Reservation?

    @Query("SELECT * FROM reservations WHERE facilityId = :facilityId")
    fun getByFacility(facilityId: String): Flow<List<Reservation>>

    @Query("SELECT * FROM reservations WHERE teamId = :teamId")
    fun getByTeam(teamId: Int): Flow<List<Reservation>>

    @Update
    suspend fun update(reservation: Reservation): Int

    @Delete
    suspend fun delete(reservation: Reservation)
}