package eu.playersnba.base

/**
 * Represents different states of UI.
 */
sealed class BaseUiState<out T> {
    /**
     * Indicates loading state.
     */
    data object Loading : BaseUiState<Nothing>()

    /**
     * Represents success state with data.
     */
    data class Success<out T>(val data: T) : BaseUiState<T>()

    /**
     * Represents error state with message.
     */
    data class Error(val message: String) : BaseUiState<Nothing>()

    /**
     * Represents an empty state.
     */
    data object Empty : BaseUiState<Nothing>()
}
