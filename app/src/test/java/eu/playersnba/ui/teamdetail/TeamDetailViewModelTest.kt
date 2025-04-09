package eu.playersnba.ui.teamdetail

import app.cash.turbine.test
import eu.playersnba.base.BaseUiState
import eu.playersnba.base.ResultWrapper
import eu.playersnba.data.model.SinglePlayerResponse
import eu.playersnba.data.model.SingleTeamResponse
import eu.playersnba.data.model.Team
import eu.playersnba.data.repository.PlayerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.*

@OptIn(ExperimentalCoroutinesApi::class)
class TeamDetailViewModelTest {

	private lateinit var viewModel: TeamDetailViewModel
	private val testDispatcher = StandardTestDispatcher()
	private val repository: PlayerRepository = mock()

	private val mockTeam = Team(
		id = 1,
		abbreviation = "LAL",
		city = "Los Angeles",
		conference = "West",
		division = "Pacific",
		fullName = "Los Angeles Lakers",
		name = "Lakers"
	)

	@Before
	fun setup() {
		Dispatchers.setMain(testDispatcher)
		viewModel = TeamDetailViewModel(repository)
	}

	@After
	fun tearDown() {
		Dispatchers.resetMain()
	}

	@Test
	fun `loadTeam should emit Loading then Success`() = runTest {
		val response = SingleTeamResponse(team = mockTeam)
		whenever(repository.getTeamDetail(1)).thenReturn(ResultWrapper.Success(response))

		viewModel.uiState.test {
			viewModel.loadTeam(1)
			testDispatcher.scheduler.advanceUntilIdle()

			assert(awaitItem() is BaseUiState.Loading)
			val successState = awaitItem()
			assert(successState is BaseUiState.Success && successState.data == mockTeam)

			cancelAndIgnoreRemainingEvents()
		}
	}

	@Test
	fun `loadTeam should emit Error on failure`() = runTest {
		whenever(repository.getTeamDetail(999)).thenReturn(ResultWrapper.Error("Team not found"))

		viewModel.uiState.test {
			viewModel.loadTeam(999)
			testDispatcher.scheduler.advanceUntilIdle()

			assert(awaitItem() is BaseUiState.Loading)
			val errorState = awaitItem()
			assert(errorState is BaseUiState.Error && errorState.message == "Team not found")

			cancelAndIgnoreRemainingEvents()
		}
	}
}
