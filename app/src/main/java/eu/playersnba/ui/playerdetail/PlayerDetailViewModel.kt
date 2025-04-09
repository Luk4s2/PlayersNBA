package eu.playersnba.ui.playerdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import eu.playersnba.data.model.Player
import eu.playersnba.data.repository.PlayerRepository
import eu.playersnba.base.BaseUiState
import eu.playersnba.base.ResultWrapper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel responsible for retrieving and exposing player detail data
 * to the [PlayerDetailScreen]. Uses [PlayerRepository] to fetch data.
 */
@HiltViewModel
class PlayerDetailViewModel @Inject constructor(
    private val repository: PlayerRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<BaseUiState<Player>>(BaseUiState.Loading)

    /**
     * Exposes a [StateFlow] representing the current UI state.
     */
    val uiState: StateFlow<BaseUiState<Player>> = _uiState

    /**
     * Loads a single player's detail by [id] from the repository.
     * Updates [uiState] with Loading, Success, or [Error].
     *
     * @param id The player ID to fetch details for.
     */
    fun loadPlayer(id: Int) {
        viewModelScope.launch {
            _uiState.value = BaseUiState.Loading

            when (val result = repository.getPlayerDetail(id)) {
                is ResultWrapper.Success -> {
                    _uiState.value = BaseUiState.Success(result.data.player)
                }
                is ResultWrapper.Error -> {
                    _uiState.value = BaseUiState.Error(result.message)
                }
            }
        }
    }
}
