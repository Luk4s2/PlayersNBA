package eu.playersnba.ui.playerdetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material3.placeholder
import com.google.accompanist.placeholder.material3.shimmer
import eu.playersnba.R
import eu.playersnba.base.BaseUiState
import eu.playersnba.navigation.Routes
import eu.playersnba.ui.components.AttributeRow
import eu.playersnba.ui.components.ErrorWithRetry
import eu.playersnba.utils.Constants
import eu.playersnba.utils.Dimens

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun PlayerDetailScreen(
    playerId: Int,
    imageUrl: String,
    navController: NavController,
    viewModel: PlayerDetailViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadPlayer(playerId)
    }

    Scaffold(
        topBar = {
            val player = if (state is BaseUiState.Success) (state as BaseUiState.Success).data else null
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(Dimens.TopAppBarHeight),
                color = Color.Transparent
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.horizontalGradient(
                                colors = listOf(Color(0xFF17408B), Color(0xFFC9082A))
                            )
                        )
                ) {
                    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
                        val (back, title) = createRefs()

                        IconButton(
                            onClick = { navController.popBackStack() },
                            modifier = Modifier
                                .constrainAs(back) {
                                    start.linkTo(parent.start)
                                    top.linkTo(parent.top)
                                    bottom.linkTo(parent.bottom)
                                }
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back",
                                tint = Color.White
                            )
                        }

                        if (player != null) {
                            Text(
                                text = "${player.firstName} ${player.lastName}",
                                style = MaterialTheme.typography.headlineLarge,
                                color = Color.White,
                                modifier = Modifier.constrainAs(title) {
                                    start.linkTo(parent.start)
                                    end.linkTo(parent.end)
                                    top.linkTo(parent.top)
                                    bottom.linkTo(parent.bottom)
                                }
                            )
                        } else {
                            Text(
                                text = "Loading..............",
                                style = MaterialTheme.typography.headlineLarge,
                                color = Color.White,
                                modifier = Modifier
                                    .constrainAs(title) {
                                        start.linkTo(parent.start)
                                        end.linkTo(parent.end)
                                        top.linkTo(parent.top)
                                        bottom.linkTo(parent.bottom)
                                    }
                                    .placeholder(
                                        visible = true,
                                        highlight = PlaceholderHighlight.shimmer(),
                                        color = Color.LightGray
                                    )
                            )
                        }
                    }
                }
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            when (state) {
                is BaseUiState.Loading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))

                is BaseUiState.Success -> {
                    val player = (state as BaseUiState.Success).data
                    val imageToLoad = if (imageUrl == Constants.PLACEHOLDER_IMAGE) R.drawable.placeholder else imageUrl

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .padding(bottom = 80.dp)
                            .padding(Dimens.ScreenPadding)
                    ) {
                        GlideImage(
                            model = imageToLoad,
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(Dimens.PlayerImageHeight)
                        )

                        Spacer(modifier = Modifier.height(Dimens.SectionSpacing))

                        AttributeRow("Position", player.position)
                        AttributeRow("Jersey Number", player.jerseyNumber)
                        AttributeRow("Height", player.height)
                        AttributeRow("Weight", player.weight)
                        AttributeRow("College", player.college)
                        AttributeRow("Country", player.country)
                        AttributeRow("Draft Year", player.draftYear?.toString())
                        AttributeRow("Draft Round", player.draftRound?.toString())
                        AttributeRow("Draft Number", player.draftNumber?.toString())
                        AttributeRow("Team", player.team?.fullName)
                    }

                    // Floating button
                    Button(
                        onClick = {
                            player.team?.id?.let { teamId ->
                                navController.navigate(Routes.teamDetailRoute(teamId))
                            }
                        },
                        enabled = player.team != null,
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(Dimens.ScreenPadding),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF0A3D91),
                            disabledContainerColor = Color.Gray
                        )
                    ) {
                        Text(Constants.VIEW_TEAM_DETAIL)
                    }
                }

                is BaseUiState.Error -> ErrorWithRetry(
                    message = (state as BaseUiState.Error).message,
                    onRetry = { viewModel.loadPlayer(playerId) }
                )
                is BaseUiState.Empty -> Text("No data found.")
            }
        }

    }
}


