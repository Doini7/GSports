package com.example.gesports.database

import androidx.room.*
import com.example.gesports.models.Facility
import kotlinx.coroutines.flow.Flow

@Dao
interface FacilityDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(facility: Facility): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(facilities: List<Facility>)

    @Query("SELECT * FROM facility ORDER BY nombre ASC")
    fun getAll(): Flow<List<Facility>>

    @Query("SELECT * FROM facility WHERE id = :id LIMIT 1")
    suspend fun getById(id: Int): Facility?

    @Update
    suspend fun update(facility: Facility): Int

    @Delete
    suspend fun delete(facility: Facility)

    @Query("DELETE FROM facility")
    suspend fun deleteAll()
}
