package eu.playersnba.ui.playerdetail

import app.cash.turbine.test
import eu.playersnba.base.BaseUiState
import eu.playersnba.data.model.Player
import eu.playersnba.data.repository.PlayerRepository
import eu.playersnba.base.ResultWrapper
import eu.playersnba.data.model.SinglePlayerResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.*

@OptIn(ExperimentalCoroutinesApi::class)
class PlayerDetailViewModelTest {

	private lateinit var viewModel: PlayerDetailViewModel
	private val testDispatcher = StandardTestDispatcher()
	private val repository: PlayerRepository = mock()

	private val mockPlayer = Player(
		id = 1,
		firstName = "LeBron",
		lastName = "James",
		position = "F",
		height = "6ft 9in",
		weight = "250 lbs",
		jerseyNumber = "6",
		college = "None",
		country = "USA",
		draftYear = 2003,
		draftRound = 1,
		draftNumber = 1,
		team = null
	)

	@Before
	fun setup() {
		Dispatchers.setMain(testDispatcher)
		viewModel = PlayerDetailViewModel(repository)
	}

	@After
	fun tearDown() {
		Dispatchers.resetMain()
	}

	@Test
	fun `loadPlayer should emit Loading then Success`() = runTest {
		val response = SinglePlayerResponse(player = mockPlayer)
		whenever(repository.getPlayerDetail(1)).thenReturn(ResultWrapper.Success(response))

		viewModel.uiState.test {
			viewModel.loadPlayer(1)
			testDispatcher.scheduler.advanceUntilIdle()

			assert(awaitItem() is BaseUiState.Loading)
			val successState = awaitItem()
			assert(successState is BaseUiState.Success && successState.data == mockPlayer)

			cancelAndIgnoreRemainingEvents()
		}
	}

	@Test
	fun `loadPlayer should emit Error on failure`() = runTest {
		whenever(repository.getPlayerDetail(999)).thenReturn(ResultWrapper.Error("Not found"))

		viewModel.uiState.test {
			viewModel.loadPlayer(999)
			testDispatcher.scheduler.advanceUntilIdle()

			assert(awaitItem() is BaseUiState.Loading)
			val errorState = awaitItem()
			assert(errorState is BaseUiState.Error && errorState.message == "Not found")

			cancelAndIgnoreRemainingEvents()
		}
	}
}
