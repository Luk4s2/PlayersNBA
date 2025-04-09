package eu.playersnba.base

/**
 * A wrapper that encapsulates API results as success or error.
 */
sealed class ResultWrapper<out T> {
    /**
     * Represents a successful API call result.
     */
    data class Success<out T>(val data: T) : ResultWrapper<T>()

    /**
     * Represents a failed API call result with error message.
     */
    data class Error(val message: String) : ResultWrapper<Nothing>()
}
