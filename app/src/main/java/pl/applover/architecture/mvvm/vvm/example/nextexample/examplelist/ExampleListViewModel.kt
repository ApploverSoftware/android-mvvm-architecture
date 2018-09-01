package pl.applover.architecture.mvvm.vvm.example.nextexample.examplelist

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableArrayList
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import pl.applover.architecture.mvvm.App
import pl.applover.architecture.mvvm.data.example.repositories.ExampleCitiesRepository
import pl.applover.architecture.mvvm.models.example.ExampleCityModel
import pl.applover.architecture.mvvm.util.architecture.livedata.ObservableListMutableLiveData
import pl.applover.architecture.mvvm.util.architecture.livedata.SingleEvent
import pl.applover.architecture.mvvm.util.architecture.network.NetworkState
import pl.applover.architecture.mvvm.util.architecture.rx.EmptyEvent
import pl.applover.architecture.mvvm.util.other.SchedulerProvider
import retrofit2.Response
import timber.log.Timber

/**
 * Created by Janusz Hain on 2018-06-06.
 */
class ExampleListViewModel(private val router: ExampleListFragmentRouter,
                           private val schedulerProvider: SchedulerProvider,
                           private val citiesRepository: ExampleCitiesRepository) : ViewModel() {

    private val compositeDisposable by lazy { CompositeDisposable() }

    val mldSomeToast = MutableLiveData<SingleEvent<String>>()

    val mldNetworkState = MutableLiveData<NetworkState>()

    /**
     * for this case when every call gives whole list it is better to have simple LiveData and set new list each time
     * I leave this kind of list to show possibility of automatic sending info about changed data
     */
    val mldCitiesLiveData: ObservableListMutableLiveData<ExampleCityModel> = ObservableListMutableLiveData()

    private val cities = ObservableArrayList<ExampleCityModel>()

    private var citiesLoadDisposable: Disposable? = null

    val mldCitiesFromLocal: MutableLiveData<Boolean> = MutableLiveData()

    init {
        mldCitiesLiveData.value = cities
    }

    fun showSomeToast() {
        mldSomeToast.value = SingleEvent("mldSomeToast")
    }

    fun fragmentClicked() {
        router.sender.fragmentClicked.onNext(EmptyEvent())
    }

    fun loadCities() {
        citiesLoadDisposable?.dispose()
        cities.clear()
        mldNetworkState.value = NetworkState.LOADING

        val disposable = citiesRepository.citiesFromNetwork()
                .subscribeOn(schedulerProvider.subscribeOn)
                .observeOn(schedulerProvider.observeOn)
                .subscribe({ response: Response<List<ExampleCityModel>> ->
                    mldCitiesFromLocal.value = false
                    if (response.isSuccessful) {
                        response.body()?.let {
                            cities.addAll(it)
                            mldNetworkState.value = NetworkState.LOADED
                        } ?: run {
                            mldNetworkState.value = NetworkState.error(response.code())
                        }
                    } else {
                        mldNetworkState.value = NetworkState.error(response.code(), response.errorBody())
                    }
                }, { throwable: Throwable ->
                    mldCitiesFromLocal.value = false
                    mldNetworkState.value = NetworkState.throwable(throwable)
                })

        citiesLoadDisposable = disposable
        compositeDisposable.add(disposable)
    }

    fun loadCitiesFromDb() {
        citiesLoadDisposable?.dispose()
        cities.clear()
        mldNetworkState.value = NetworkState.LOADING

        val disposable = citiesRepository.citiesFromDatabase()
                .subscribeOn(schedulerProvider.subscribeOn)
                .observeOn(schedulerProvider.observeOn)
                .subscribe({
                    cities.addAll(it)
                    mldNetworkState.value = NetworkState.LOADED
                    mldCitiesFromLocal.value = true
                }, {
                    mldNetworkState.value = NetworkState.throwable(it)
                    Timber.e(it)
                    mldCitiesFromLocal.value = true
                })

        citiesLoadDisposable = disposable
        compositeDisposable.add(disposable)
    }

    fun saveCitiesToDb() {
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

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
        watchForLeaks()
    }


    private fun watchForLeaks() {
        App.refWatcher.watch(mldSomeToast)
        App.refWatcher.watch(compositeDisposable)
        App.refWatcher.watch(this)
        //we don't watch for router leak, as router has to stay alive, as it is router for activity scope
    }
}