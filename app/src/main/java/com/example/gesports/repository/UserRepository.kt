package com.example.gesports.repository

import com.example.gesports.models.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    fun getAllUsers(): Flow<List<User>>
    fun getUsersByRole(role: String): Flow<List<User>>
    /* Obtener una lista con todos los usuarios */
    /*suspend fun getAllUsers(): List<User>*/

    /** Obtener una lista de usaurios por rol */
    /*suspend fun getUsersByRole(rol: String): List<User>*/

    /** Obtener un usuario por id */
    suspend fun getUserById(id: Int): User?

    /** Crear usuario nuevo */
    suspend fun addUser(user: User): User

    /** Actualizar usuario existente (true si se ha podido actualizar) */
    suspend fun updateUser(user: User): Int

    /** Eliminar usuario por id (true si se ha eliminado alguno) */
    suspend fun deleteUser(id: Int): Boolean

    suspend fun getUserByEmail(email: String): User?

}
/*
* PARA ROOM
* Room trabaja con Flow<List<User>> (para que Compose actualice automáticamente los datos al insertarse o borrarse usuarios).
*  fun getAllUsers(): Flow<List<User>>
    fun getUsersByRole(role: String): Flow<List<User>>
* */