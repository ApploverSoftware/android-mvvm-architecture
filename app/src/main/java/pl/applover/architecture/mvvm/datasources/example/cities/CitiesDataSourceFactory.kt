package pl.applover.architecture.mvvm.datasources.example.cities

import android.arch.paging.DataSource
import io.reactivex.disposables.CompositeDisposable
import pl.applover.architecture.mvvm.data.example.internet.apiendpoints.ExampleCitiesApiEndpointsInterface
import pl.applover.architecture.mvvm.models.example.ExampleCityModel
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