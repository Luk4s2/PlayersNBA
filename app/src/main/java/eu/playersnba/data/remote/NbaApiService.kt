package eu.playersnba.data.remote

import eu.playersnba.data.model.PlayerResponse
import eu.playersnba.data.model.SinglePlayerResponse
import eu.playersnba.data.model.SingleTeamResponse
import eu.playersnba.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Retrofit interface defining NBA API endpoints.
 */
interface NbaApiService {

    /**
     * Get paginated list of players.
     * @param page page number
     * @param perPage players per page
     * @return PlayerResponse
     */
    @GET("players")
    suspend fun getPlayers(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = Constants.PAGE_SIZE
    ): PlayerResponse

    /**
     * Get detailed player information.
     * @param id player ID
     * @return SinglePlayerResponse
     */
    @GET("players/{id}")
    suspend fun getPlayerDetail(@Path("id") id: Int): SinglePlayerResponse

    /**
     * Get detailed team information.
     * @param id team ID
     * @return SingleTeamResponse
     */
    @GET("teams/{id}")
    suspend fun getTeamDetail(@Path("id") id: Int): SingleTeamResponse
}
