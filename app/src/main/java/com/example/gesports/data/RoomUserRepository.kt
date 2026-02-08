package com.example.gesports.data

import com.example.gesports.database.UserDao
import com.example.gesports.models.User
import com.example.gesports.repository.UserRepository
import kotlinx.coroutines.flow.Flow


class RoomUserRepository(private val userDao: UserDao): UserRepository {

    override fun getAllUsers(): Flow<List<User>> = userDao.getAll()

    override fun getUsersByRole(role: String): Flow<List<User>> =
        userDao.getByRole(role) // Añadimos este método en el DAO

    override suspend fun addUser(user: User): User {
        val id = userDao.insert(user)
        return user.copy(id = id.toInt())
    }


    override suspend fun updateUser(user: User): Int {
        var num_actualizado = userDao.update(user)
        return num_actualizado;
    }

    override suspend fun deleteUser(id: Int): Boolean {
        val user = userDao.getById(id)
        return if (user != null) {
            userDao.delete(user)
            true
        } else {
            false
        }
    }

    override suspend fun getUserByEmail(email: String): User? {
        return userDao.getByEmail(email)
    }

    override suspend fun getUserById(id: Int): User? = userDao.getById(id)



}