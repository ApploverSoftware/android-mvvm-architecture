package pl.applover.architecture.mvvm.util.architecture.network

import okhttp3.ResponseBody


/**
 * NetworkState with network statuses and other info
 */
@Suppress("DataClassPrivateConstructor")
data class NetworkState private constructor(
        val networkStatus: State,
        val errorBody: ResponseBody? = null,
        val responseCode: Int? = null,
        val throwable: Throwable? = null
) {
    companion object {
        val LOADED = NetworkState(networkStatus = State.SUCCESS)
        val LOADING = NetworkState(networkStatus = State.RUNNING)
        fun error(responseCode: Int, errorBody: ResponseBody? = null) = NetworkState(networkStatus = State.FAILED, errorBody = errorBody, responseCode = responseCode)
        fun throwable(throwable: Throwable) = NetworkState(networkStatus = State.FAILED, throwable = throwable)
    }

    /**
     * Enum with Network statuses
     */
    enum class State {
        RUNNING,
        SUCCESS,
        FAILED
    }
}