package com.example.gesports.ui.login.backend.ges_user.reserva

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.gesports.data.room.RoomReservesRepository
import com.example.gesports.database.AppDatabase
import com.example.gesports.ui.login.backend.ges_user.reserva.GesReservationViewModel

class GesReservationViewModelFactory(private val appContext: Context) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val database = AppDatabase.Companion.getDatabase(appContext)
        val reservationDao = database.reservationDao()
        val repo = RoomReservesRepository(reservationDao)
        return GesReservationViewModel(repo) as T
    }
}