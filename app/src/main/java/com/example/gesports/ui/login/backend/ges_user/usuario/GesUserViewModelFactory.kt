package com.example.gesports.ui.login.backend.ges_user.usuario

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.gesports.data.room.RoomUserRepository
import com.example.gesports.data.room.RoomTeamRepository // Asegúrate de tener este repo
import com.example.gesports.database.AppDatabase

class GesUserViewModelFactory(private val appContext: Context) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val database = AppDatabase.getDatabase(appContext)

        val userRepo = RoomUserRepository(database.userDao())
        val teamRepo = RoomTeamRepository(database.teamDao())

        return GesUserViewModel(userRepo, teamRepo) as T
    }
}