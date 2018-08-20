package pl.applover.android.mvvmtest.util.architecture.paging

import io.reactivex.subjects.BehaviorSubject
import pl.applover.android.mvvmtest.util.architecture.network.NetworkState

interface NetworkStateDataSource {
    fun networkStateSubject(): BehaviorSubject<NetworkState>
    fun initialStateSubject(): BehaviorSubject<NetworkState>
    fun retry()
    fun resetData()
}