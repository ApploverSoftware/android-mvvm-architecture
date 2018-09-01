package pl.applover.architecture.mvvm.vvm.example.nextexample.examplepagedList

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import io.reactivex.disposables.CompositeDisposable
import pl.applover.architecture.mvvm.App
import pl.applover.architecture.mvvm.data.example.repositories.ExampleCitiesRepository
import pl.applover.architecture.mvvm.models.example.ExampleCityModel
import pl.applover.architecture.mvvm.util.architecture.livedata.switchMap
import pl.applover.architecture.mvvm.util.architecture.network.NetworkState
import pl.applover.architecture.mvvm.util.architecture.paging.ListingFactory
import pl.applover.architecture.mvvm.util.other.SchedulerProvider
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
    val mldCities: MutableLiveData<Boolean> = MutableLiveData()

    private val myPagingConfig = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(5)
            .setInitialLoadSizeHint(5)
            .setPrefetchDistance(2)
            .build()

    private var listingFactoryOnline: ListingFactory<String, ExampleCityModel> = ListingFactory(myPagingConfig)

    val ldCitiesPagedList: MutableLiveData<PagedList<ExampleCityModel>> = MutableLiveData()


    init {

        compositeDisposable.add(listingFactoryOnline.initialStateBehaviourSubject
                .observeOn(schedulerProvider.observeOn).subscribe {
                    mldInitialNetworkState.value = it
                })
        compositeDisposable.add(listingFactoryOnline.networkStateBehaviorSubject
                .observeOn(schedulerProvider.observeOn).subscribe {
                    mldNetworkState.value = it
                })
    }

    fun retry() {
        if (mldCities.value == false)
            listingFactoryOnline.retry()
    }

    fun refresh() {
        if (mldCities.value == false)
            listingFactoryOnline.refresh()
    }

    fun loadCitiesFromDb(lifecycleOwner: LifecycleOwner) {
        mldCities.value = true
        val liveData = LivePagedListBuilder(citiesRepository.pagedCitiesFromDatabase(), myPagingConfig).build()
        ldCitiesPagedList.switchMap(liveData, lifecycleOwner)
    }

    fun loadCitiesFromOnlineSource(lifecycleOwner: LifecycleOwner) {
        mldCities.value = false
        listingFactoryOnline.build(citiesRepository.citiesDataSourceFactory(compositeDisposable)).let { liveData ->
            ldCitiesPagedList.switchMap(liveData, lifecycleOwner)
        }
    }

    fun saveCitiesToDb() {
        ldCitiesPagedList.value?.let { cities ->
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