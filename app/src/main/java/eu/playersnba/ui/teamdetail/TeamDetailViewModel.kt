package eu.playersnba.ui.teamdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import eu.playersnba.data.repository.PlayerRepository
import eu.playersnba.base.BaseUiState
import eu.playersnba.base.ResultWrapper
import eu.playersnba.data.model.Team
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel that manages the state and data loading for [TeamDetailScreen].
 * Interacts with TeamRepository to fetch a single team.
 */
@HiltViewModel
class TeamDetailViewModel @Inject constructor(
    private val repository: PlayerRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<BaseUiState<Team>>(BaseUiState.Loading)
    /**
     * Public UI state exposed as immutable [StateFlow] to the view.
     */
    val uiState: StateFlow<BaseUiState<Team>> = _uiState

    /**
     * Triggers a request to fetch team data by [id] and emits loading/success/error state.
     */
    fun loadTeam(id: Int) {
        viewModelScope.launch {
            _uiState.value = BaseUiState.Loading
            when (val result = repository.getTeamDetail(id)) {
                is ResultWrapper.Success -> {
                    _uiState.value = BaseUiState.Success(result.data.team)
                }
                is ResultWrapper.Error -> {
                    _uiState.value = BaseUiState.Error(result.message)
                }
            }
        }
    }
}
