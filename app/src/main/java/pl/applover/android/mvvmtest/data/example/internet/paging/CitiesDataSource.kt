package pl.applover.android.mvvmtest.data.example.internet.paging

import android.arch.paging.ItemKeyedDataSource
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Action
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import pl.applover.android.mvvmtest.data.example.internet.api_endpoints.ExampleCitiesApiEndpointsInterface
import pl.applover.android.mvvmtest.models.example.ExampleCityModel
import pl.applover.android.mvvmtest.util.architecture.network.NetworkState
import pl.applover.android.mvvmtest.util.architecture.retrofit.MappedResponse
import timber.log.Timber

/**
 * Created by Janusz Hain on 2018-06-18.
 */
class CitiesDataSource(private val apiCities: ExampleCitiesApiEndpointsInterface, private val compositeDisposable: CompositeDisposable) : ItemKeyedDataSource<Int, ExampleCityModel>() {

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


    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<ExampleCityModel>) {
        networkStateSubject.onNext(NetworkState.LOADING)
        initialStateSubject.onNext(NetworkState.LOADING)
        compositeDisposable.add(
                initialPagedCitiesFromNetwork().subscribe({ response: MappedResponse<List<ExampleCityModel>> ->
                    response.body()?.let {
                        setRetry(null)
                        callback.onResult(it)
                        networkStateSubject.onNext(NetworkState.LOADED)
                        initialStateSubject.onNext(NetworkState.LOADED)
                    } ?: let {
                        setRetry(Action { loadInitial(params, callback) })
                        networkStateSubject.onNext(NetworkState.error(response.code(), response.errorBody()))
                        initialStateSubject.onNext(NetworkState.error(response.code(), response.errorBody()))
                    }
                }, { throwable: Throwable ->
                    Timber.e(throwable.message)
                    setRetry(Action { loadInitial(params, callback) })
                    networkStateSubject.onNext(NetworkState.throwable(throwable))
                    initialStateSubject.onNext(NetworkState.throwable(throwable))
                })
        )
    }

    private fun initialPagedCitiesFromNetwork() = apiCities.getPagedCitiesList(null).map { MappedResponse(it.raw(), it.body()?.map { ExampleCityModel(it) }, it.errorBody()) }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<ExampleCityModel>) {
        networkStateSubject.onNext(NetworkState.LOADING)
        compositeDisposable.add(
                afterPagedCitiesFromNetwork(params.key).subscribe({ response: MappedResponse<List<ExampleCityModel>> ->
                    response.body()?.let {
                        setRetry(null)
                        callback.onResult(it)
                        networkStateSubject.onNext(NetworkState.LOADED)
                    } ?: let {
                        setRetry(Action { loadAfter(params, callback) })
                        networkStateSubject.onNext(NetworkState.error(response.code(), response.errorBody()))
                    }
                }, { throwable: Throwable ->
                    Timber.e(throwable.message)
                    setRetry(Action { loadAfter(params, callback) })
                    networkStateSubject.onNext(NetworkState.throwable(throwable))
                })
        )
    }

    private fun afterPagedCitiesFromNetwork(lastId: Int) = apiCities.getPagedCitiesList(lastId).map { MappedResponse(it.raw(), it.body()?.map { ExampleCityModel(it) }, it.errorBody()) }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<ExampleCityModel>) {
        //in this example we will do paging list only for "loadAfter"
    }

    override fun getKey(item: ExampleCityModel): Int = item.id

    private fun setRetry(action: Action?) {
        if (action == null) {
            this.retryCompletable = null
        } else {
            this.retryCompletable = Completable.fromAction(action)
        }
    }

}