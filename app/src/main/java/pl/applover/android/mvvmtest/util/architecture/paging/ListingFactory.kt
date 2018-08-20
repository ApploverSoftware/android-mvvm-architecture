package pl.applover.android.mvvmtest.util.architecture.paging

import android.arch.lifecycle.LiveData
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import io.reactivex.subjects.BehaviorSubject
import pl.applover.labplus.util.architecture.paging.DataSourceFactoryWithNetworkState

/**
 * Created by Janusz Hain on 2018-07-02.
 */
class ListingFactory<K : Any, V : Any>(private val config: PagedList.Config) {

    private val subjectDataSourceFactory: BehaviorSubject<DataSourceFactoryWithNetworkState<K, V>> = BehaviorSubject.create()
    private var dataSourceFactory: DataSourceFactoryWithNetworkState<K, V>? = null

    /**
     * @return [LiveData] with [PagedList]. Use [LiveData.switchMap()] (from extension) to another liveData to keep single liveData in viewModel without changing ref to this liveData
     */
    fun build(dataSourceFactory: DataSourceFactoryWithNetworkState<K, V>): LiveData<PagedList<V>> {
        this.dataSourceFactory = dataSourceFactory
        subjectDataSourceFactory.onNext(dataSourceFactory)
        return LivePagedListBuilder(dataSourceFactory, config).build()
    }

    /**
     * Always returns ref to the correct subject from new dataSourceFactory
     */
    val networkStateBehaviorSubject = subjectDataSourceFactory.switchMap { it.subjectDataSource.switchMap { it.networkStateSubject() } }!!

    /**
     * Always returns ref to the correct subject from new dataSourceFactory
     */
    val initialStateBehaviourSubject = subjectDataSourceFactory.switchMap { it.subjectDataSource.switchMap { it.initialStateSubject() } }!!


    fun retry() {
        dataSourceFactory?.let {
            it.subjectDataSource.value.retry()
        }
    }

    fun refresh() {
        dataSourceFactory?.let {
            it.subjectDataSource.value.resetData()
        }
    }
}