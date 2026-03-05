package com.example.gesports.ui.login.backend.ges_user.pista

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.gesports.data.room.RoomFacilitiesRepository
import com.example.gesports.database.AppDatabase

class GesFacilitiesViewModelFactory(private val appContext: Context) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val database = AppDatabase.Companion.getDatabase(appContext)
        val facilityDao = database.facilityDao()
        val repo = RoomFacilitiesRepository(facilityDao)

        return GesFacilitiesViewModel(repo) as T
    }
}