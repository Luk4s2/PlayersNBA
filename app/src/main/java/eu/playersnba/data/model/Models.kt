package eu.playersnba.data.model

import com.google.gson.annotations.SerializedName


data class SinglePlayerResponse(
    @SerializedName("data")val player: Player
)

data class PlayerImageEntry(
    val firstName: String,
    val lastName: String,
    val playerId: Int
)

data class Player(
    val id: Int,
    @SerializedName("first_name") val firstName: String,
    @SerializedName("last_name") val lastName: String,
    val position: String?,
    val height: String?,
    val weight: String?,
    @SerializedName("jersey_number") val jerseyNumber: String?,
    val college: String?,
    val country: String?,
    @SerializedName("draft_year") val draftYear: Int?,
    @SerializedName("draft_round") val draftRound: Int?,
    @SerializedName("draft_number") val draftNumber: Int?,
    val team: Team?
)

data class SingleTeamResponse(
    @SerializedName("data")val team: Team
)
data class Team(
    val id: Int,
    val abbreviation: String,
    val city: String,
    val conference: String,
    val division: String,
    @SerializedName("full_name") val fullName: String,
    val name: String
)

data class PlayerResponse(
    @SerializedName("data") val players: List<Player>,
    val meta: Meta
)

data class Meta(
    @SerializedName("next_cursor") val nextCursor: Int?,
    @SerializedName("per_page") val perPage: Int
)
