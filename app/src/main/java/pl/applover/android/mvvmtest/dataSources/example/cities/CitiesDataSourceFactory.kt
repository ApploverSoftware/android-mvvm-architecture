package pl.applover.android.mvvmtest.dataSources.example.cities

import android.arch.paging.DataSource
import io.reactivex.disposables.CompositeDisposable
import pl.applover.android.mvvmtest.data.example.internet.api_endpoints.ExampleCitiesApiEndpointsInterface
import pl.applover.android.mvvmtest.models.example.ExampleCityModel
import pl.applover.labplus.util.architecture.paging.DataSourceFactoryWithNetworkState

/**
 * Created by Janusz Hain on 2018-06-18.
 */

/**
 * @param compositeDisposable - disposable for all calls in DataSource, if disposed, data source will be stopped
 */
class CitiesDataSourceFactory(private val api: ExampleCitiesApiEndpointsInterface, private val compositeDisposable: CompositeDisposable) : DataSourceFactoryWithNetworkState<String, ExampleCityModel>() {

    override fun create(): DataSource<String, ExampleCityModel> {
        val dataSource = CitiesDataSource(api, compositeDisposable)
        subjectDataSource.onNext(dataSource)
        return dataSource
    }

}