package pl.applover.android.mvvmtest.data.example.repositories

import pl.applover.android.mvvmtest.data.example.database.dao.ExampleCityDao
import pl.applover.android.mvvmtest.data.example.database.models.ExampleCityDbModel
import pl.applover.android.mvvmtest.data.example.internet.api_endpoints.ExampleCitiesApiEndpointsInterface
import pl.applover.android.mvvmtest.models.example.ExampleCityModel
import pl.applover.android.mvvmtest.util.architecture.retrofit.MappedResponse
import javax.inject.Inject

/**
 * Created by Janusz Hain on 2018-06-14.
 */
class ExampleCitiesRepository @Inject constructor(private val apiCities: ExampleCitiesApiEndpointsInterface,
                                                  private val daoCities: ExampleCityDao) {

    fun citiesFromNetwork() =
            apiCities.getCitiesList().map { t -> MappedResponse(t.raw(), t.body()?.map { ExampleCityModel(it) }, t.errorBody()) }


    fun citiesFromDatabase() = daoCities.citiesById().map { it.map { ExampleCityModel(it) } }

    fun pagedCitiesFromDatabase() = daoCities.citiesPagedById().map { ExampleCityModel(it) }

    fun saveAllCitiesToDatabase(cities: ArrayList<ExampleCityModel>) = daoCities.insertOrReplaceAll(cities.map { ExampleCityDbModel(it) })

    fun deleteAllCitiesFromDatabase() = daoCities.deleteAll()
}