package com.example.gesports.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.gesports.models.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    // CREATE
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(users: List<User>)

    // READ
    @Query("SELECT * FROM usuarios ORDER BY nombre ASC")
    fun getAll(): Flow<List<User>>
    @Query("SELECT * FROM usuarios WHERE rol = :role ORDER BY nombre ASC")
    fun getByRole(role: String): Flow<List<User>>
    @Query("SELECT * FROM usuarios WHERE id = :id LIMIT 1")
    suspend fun getById(id: Int): User?

    @Query("SELECT * FROM usuarios WHERE email = :email LIMIT 1")
    suspend fun getByEmail(email: String): User?

    // UPDATE
    @Update
    suspend fun update(user: User):Int //Me devolverá el número de filas actualizada

    // DELETE
    @Delete
    suspend fun delete(user: User)

    @Query("DELETE FROM usuarios")
    suspend fun deleteAll()
}