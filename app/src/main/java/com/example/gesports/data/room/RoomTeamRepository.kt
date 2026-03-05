package com.example.gesports.data.room

import com.example.gesports.database.TeamDao
import com.example.gesports.models.Team
import com.example.gesports.repository.TeamRepository
import kotlinx.coroutines.flow.Flow

class RoomTeamRepository(private val teamDao: TeamDao) : TeamRepository {
    override fun getAllTeams(): Flow<List<Team>> = teamDao.getAll()

    override suspend fun getTeamById(id: Int): Team? = teamDao.getById(id)

    override suspend fun addTeam(team: Team): Team {
        val id = teamDao.insert(team)
        return team.copy(id = id.toInt())
    }

    override suspend fun updateTeam(team: Team): Int = teamDao.update(team)

    override suspend fun deleteTeam(id: Int): Boolean {
        val team = teamDao.getById(id)
        return if (team != null) {
            teamDao.delete(team)
            true
        } else false
    }
}