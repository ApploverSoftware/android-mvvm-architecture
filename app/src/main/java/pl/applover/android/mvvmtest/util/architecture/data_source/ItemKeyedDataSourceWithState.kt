package pl.applover.android.mvvmtest.util.architecture.data_source

import android.arch.paging.ItemKeyedDataSource
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Action
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import pl.applover.android.mvvmtest.util.architecture.network.NetworkState
import timber.log.Timber

/**
 * Created by Janusz Hain on 2018-06-19.
 */
abstract class ItemKeyedDataSourceWithState<Key, Value>(private val compositeDisposable: CompositeDisposable) : ItemKeyedDataSource<Key, Value>() {

    val networkStateSubject: BehaviorSubject<NetworkState> = BehaviorSubject.create()
    val initialStateSubject: BehaviorSubject<NetworkState> = BehaviorSubject.create()

    /**
     * Keep Completable reference for the retry event
     */
    private var retryCompletable: Completable? = null


    fun retry() {
        if (retryCompletable != null) {
            compositeDisposable.add(retryCompletable!!
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ }, { throwable -> Timber.e(throwable.message) }))
        }
    }

    fun resetData() {
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