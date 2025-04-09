package eu.playersnba.ui.playerlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import eu.playersnba.data.model.Player
import eu.playersnba.data.repository.PlayerRepository
import eu.playersnba.utils.Constants
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class PlayerListViewModel @Inject constructor(
    private val repository: PlayerRepository
) : ViewModel() {

    val players: Flow<PagingData<Player>> = Pager(PagingConfig(pageSize = Constants.PAGE_SIZE)) {
        PlayerPagingSource(repository)
    }.flow.cachedIn(viewModelScope)
}