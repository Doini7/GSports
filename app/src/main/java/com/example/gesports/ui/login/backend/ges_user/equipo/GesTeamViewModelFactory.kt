package com.example.gesports.ui.login.backend.ges_user.equipo

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.gesports.data.room.RoomTeamRepository
import com.example.gesports.database.AppDatabase
import com.example.gesports.ui.login.backend.ges_user.equipo.GesTeamViewModel

class GesTeamViewModelFactory(private val appContext: Context) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val database = AppDatabase.Companion.getDatabase(appContext)
        val teamDao = database.teamDao()
        val repo = RoomTeamRepository(teamDao)
        return GesTeamViewModel(repo) as T
    }
}