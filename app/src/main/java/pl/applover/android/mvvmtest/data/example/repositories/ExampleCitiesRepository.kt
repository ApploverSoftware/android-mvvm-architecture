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
            apiCities.getCitiesList().map { t -> MappedResponse(t.raw(), t.body()?.map { exampleCityResponse -> ExampleCityModel(exampleCityResponse) }, t.errorBody()) }


    fun citiesFromDatabase() = daoCities.citiesById()

    fun pagedCitiesFromDatabase() = daoCities.citiesPagedById()

    fun saveAllCitiesToDatabase(cities: ArrayList<ExampleCityDbModel>) = daoCities.insertOrReplaceAll(cities)

    fun deleteAllCitiesFromDatabase() = daoCities.deleteAll()
}