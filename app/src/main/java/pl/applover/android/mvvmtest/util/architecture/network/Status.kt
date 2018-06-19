package pl.applover.android.mvvmtest.util.architecture.network

import okhttp3.ResponseBody

/**
 * Created by Ahmed Abd-Elmeged on 2/20/2018.
 */

enum class Status {
    RUNNING,
    SUCCESS,
    FAILED
}

@Suppress("DataClassPrivateConstructor")
data class NetworkState private constructor(
        val status: Status,
        val errorBody: ResponseBody? = null,
        val responseCode: Int? = null,
        val throwable: Throwable? = null
) {
    companion object {
        val LOADED = NetworkState(status = Status.SUCCESS)
        val LOADING = NetworkState(status = Status.RUNNING)
        fun error(responseCode: Int, errorBody: ResponseBody? = null) = NetworkState(status = Status.FAILED, errorBody = errorBody, responseCode = responseCode)
        fun throwable(throwable: Throwable) = NetworkState(status = Status.FAILED, throwable = throwable)
    }
}