package pl.applover.android.mvvmtest.data.example.internet.paging

import android.arch.paging.DataSource
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import pl.applover.android.mvvmtest.data.example.internet.api_endpoints.ExampleCitiesApiEndpointsInterface
import pl.applover.android.mvvmtest.models.example.ExampleCityModel
import pl.applover.android.mvvmtest.util.architecture.retrofit.MappedResponse

/**
 * Created by Janusz Hain on 2018-06-18.
 */

/**
 * @param compositeDisposable - disposable for all calls in DataSource, if disposed, data source will be stopped
 */
class CitiesDataSourceFactory(private val apiCities: ExampleCitiesApiEndpointsInterface, private val compositeDisposable: CompositeDisposable) : DataSource.Factory<Int, ExampleCityModel>() {

    /**
     * Keeps reference to the latest subjectCitiesDataSource
     */
    val subjectCitiesDataSource: BehaviorSubject<CitiesDataSource> = BehaviorSubject.create()

    override fun create(): DataSource<Int, ExampleCityModel> {
        val citiesDataSource = CitiesDataSource(apiCities, compositeDisposable)
        subjectCitiesDataSource.onNext(citiesDataSource)
        return citiesDataSource
    }

}