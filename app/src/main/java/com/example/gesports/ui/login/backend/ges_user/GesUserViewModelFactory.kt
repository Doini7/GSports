package com.example.gesports.ui.login.backend.ges_user

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.gesports.data.RoomUserRepository
import com.example.gesports.database.AppDatabase

class GesUserViewModelFactory(
    private val appContext: Context
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        // Crear la base de datos (NECESITA Context)
        val database = AppDatabase.getDatabase(appContext)

        // Obtener el DAO
        val userDao = database.userDao()

        val repo = RoomUserRepository(userDao)  // se crea tu repo real
        return GesUserViewModel(repo) as T  //se crea el ViewModel
    }
}