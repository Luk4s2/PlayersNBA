package eu.playersnba.ui.teamdetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import eu.playersnba.base.BaseUiState
import eu.playersnba.ui.components.AttributeRow
import eu.playersnba.ui.components.ErrorWithRetry
import eu.playersnba.utils.Constants
import eu.playersnba.utils.Dimens

@Composable
fun TeamDetailScreen(
    teamId: Int,
    navController: NavController,
    viewModel: TeamDetailViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadTeam(teamId)
    }

    Scaffold(
        topBar = {

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
                            modifier = Modifier.constrainAs(back) {
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

                        Text(
                            text = Constants.TEAM_DETAIL,
                            style = MaterialTheme.typography.headlineLarge,
                            color = Color.White,
                            modifier = Modifier.constrainAs(title) {
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                                top.linkTo(parent.top)
                                bottom.linkTo(parent.bottom)
                            }
                        )
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
                    val team = (state as BaseUiState.Success).data
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(Dimens.ScreenPadding),
                        verticalArrangement = Arrangement.Top
                    ) {
                        AttributeRow("Full Name", team.fullName)
                        AttributeRow("City", team.city)
                        AttributeRow("Name", team.name)
                        AttributeRow("Abbreviation", team.abbreviation)
                        AttributeRow("Conference", team.conference)
                        AttributeRow("Division", team.division)
                    }
                }

                is BaseUiState.Error -> ErrorWithRetry(
                    message = (state as BaseUiState.Error).message,
                    onRetry = { viewModel.loadTeam(teamId) }
                )

                is BaseUiState.Empty -> Text(
                    text = Constants.NO_DATA_FOUND,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}

