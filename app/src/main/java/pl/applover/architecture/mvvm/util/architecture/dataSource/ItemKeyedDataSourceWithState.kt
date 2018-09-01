package pl.applover.architecture.mvvm.util.architecture.dataSource

import android.arch.paging.ItemKeyedDataSource
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Action
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import pl.applover.architecture.mvvm.util.architecture.network.NetworkState
import pl.applover.architecture.mvvm.util.architecture.paging.NetworkStateDataSource
import timber.log.Timber

/**
 * Created by Janusz Hain on 2018-06-19.
 */

/**
 * [ItemKeyedDataSource] with [NetworkState], [CompositeDisposable], retry and resetData support.
 * After failed load use [setRetry] passing function to be repeated when calling [retry]
 */
abstract class ItemKeyedDataSourceWithState<Key, Value>(private val compositeDisposable: CompositeDisposable) : ItemKeyedDataSource<Key, Value>(), NetworkStateDataSource {

    val networkStateSubject: BehaviorSubject<NetworkState> = BehaviorSubject.create()
    val initialStateSubject: BehaviorSubject<NetworkState> = BehaviorSubject.create()

    /**
     * Keep Completable reference for the retry event
     */
    private var retryCompletable: Completable? = null

    override fun networkStateSubject(): BehaviorSubject<NetworkState> = networkStateSubject

    override fun initialStateSubject(): BehaviorSubject<NetworkState> = initialStateSubject

    override fun retry() {
        if (retryCompletable != null) {
            compositeDisposable.add(retryCompletable!!
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ }, { throwable -> Timber.e(throwable) }))
        }
    }

    override fun resetData() {
        invalidate()
    }

    protected fun setRetry(action: Action?) {
        if (action == null) {
            this.retryCompletable = null
        } else {
            this.retryCompletable = Completable.fromAction(action)
        }
    }

}