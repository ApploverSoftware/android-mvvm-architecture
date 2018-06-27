package pl.applover.android.mvvmtest.vvm.example.nextExample.examplePagedList

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import io.reactivex.disposables.CompositeDisposable
import pl.applover.android.mvvmtest.App
import pl.applover.android.mvvmtest.data.example.repositories.ExampleCitiesRepository
import pl.applover.android.mvvmtest.util.architecture.network.NetworkState
import pl.applover.android.mvvmtest.util.other.SchedulerProvider
import timber.log.Timber

/**
 * Created by Janusz Hain on 2018-06-06.
 */
class ExamplePagedListViewModel(private val router: ExamplePagedListFragmentRouter,
                                private val schedulerProvider: SchedulerProvider,
                                private val citiesRepository: ExampleCitiesRepository) : ViewModel() {

    private val compositeDisposable by lazy { CompositeDisposable() }

    val mldNetworkState = MutableLiveData<NetworkState>()
    val mldInitialNetworkState = MutableLiveData<NetworkState>()
    val mldCitiesFromLocal: MutableLiveData<Boolean> = MutableLiveData()

    private val myPagingConfig = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(5)
            .setInitialLoadSizeHint(5)
            .setPrefetchDistance(2)
            .build()

    private val dataSourceFactory = citiesRepository.citiesDataSourceFactory(compositeDisposable)
    private val databaseDataSourceFactory = citiesRepository.pagedCitiesFromDatabase()

    var pagedList = LivePagedListBuilder(dataSourceFactory, myPagingConfig).build()

    init {

        compositeDisposable.add(citiesRepository.citiesInitialStateBehaviourSubject(dataSourceFactory)
                .observeOn(schedulerProvider.observeOn).subscribe {
                    mldInitialNetworkState.value = it
                })
        compositeDisposable.add(citiesRepository.citiesNetworkStateBehaviorSubject(dataSourceFactory)
                .observeOn(schedulerProvider.observeOn).subscribe {
                    mldNetworkState.value = it
                })
    }

    fun retry() {
        if (mldCitiesFromLocal.value == false)
            dataSourceFactory.subjectCitiesDataSource.value.retry()
    }

    fun refresh() {
        if (mldCitiesFromLocal.value == false)
            dataSourceFactory.subjectCitiesDataSource.value.resetData()
    }

    fun loadCitiesFromDb(lifecycleOwner: LifecycleOwner) {
        pagedList.removeObservers(lifecycleOwner)
        mldCitiesFromLocal.value = true
        pagedList = LivePagedListBuilder(databaseDataSourceFactory, myPagingConfig).build()
    }

    fun loadCitiesFromOnlineSource(lifecycleOwner: LifecycleOwner) {
        pagedList.removeObservers(lifecycleOwner)
        mldCitiesFromLocal.value = false
        pagedList = LivePagedListBuilder(dataSourceFactory, myPagingConfig).build()

    }

    fun saveCitiesToDb() {
        pagedList.value?.let { cities ->
            citiesRepository.deleteAllCitiesFromDatabase()
                    .subscribeOn(schedulerProvider.subscribeOn)
                    .subscribe({
                        citiesRepository.saveAllCitiesToDatabase(cities)
                                .subscribeOn(schedulerProvider.subscribeOn)
                                .observeOn(schedulerProvider.observeOn)
                                .subscribe()
                    }, {
                        Timber.e(it)
                    })
        }
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
        watchForLeaks()
    }


    private fun watchForLeaks() {
        App.refWatcher.watch(compositeDisposable)
        App.refWatcher.watch(this)
        //we don't watch for router leak, as router has to stay alive, as it is router for activity scope
    }
}