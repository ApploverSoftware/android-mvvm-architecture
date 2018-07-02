package pl.applover.android.mvvmtest.repositories.example

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.paging.ItemKeyedDataSource
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import com.nhaarman.mockitokotlin2.*
import io.reactivex.Single
import io.reactivex.subjects.BehaviorSubject
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.AdditionalAnswers.answer
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.invocation.InvocationOnMock
import org.mockito.stubbing.Answer
import pl.applover.android.mvvmtest.data.example.database.dao.ExampleCityDao
import pl.applover.android.mvvmtest.data.example.internet.api_endpoints.ExampleCitiesApiEndpointsInterface
import pl.applover.android.mvvmtest.data.example.internet.response.ExampleCityResponse
import pl.applover.android.mvvmtest.data.example.repositories.ExampleCitiesRepository
import pl.applover.android.mvvmtest.dataSources.example.cities.CitiesDataSource
import pl.applover.android.mvvmtest.dataSources.example.cities.CitiesDataSourceFactory
import pl.applover.android.mvvmtest.modelFactories.example.ExampleCityResponseTestFactory
import pl.applover.android.mvvmtest.models.example.ExampleCityModel
import pl.applover.android.mvvmtest.util.architecture.network.NetworkState
import pl.applover.android.mvvmtest.util.extensions.removeLastItems
import retrofit2.Response

/**
 * Created by Janusz Hain on 2018-06-29.
 */
class ExampleCitiesRepositoryUnitTest {

    /**
     * A JUnit Test Rule that swaps the background executor used by the Architecture Components with a
     * different one which executes each task synchronously.
     * <p>
     * You can use this rule for your host side tests that use Architecture Components.
     */
    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    private lateinit var mockedRepository: ExampleCitiesRepository

    private val exampleCityResponseTestFactory = ExampleCityResponseTestFactory()

    //City list for testing
    private val cityCountPerPage = 10
    private val maxCities = 25
    private val citiesForPaging = ArrayList(exampleCityResponseTestFactory.createList(maxCities))
    private val lastCitiesFromPage = ArrayList<ExampleCityResponse>()

    private var lastItemId: String = "null"

    @Mock
    private lateinit var dao: ExampleCityDao

    @Mock
    private lateinit var exampleCitiesApiEndpointsInterface: ExampleCitiesApiEndpointsInterface

    private val subjectCitiesDataSource: BehaviorSubject<CitiesDataSource> = BehaviorSubject.create()

    private val networkStateSubject: BehaviorSubject<NetworkState> = BehaviorSubject.create()
    private val initialStateSubject: BehaviorSubject<NetworkState> = BehaviorSubject.create()

    private lateinit var dataSource: CitiesDataSource

    private lateinit var dataSourceFactory: CitiesDataSourceFactory

    private val myPagingConfig = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(5)
            .setInitialLoadSizeHint(5)
            .setPrefetchDistance(2)
            .build()

    @Captor
    private lateinit var captorLastKey: ArgumentCaptor<ItemKeyedDataSource.LoadParams<String>>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

    }

    private fun getCitiesListApi() =
            Single.just(Response.success(
                    exampleCityResponseTestFactory.createList(25)))

    private fun getCitiesPagedCities(): Single<Response<List<ExampleCityResponse>>> {

        return Single.just(Response.success(getAndRepmoveCities() as List<ExampleCityResponse>))
    }

    private fun getAndRepmoveCities(): ArrayList<ExampleCityResponse> {
        lastItemId = if (lastCitiesFromPage.size > 0)
            lastCitiesFromPage[lastCitiesFromPage.lastIndex].id
        else "null"

        lastCitiesFromPage.clear()
        lastCitiesFromPage.addAll(citiesForPaging.removeLastItems(cityCountPerPage))

        return lastCitiesFromPage
    }

    @Test
    fun something() {
    }

    private fun loadAroundLast(pagedList: PagedList<ExampleCityModel>) {
        pagedList.loadAround(pagedList.lastIndex)
    }

}