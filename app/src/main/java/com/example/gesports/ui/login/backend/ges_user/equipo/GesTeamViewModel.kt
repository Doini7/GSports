package com.example.gesports.ui.login.backend.ges_user.equipo

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gesports.models.Team
import com.example.gesports.repository.TeamRepository
import kotlinx.coroutines.launch

// Cambiamos private val teamDao por val teamRepository
class GesTeamViewModel(val teamRepository: TeamRepository) : ViewModel() {

    var teams by mutableStateOf<List<Team>>(emptyList())
        private set

    var selectedSport by mutableStateOf<String?>(null)
        private set

    init {
        loadTeams()
    }

    private fun loadTeams() {
        viewModelScope.launch {
            // Ahora usamos el repositorio
            teamRepository.getAllTeams().collect { list ->
                teams = if (selectedSport == null) {
                    list
                } else {
                    list.filter { it.sport == selectedSport }
                }
            }
        }
    }

    fun onSportSelected(sport: String?) {
        selectedSport = sport
        loadTeams()
    }

    fun addTeam(team: Team) = viewModelScope.launch {
        teamRepository.addTeam(team)
    }

    fun updateTeam(team: Team) = viewModelScope.launch {
        teamRepository.updateTeam(team)
    }

    fun deleteTeam(team: Team) {
        viewModelScope.launch {
            teamRepository.deleteTeam(team.id)
        }
    }
}