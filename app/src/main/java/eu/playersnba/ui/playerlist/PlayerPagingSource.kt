package eu.playersnba.ui.playerlist

import androidx.paging.PagingSource
import androidx.paging.PagingState
import eu.playersnba.base.ResultWrapper
import eu.playersnba.data.model.Player
import eu.playersnba.data.repository.PlayerRepository
import eu.playersnba.utils.Constants

/**
 * A [PagingSource] implementation that loads pages of [Player] data
 * from the [PlayerRepository]. Used for infinite scrolling of player lists.
 */
class PlayerPagingSource(
    private val repository: PlayerRepository
) : PagingSource<Int, Player>() {

    /**
     * Loads a page of [Player] data from the repository.
     *
     * @param params The parameters for loading the data, including the key and load size.
     * @return A LoadResult containing either the loaded data or an error.
     */
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Player> {
        val page = params.key ?: 1
        val perPage = params.loadSize.takeIf { it > 0 } ?: Constants.PAGE_SIZE

        return when (val result = repository.getPlayers(page, perPage)) {
            is ResultWrapper.Success -> {
                val players = result.data.players
                LoadResult.Page(
                    data = players,
                    prevKey = if (page == 1) null else page - 1,
                    nextKey = if (players.isEmpty()) null else page + 1
                )
            }
            is ResultWrapper.Error -> {
                LoadResult.Error(Throwable(result.message))
            }
        }
    }

    /**
     * Determines the key for the page to be refreshed when invalidation occurs.
     */
    override fun getRefreshKey(state: PagingState<Int, Player>): Int? {
        return state.anchorPosition?.let { anchor ->
            state.closestPageToPosition(anchor)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchor)?.nextKey?.minus(1)
        }
    }
}
