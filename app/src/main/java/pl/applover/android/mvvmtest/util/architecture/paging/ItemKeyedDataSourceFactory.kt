package pl.applover.android.mvvmtest.util.architecture.paging

import android.arch.paging.DataSource
import io.reactivex.subjects.BehaviorSubject
import pl.applover.android.mvvmtest.util.architecture.dataSource.ItemKeyedDataSourceWithState

/**
 * Created by Janusz Hain on 2018-06-18.
 */

/**
 * @param compositeDisposable - disposable for all calls in DataSource, if disposed, data source will be stopped
 */
abstract class ItemKeyedDataSourceFactory<K, V> : DataSource.Factory<K, V>() {

    /**
     * Keeps reference to the latest subjectCitiesDataSource
     * Call subjectCitiesDataSource.onNext(dataSource) in on create
     */
    val subjectCitiesDataSource: BehaviorSubject<ItemKeyedDataSourceWithState<K, V>> = BehaviorSubject.create()
}