package pl.applover.architecture.mvvm.data.example.repositories

import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import pl.applover.architecture.mvvm.data.example.database.dao.ExampleCityDao
import pl.applover.architecture.mvvm.data.example.database.models.ExampleCityDbModel
import pl.applover.architecture.mvvm.data.example.internet.apiendpoints.ExampleCitiesApiEndpointsInterface
import pl.applover.architecture.mvvm.datasources.example.cities.CitiesDataSourceFactory
import pl.applover.architecture.mvvm.models.example.ExampleCityModel
import pl.applover.architecture.mvvm.util.architecture.retrofit.mapResponseList
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

    fun citiesDataSourceFactory(compositeDisposable: CompositeDisposable) = CitiesDataSourceFactory(apiCities, compositeDisposable)

    fun citiesFromNetwork() =
            apiCities.getCitiesList().mapResponseList(mapper = { ExampleCityModel(it) })

    fun pagedCitiesFromDatabase() = daoCities.citiesPagedById().map { ExampleCityModel(it) }

    fun citiesFromDatabase() = daoCities.citiesById().map { it.map { ExampleCityModel(it) } }!!

    fun saveAllCitiesToDatabase(cities: Collection<ExampleCityModel>) = Single.fromCallable { daoCities.insertOrReplaceAll(cities.map { ExampleCityDbModel(it) }) }!!

    fun deleteAllCitiesFromDatabase() = Single.fromCallable { daoCities.deleteAll() }!!

}