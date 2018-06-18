package pl.applover.android.mvvmtest.data.example.internet.paging

import android.arch.paging.ItemKeyedDataSource
import io.reactivex.subjects.BehaviorSubject
import pl.applover.android.mvvmtest.data.example.internet.api_endpoints.ExampleCitiesApiEndpointsInterface
import pl.applover.android.mvvmtest.models.example.ExampleCityModel
import pl.applover.android.mvvmtest.util.architecture.network.NetworkState
import pl.applover.android.mvvmtest.util.architecture.retrofit.MappedResponse

/**
 * Created by Janusz Hain on 2018-06-18.
 */
class CitiesDataSource(private val apiCities: ExampleCitiesApiEndpointsInterface) : ItemKeyedDataSource<Int, ExampleCityModel>() {

    val networkStateSubject: BehaviorSubject<NetworkState> = BehaviorSubject.create()

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<ExampleCityModel>) {
        networkStateSubject.onNext(NetworkState.LOADING)
        initialPagedCitiesFromNetwork().subscribe({ response: MappedResponse<List<ExampleCityModel>> ->
            response.body()?.let {
                callback.onResult(it)
                networkStateSubject.onNext(NetworkState.LOADED)
            }
        }, { throwable: Throwable ->
            networkStateSubject.onNext(NetworkState.error())
        })
    }

    private fun initialPagedCitiesFromNetwork() = apiCities.getPagedCitiesList(null).map { MappedResponse(it.raw(), it.body()?.map { ExampleCityModel(it) }, it.errorBody()) }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<ExampleCityModel>) {
        networkStateSubject.onNext(NetworkState.LOADING)
        afterPagedCitiesFromNetwork(params.key).subscribe({ response: MappedResponse<List<ExampleCityModel>> ->
            response.body()?.let {
                callback.onResult(it)
                networkStateSubject.onNext(NetworkState.LOADED)
            }
        }, { throwable: Throwable ->
            networkStateSubject.onNext(NetworkState.error())
        })
    }

    private fun afterPagedCitiesFromNetwork(lastId: Int) = apiCities.getPagedCitiesList(lastId).map { MappedResponse(it.raw(), it.body()?.map { ExampleCityModel(it) }, it.errorBody()) }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<ExampleCityModel>) {
        //in this example we will do paging list only for "loadAfter"
    }

    override fun getKey(item: ExampleCityModel): Int = item.id

}