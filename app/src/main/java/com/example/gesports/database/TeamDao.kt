package com.example.gesports.database

import androidx.room.*
import com.example.gesports.models.Team
import kotlinx.coroutines.flow.Flow

@Dao
interface TeamDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(team: Team): Long

    @Query("SELECT * FROM teams ORDER BY name ASC")
    fun getAll(): Flow<List<Team>>

    @Query("SELECT * FROM teams WHERE id = :id LIMIT 1")
    suspend fun getById(id: Int): Team?

    @Update
    suspend fun update(team: Team): Int

    @Delete
    suspend fun delete(team: Team)
}
