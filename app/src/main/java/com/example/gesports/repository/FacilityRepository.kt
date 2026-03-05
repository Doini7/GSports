package com.example.gesports.repository

import com.example.gesports.models.Facility
import kotlinx.coroutines.flow.Flow

interface FacilityRepository {
    fun getAllFacilities(): Flow<List<Facility>>
    suspend fun getFacilityById(id: Int): Facility?
    suspend fun addFacility(facility: Facility): Facility
    suspend fun updateFacility(facility: Facility): Int
    suspend fun deleteFacility(id: Int): Boolean
}