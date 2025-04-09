package eu.playersnba.data.repository

import eu.playersnba.base.ResultWrapper
import eu.playersnba.data.model.PlayerResponse
import eu.playersnba.data.model.SinglePlayerResponse
import eu.playersnba.data.model.SingleTeamResponse
import eu.playersnba.data.remote.NbaApiService
import javax.inject.Inject

/**
 * Repository class responsible for handling API calls
 * related to NBA players and teams.
 */
open class PlayerRepository @Inject constructor(
    private val api: NbaApiService
) {

    /**
     * Fetch a list of players from the API.
     * @param page the page number to load
     * @param perPage number of players per page
     * @return ResultWrapper containing PlayerResponse
     */
    suspend fun getPlayers(page: Int, perPage: Int): ResultWrapper<PlayerResponse> {
        return try {
            val response = api.getPlayers(page, perPage)
            ResultWrapper.Success(response)
        } catch (e: Exception) {
            ResultWrapper.Error(e.message ?: "Unknown error")
        }
    }

    /**
     * Fetch detailed player information.
     * @param id the player's unique ID
     * @return ResultWrapper containing SinglePlayerResponse
     */
    open suspend fun getPlayerDetail(id: Int): ResultWrapper<SinglePlayerResponse> {
        return try {
            val response = api.getPlayerDetail(id)
            ResultWrapper.Success(response)
        } catch (e: Exception) {
            ResultWrapper.Error(e.message ?: "Unknown error")
        }
    }

    /**
     * Fetch detailed team information.
     * @param id the team's unique ID
     * @return ResultWrapper containing SingleTeamResponse
     */
    open suspend fun getTeamDetail(id: Int): ResultWrapper<SingleTeamResponse> {
        return try {
            val response = api.getTeamDetail(id)
            ResultWrapper.Success(response)
        } catch (e: Exception) {
            ResultWrapper.Error(e.message ?: "Unknown error")
        }
    }
}

