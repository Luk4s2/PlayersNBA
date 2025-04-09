package eu.playersnba.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import eu.playersnba.ui.playerdetail.PlayerDetailScreen
import eu.playersnba.ui.playerlist.PlayerListScreen
import eu.playersnba.ui.teamdetail.TeamDetailScreen
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

/**
 * Defines the navigation graph for the application.
 * Includes PlayerList, PlayerDetail and TeamDetail screens.
 */
@Composable
fun AppNavGraph(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = Routes.PLAYER_LIST,
        modifier = modifier
    ) {
        composable(Routes.PLAYER_LIST) {
            PlayerListScreen(navController = navController)
        }

        composable(
            route = Routes.PLAYER_DETAIL,
            arguments = listOf(
                navArgument("playerId") { type = NavType.IntType },
                navArgument("imageUrl") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val playerId = backStackEntry.arguments?.getInt("playerId") ?: -1
            val imageUrlEncoded = backStackEntry.arguments?.getString("imageUrl") ?: ""
            val imageUrl = URLDecoder.decode(imageUrlEncoded, StandardCharsets.UTF_8.toString())

            PlayerDetailScreen(
                playerId = playerId,
                imageUrl = imageUrl,
                navController = navController
            )
        }

        composable(Routes.TEAM_DETAIL) { backStackEntry ->
            val teamId = backStackEntry.arguments?.getString("teamId")?.toIntOrNull()
            teamId?.let {
                TeamDetailScreen(
                    teamId = it,
                    navController = navController
                )
            }
        }
    }
}

