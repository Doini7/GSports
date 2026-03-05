package com.example.gesports.repository

import com.example.gesports.models.Team
import kotlinx.coroutines.flow.Flow

interface TeamRepository {
    fun getAllTeams(): Flow<List<Team>>
    suspend fun getTeamById(id: Int): Team?
    suspend fun addTeam(team: Team): Team
    suspend fun updateTeam(team: Team): Int
    suspend fun deleteTeam(id: Int): Boolean
}