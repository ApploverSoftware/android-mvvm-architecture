package pl.applover.android.mvvmtest.util.architecture.network

import okhttp3.ResponseBody

/**
 * Created by Ahmed Abd-Elmeged on 2/20/2018.
 */

enum class NetworkStatus {
    RUNNING,
    SUCCESS,
    FAILED
}

@Suppress("DataClassPrivateConstructor")
data class NetworkState private constructor(
        val networkStatus: NetworkStatus,
        val errorBody: ResponseBody? = null,
        val responseCode: Int? = null,
        val throwable: Throwable? = null
) {
    companion object {
        val LOADED = NetworkState(networkStatus = NetworkStatus.SUCCESS)
        val LOADING = NetworkState(networkStatus = NetworkStatus.RUNNING)
        fun error(responseCode: Int, errorBody: ResponseBody? = null) = NetworkState(networkStatus = NetworkStatus.FAILED, errorBody = errorBody, responseCode = responseCode)
        fun throwable(throwable: Throwable) = NetworkState(networkStatus = NetworkStatus.FAILED, throwable = throwable)
    }
}