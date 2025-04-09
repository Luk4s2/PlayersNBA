package eu.playersnba.navigation

object Routes {
    const val PLAYER_LIST = "playerList"
    const val PLAYER_DETAIL = "playerDetail/{playerId}/{imageUrl}"
    const val TEAM_DETAIL = "teamDetail/{teamId}"

    fun playerDetailRoute(playerId: Int, encodedImageUrl: String) =
        "playerDetail/$playerId/$encodedImageUrl"

    fun teamDetailRoute(teamId: Int) = "teamDetail/$teamId"
}