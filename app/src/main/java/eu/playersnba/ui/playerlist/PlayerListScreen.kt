
package eu.playersnba.ui.playerlist

import android.content.Context
import android.util.Log
import android.view.ViewGroup
import android.widget.ImageView
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import eu.playersnba.R
import eu.playersnba.data.model.Player
import eu.playersnba.data.model.PlayerImageEntry
import eu.playersnba.navigation.Routes
import eu.playersnba.utils.Constants
import eu.playersnba.utils.Dimens
import eu.playersnba.utils.getPlayerImageUrl
import eu.playersnba.utils.getSafeEncodedPlayerImageUrl
import eu.playersnba.utils.loadPlayerImageMap

@Composable
fun PlayerListScreen(
    navController: NavController,
    viewModel: PlayerListViewModel = hiltViewModel()
) {
    val players = viewModel.players.collectAsLazyPagingItems()
    val context = LocalContext.current
    val imageMap = remember { loadPlayerImageMap(context) }

    LaunchedEffect(players) {
        Log.d("PlayerList", "Collecting players flow, item count: ${players.itemCount}")
    }

    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(Dimens.TopAppBarHeight)
                    .background(
                        Brush.horizontalGradient(
                            colors = listOf(Color(0xFF17408B), Color(0xFFC9082A))
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = Constants.APP_TITLE,
                    style = MaterialTheme.typography.headlineLarge,
                    color = Color.White
                )
            }
        }
    ) { paddingValues ->
        if (players.loadState.refresh is androidx.paging.LoadState.Loading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CircularProgressIndicator()
                    Spacer(modifier = Modifier.width(Dimens.ElementSpacing))
                    Text(Constants.LOADING_PLAYERS)
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = paddingValues
            ) {
                items(players.itemCount) { index ->
                    val player = players[index]
                    val encodedImageUrl = getSafeEncodedPlayerImageUrl(player, imageMap)

                    if (player != null) {
                        PlayerItem(player, imageMap, context) {
                            navController.navigate(Routes.playerDetailRoute(player.id, encodedImageUrl))
                        }
                    }
                }

                players.apply {
                    when {
                        loadState.append is androidx.paging.LoadState.Loading -> {
                            item { LoadingItem("Loading more...") }
                        }
                        loadState.append is androidx.paging.LoadState.Error -> {
                            val e = loadState.append as androidx.paging.LoadState.Error
                            item {
                                Text("Error: ${e.error.localizedMessage}", modifier = Modifier.padding(Dimens.ScreenPadding))
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PlayerItem(
    player: Player,
    imageMap: List<PlayerImageEntry>,
    context: Context,
    onClick: () -> Unit
) {
    val imageUrl = getPlayerImageUrl(player, imageMap)

    Card(
        modifier = Modifier
            .padding(horizontal = 12.dp, vertical = 6.dp)
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = Dimens.SmallSpacing),
        shape = RoundedCornerShape(Dimens.CardCornerRadius),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = Dimens.ScreenPadding, vertical = Dimens.ElementSpacing)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AndroidView(
                factory = {
                    ImageView(it).apply {
                        layoutParams = ViewGroup.LayoutParams(200, 200)
                        scaleType = ImageView.ScaleType.CENTER_CROP
                    }
                },
                update = { imageView ->
                    Glide.with(context)
                        .load(imageUrl)
                        .apply(
                            RequestOptions()
                                .placeholder(R.drawable.placeholder)
                                .error(R.drawable.placeholder)
                                .circleCrop()
                        )
                        .into(imageView)
                },
                modifier = Modifier.size(Dimens.ImageSize)
            )

            Spacer(modifier = Modifier.width(Dimens.ScreenPadding))

            Column {
                Text(
                    "${player.firstName} ${player.lastName}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1C1C1E)
                )
                Row {
                    Text(
                        "Position: ",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        player.position ?: "N/A",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Row {
                    Text(
                        "Team: ",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        player.team?.fullName ?: "Unknown",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}

@Composable
fun LoadingItem(text: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(Dimens.ScreenPadding),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator()
        Spacer(modifier = Modifier.width(Dimens.ElementSpacing))
        Text(text)
    }
}
