package pl.applover.android.mvvmtest.data.example.repositories

import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import pl.applover.android.mvvmtest.data.example.database.dao.ExampleCityDao
import pl.applover.android.mvvmtest.data.example.database.models.ExampleCityDbModel
import pl.applover.android.mvvmtest.data.example.internet.api_endpoints.ExampleCitiesApiEndpointsInterface
import pl.applover.android.mvvmtest.data.example.internet.paging.CitiesDataSourceFactory
import pl.applover.android.mvvmtest.models.example.ExampleCityModel
import pl.applover.android.mvvmtest.util.architecture.retrofit.MappedResponse
import java.util.*
import javax.inject.Inject

/**
 * Created by Janusz Hain on 2018-06-14.
 */
class ExampleCitiesRepository @Inject constructor(private val apiCities: ExampleCitiesApiEndpointsInterface,
                                                  private val daoCities: ExampleCityDao) {

    fun citiesDataSourceFactory(compositeDisposable: CompositeDisposable): CitiesDataSourceFactory = CitiesDataSourceFactory(apiCities, compositeDisposable)

    fun citiesNetworkStateBehaviorSubject(citiesDataSourceFactory: CitiesDataSourceFactory) = citiesDataSourceFactory.subjectCitiesDataSource.switchMap { it.networkStateSubject }

    fun citiesInitialStateBehaviourSubject(citiesDataSourceFactory: CitiesDataSourceFactory) = citiesDataSourceFactory.subjectCitiesDataSource.switchMap { it.initialStateSubject }

    fun citiesFromNetwork() =
            apiCities.getCitiesList().map { MappedResponse(it.raw(), it.body()?.map { ExampleCityModel(it) }, it.errorBody()) }

    fun pagedCitiesFromDatabase() = daoCities.citiesPagedById().map { ExampleCityModel(it) }

    fun citiesFromDatabase() = daoCities.citiesById().map { it.map { ExampleCityModel(it) } }

    fun saveAllCitiesToDatabase(cities: ArrayList<ExampleCityModel>) = Observable.fromCallable { daoCities.insertOrReplaceAll(cities.map { ExampleCityDbModel(it) }) }

    fun deleteAllCitiesFromDatabase() = Observable.fromCallable { daoCities.deleteAll() }


}