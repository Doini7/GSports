package com.example.gesports.ui.login.backend.ges_user.pista

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gesports.models.Facility
import com.example.gesports.repository.FacilityRepository
import kotlinx.coroutines.launch

// Cambiamos private val facilityDao por val facilityRepository
class GesFacilitiesViewModel(val facilityRepository: FacilityRepository) : ViewModel() {

    var facilities by mutableStateOf<List<Facility>>(emptyList())
        private set

    var selectedCategory by mutableStateOf<String?>(null)
        private set

    init {
        loadFacilities()
    }

    private fun loadFacilities() {
        viewModelScope.launch {
            facilityRepository.getAllFacilities().collect { list ->
                facilities = if (selectedCategory == null) {
                    list
                } else {
                    list.filter { it.category == selectedCategory }
                }
            }
        }
    }

    fun onCategorySelected(category: String?) {
        selectedCategory = category
        loadFacilities()
    }

    fun addFacility(facility: Facility) = viewModelScope.launch {
        facilityRepository.addFacility(facility)
    }

    fun updateFacility(facility: Facility) = viewModelScope.launch {
        facilityRepository.updateFacility(facility)
    }

    fun deleteFacility(facility: Facility) {
        viewModelScope.launch {
            facilityRepository.deleteFacility(facility.id)
        }
    }
}