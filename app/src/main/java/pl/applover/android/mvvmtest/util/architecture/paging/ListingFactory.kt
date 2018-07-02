package pl.applover.android.mvvmtest.util.architecture.paging

import android.arch.lifecycle.LiveData
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import io.reactivex.subjects.BehaviorSubject

/**
 * Created by Janusz Hain on 2018-07-02.
 */
class ListingFactoryItemKeyed<K : Any, V : Any>(private var dataSourceFactory: ItemKeyedDataSourceFactory<K, V>, private val config: PagedList.Config) {

    private val subjectCitiesDataSourceFactory: BehaviorSubject<ItemKeyedDataSourceFactory<K, V>> = BehaviorSubject.create()

    fun build(): LiveData<PagedList<V>> {
        subjectCitiesDataSourceFactory.onNext(dataSourceFactory)
        return LivePagedListBuilder(dataSourceFactory, config).build()
    }

    val networkStateBehaviorSubject = subjectCitiesDataSourceFactory.switchMap { it.subjectCitiesDataSource.switchMap { it.networkStateSubject } }

    val initialStateBehaviourSubject = subjectCitiesDataSourceFactory.switchMap { it.subjectCitiesDataSource.switchMap { it.initialStateSubject } }

    fun swapDataSource(dataSourceFactory: ItemKeyedDataSourceFactory<K, V>): LiveData<PagedList<V>> {
        this.dataSourceFactory = dataSourceFactory
        return build()
    }

    fun retry() {
        dataSourceFactory.subjectCitiesDataSource.value.retry()
    }

    fun refresh() {
        dataSourceFactory.subjectCitiesDataSource.value.resetData()
    }
}