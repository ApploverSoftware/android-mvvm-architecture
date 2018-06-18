package pl.applover.android.mvvmtest.data.example.repositories

import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import pl.applover.android.mvvmtest.data.example.database.dao.ExampleCityDao
import pl.applover.android.mvvmtest.data.example.database.models.ExampleCityDbModel
import pl.applover.android.mvvmtest.data.example.internet.api_endpoints.ExampleCitiesApiEndpointsInterface
import pl.applover.android.mvvmtest.data.example.internet.paging.CitiesDataSourceFactory
import pl.applover.android.mvvmtest.models.example.ExampleCityModel
import pl.applover.android.mvvmtest.util.architecture.retrofit.MappedResponse
import javax.inject.Inject

/**
 * Created by Janusz Hain on 2018-06-14.
 */
class ExampleCitiesRepository @Inject constructor(private val apiCities: ExampleCitiesApiEndpointsInterface,
                                                  private val daoCities: ExampleCityDao) {

    val citiesDataSourceFactory: CitiesDataSourceFactory = CitiesDataSourceFactory(apiCities)

    fun citiesFromNetwork() =
            apiCities.getCitiesList().map { MappedResponse(it.raw(), it.body()?.map { ExampleCityModel(it) }, it.errorBody()) }

    fun citiesNetworkStateBehaviorSubject() = citiesDataSourceFactory.subjectCitiesDataSource.switchMap { it.networkStateSubject }

    fun pagedCitiesFromDatabase() = daoCities.citiesPagedById().map { ExampleCityModel(it) }

    fun citiesFromDatabase() = daoCities.citiesById().map { it.map { ExampleCityModel(it) } }

    fun saveAllCitiesToDatabase(cities: ArrayList<ExampleCityModel>) {
        launch(UI) {
            val query = async(CommonPool) {
                daoCities.insertOrReplaceAll(cities.map { ExampleCityDbModel(it) })
            }
            query.await()
        }

    }

    fun deleteAllCitiesFromDatabase() {
        launch(UI) {
            val query = async(CommonPool) {
                daoCities.deleteAll()
            }
            query.await()
        }
    }


}