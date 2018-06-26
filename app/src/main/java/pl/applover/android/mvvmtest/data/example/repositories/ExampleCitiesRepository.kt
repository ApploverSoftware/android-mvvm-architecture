package pl.applover.android.mvvmtest.data.example.repositories

import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import pl.applover.android.mvvmtest.data.example.database.dao.ExampleCityDao
import pl.applover.android.mvvmtest.data.example.database.models.ExampleCityDbModel
import pl.applover.android.mvvmtest.data.example.internet.api_endpoints.ExampleCitiesApiEndpointsInterface
import pl.applover.android.mvvmtest.dataSources.example.cities.CitiesDataSourceFactory
import pl.applover.android.mvvmtest.models.example.ExampleCityModel
import pl.applover.android.mvvmtest.util.architecture.retrofit.MappedResponse
import javax.inject.Inject

/**
 * Created by Janusz Hain on 2018-06-14.
 */

/**
 * Repository that exposes DataSources and Observables for loading data from local/network sources
 * Repository also exposes Subjects that inform about state of the calls
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

    fun saveAllCitiesToDatabase(cities: Collection<ExampleCityModel>) = Single.fromCallable { daoCities.insertOrReplaceAll(cities.map { ExampleCityDbModel(it) }) }

    fun deleteAllCitiesFromDatabase() = Single.fromCallable { daoCities.deleteAll() }


}