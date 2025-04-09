package eu.playersnba.utils

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import eu.playersnba.data.model.Player
import eu.playersnba.data.model.PlayerImageEntry
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

fun loadPlayerImageMap(context: Context): List<PlayerImageEntry> {
	val json = context.assets.open("nba_players.json").bufferedReader().use { it.readText() }
	val type = object : TypeToken<List<PlayerImageEntry>>() {}.type
	return Gson().fromJson(json, type)
}

fun getPlayerImageUrl(player: Player, imageMap: List<PlayerImageEntry>): String? {
	val match = imageMap.find {
		it.firstName.equals(player.firstName, ignoreCase = true) &&
				it.lastName.equals(player.lastName, ignoreCase = true)
	}
	return match?.let {
		"https://cdn.nba.com/headshots/nba/latest/1040x760/${it.playerId}.png"
	}
}

fun getSafeEncodedPlayerImageUrl(player: Player?, imageMap: List<PlayerImageEntry>): String {
	val raw = player?.let { getPlayerImageUrl(it, imageMap) }.orEmpty()
	val safe = raw.ifBlank { Constants.PLACEHOLDER_IMAGE }
	return URLEncoder.encode(safe, StandardCharsets.UTF_8.toString())
}
