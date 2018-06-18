package pl.applover.android.mvvmtest.data.example.internet.paging

import android.arch.paging.ItemKeyedDataSource
import io.reactivex.Single
import pl.applover.android.mvvmtest.data.example.internet.response.ExampleCityResponse
import pl.applover.android.mvvmtest.models.example.ExampleCityModel
import retrofit2.Response

/**
 * Created by Janusz Hain on 2018-06-18.
 */
class CitiesDataSource(private val initialCitiesLoad: Single<Response<List<ExampleCityResponse>>>) : ItemKeyedDataSource<Int, ExampleCityModel>() {
    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<ExampleCityModel>) {
        initialCitiesLoad.subscribe({ response: Response<List<ExampleCityResponse>> ->
            response.body()?.let {
                val cities = it.map { exampleCityResponse -> ExampleCityModel(exampleCityResponse) }
                callback.onResult(cities)
            }
        }, { throwable: Throwable ->

        })
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<ExampleCityModel>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<ExampleCityModel>) {
        //in this example we will do paging list only for "loadAfter"
    }

    override fun getKey(item: ExampleCityModel): Int = item.id

}