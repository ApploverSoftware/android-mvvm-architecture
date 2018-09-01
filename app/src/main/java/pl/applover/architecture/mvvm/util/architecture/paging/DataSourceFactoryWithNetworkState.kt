package pl.applover.labplus.util.architecture.paging

import android.arch.paging.DataSource
import io.reactivex.subjects.BehaviorSubject
import pl.applover.architecture.mvvm.util.architecture.paging.NetworkStateDataSource

/**
 * Created by Janusz Hain on 2018-06-18.
 */

/**
 * @param compositeDisposable - disposable for all calls in DataSource, if disposed, data source will be stopped
 */
abstract class DataSourceFactoryWithNetworkState<K, V> : DataSource.Factory<K, V>() {

    /**
     * Keeps reference to the latest subjectDataSource
     * Call subjectDataSource.onNext(dataSource) in on create
     */
    val subjectDataSource: BehaviorSubject<NetworkStateDataSource> = BehaviorSubject.create()
}

