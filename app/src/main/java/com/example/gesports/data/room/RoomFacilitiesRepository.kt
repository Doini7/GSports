package com.example.gesports.data.room

import com.example.gesports.database.FacilityDao
import com.example.gesports.models.Facility
import com.example.gesports.repository.FacilityRepository
import kotlinx.coroutines.flow.Flow

class RoomFacilitiesRepository(private val facilityDao: FacilityDao) : FacilityRepository {
    override fun getAllFacilities(): Flow<List<Facility>> = facilityDao.getAll()

    override suspend fun getFacilityById(id: Int): Facility? = facilityDao.getById(id)

    override suspend fun addFacility(facility: Facility): Facility {
        val id = facilityDao.insert(facility)
        return facility.copy(id = id.toInt())
    }

    override suspend fun updateFacility(facility: Facility): Int = facilityDao.update(facility)

    override suspend fun deleteFacility(id: Int): Boolean {
        val facility = facilityDao.getById(id)
        return if (facility != null) {
            facilityDao.delete(facility)
            true
        } else false
    }
}