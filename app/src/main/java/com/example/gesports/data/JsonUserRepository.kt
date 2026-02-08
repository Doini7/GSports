package com.example.gesports.data

import android.content.Context
import android.util.Log
import com.example.gesports.models.User
import com.example.gesports.repository.UserRepository
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString
import kotlinx.serialization.decodeFromString
import java.io.File
/*
class JsonUserRepository(private val context: Context): UserRepository {
    private val TAG = "Fichero"
    private val jsonFile = File (context.filesDir, "user.json")

    val jsonString = context.assets.open("user.json")   //  nombre EXACTO del archivo
        .bufferedReader()
        .use { it.readText() }

    private val json = Json {
        prettyPrint = true
        ignoreUnknownKeys = true
    }
    private val users = mutableListOf<User>()
    private var nextId: Int = 1

    init {
        Log.i (TAG, "Carga fichero")

       loadFile()

    }

    private fun loadFile(){
        Log.i(TAG, "Carga Fichero")
        if (jsonFile.exists()){
            Log.i(TAG, "Encontrado Fichero")
            val text = jsonFile.readText()
            if (text.isNotBlank()) {
                Log.i(TAG, text)
                val loadUsers: List<User> = json.decodeFromString(text)
                users.clear()
                users.addAll(loadUsers)
                nextId = (users.maxOfOrNull { it.id } ?: 0) + 1
            }
        }else{
            Log.i(TAG, "cargando desde assets")
            loadFromAssets()
            saveToFile()
        }
    }


    private fun loadFromAssets() {
        if (jsonString.isBlank()) {
            users.clear()
            nextId = 1
            return
        }
        Log.i(TAG, jsonString)
        val loadedUsers: List<User> = json.decodeFromString(jsonString)
        users.clear()
        users.addAll(loadedUsers)
        nextId = (users.maxOfOrNull { it.id } ?: 0) + 1
    }

    private fun saveToFile() {
        Log.i(TAG, "guardar lista")
        Log.i(TAG, users.size.toString())
        val text = json.encodeToString(users)
        jsonFile.writeText(text)
    }

    private fun getNewId(): Int {
        return (users.maxOfOrNull { it.id } ?: 0) + 1
    }


    override suspend fun getAllUsers(): List<User> {
        return users.toList()
    }

    override suspend fun getUsersByRole(rol: String): List<User> {
        return users.filter { it.rol == rol }
    }

    override suspend fun getUserById(id: Int): User? {
        return users.find { it.id == id }
    }

    override suspend fun addUser(user: User): User {
        nextId = getNewId()
        val newUser = user.copy(id = nextId)
        users.add(newUser)
        saveToFile()
        return newUser
    }

    override suspend fun updateUser(user: User): Boolean {
        val index = users.indexOfFirst { it.id == user.id }
        if (index == -1 ) return false
        users[index] = user
        saveToFile()
        return true
    }

    override suspend fun deleteUser(id: Int): Boolean {
        val removed = users.removeIf { it.id == id }
        if (removed) {
            saveToFile()
        }
        return removed
    }
}    */