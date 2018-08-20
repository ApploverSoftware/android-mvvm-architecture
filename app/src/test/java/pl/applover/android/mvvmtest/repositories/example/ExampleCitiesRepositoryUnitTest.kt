package pl.applover.android.mvvmtest.repositories.example

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.paging.PagedList
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.spy
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import junit.framework.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import pl.applover.android.mvvmtest.data.example.internet.api_endpoints.ExampleCitiesApiEndpointsInterface
import pl.applover.android.mvvmtest.data.example.internet.response.ExampleCityResponse
import pl.applover.android.mvvmtest.data.example.repositories.ExampleCitiesRepository
import pl.applover.android.mvvmtest.modelFactories.example.ExampleCityResponseTestFactory
import pl.applover.android.mvvmtest.models.example.ExampleCityModel
import pl.applover.android.mvvmtest.util.architecture.network.NetworkState
import pl.applover.android.mvvmtest.util.architecture.paging.ListingFactory
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

    private lateinit var repository: ExampleCitiesRepository

    @Mock
    private lateinit var exampleCitiesApiEndpointsInterface: ExampleCitiesApiEndpointsInterface

    //City list for testing

    private val exampleCityResponseTestFactory = ExampleCityResponseTestFactory()

    private val cityCountPerPage = 10
    private val maxCities = 25
    private val citiesForPaging = ArrayList(exampleCityResponseTestFactory.createList(maxCities))
    private val lastCitiesFromPage = ArrayList<ExampleCityResponse>()

    private var lastItemId: String = "null"

    private val myPagingConfig = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(5)
            .setInitialLoadSizeHint(5)
            .setPrefetchDistance(2)
            .build()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        createStubsBeforeRepository()
        repository = ExampleCitiesRepository(exampleCitiesApiEndpointsInterface, mock())
    }

    private fun createStubsBeforeRepository() {
        whenever(exampleCitiesApiEndpointsInterface.getCitiesList(any())).thenReturn(getCitiesListApi())

        whenever(exampleCitiesApiEndpointsInterface.getPagedCitiesList(any(), any())).thenReturn(getCitiesPagedCities())
    }

    private fun getCitiesListApi() =
            Single.just(Response.success(
                    exampleCityResponseTestFactory.createList(25)))

    private fun getCitiesPagedCities(): Single<Response<List<ExampleCityResponse>>> {
        return Single.create<Response<List<ExampleCityResponse>>> {
            it.onSuccess(Response.success(getAndRemoveCities() as List<ExampleCityResponse>))
        }
    }

    private fun getAndRemoveCities(): ArrayList<ExampleCityResponse> {
        lastItemId = if (lastCitiesFromPage.size > 0)
            lastCitiesFromPage[lastCitiesFromPage.lastIndex].id
        else "null"

        lastCitiesFromPage.clear()
        lastCitiesFromPage.addAll(citiesForPaging.removeLastItems(cityCountPerPage))

        return lastCitiesFromPage
    }

    /**
     * Verify if networkState is correctly propagated, in right order etc.
     *
     * Verify if data is correctly loaded in data source
     *
     */
    @Test
    fun verifyDataLoading() {
        val listingFactory: ListingFactory<String, ExampleCityModel> = ListingFactory(myPagingConfig)

        var previousInitialNetworkState: NetworkState? = null

        listingFactory.initialStateBehaviourSubject.subscribe {
            checkNetworkStatusForSuccessfulRun(previousInitialNetworkState, it)
            previousInitialNetworkState = it
        }

        var previousNetworkState: NetworkState? = null

        listingFactory.networkStateBehaviorSubject.subscribe {
            checkNetworkStatusForSuccessfulRun(previousNetworkState, it)
            previousNetworkState = it
        }

        listingFactory.build(repository.citiesDataSourceFactory(spy())).observeForever {
            it!!
            Assert.assertEquals(10, it.size)

            it.loadAround(it.lastIndex)
            Assert.assertEquals(20, it.size)

            it.loadAround(it.lastIndex)
            Assert.assertEquals(25, it.size)

            it.loadAround(it.lastIndex)
            Assert.assertEquals(25, it.size)
        }
    }

    private fun checkNetworkStatusForSuccessfulRun(previousNetworkState: NetworkState?, currentNetworkState: NetworkState?) {
        when {
            previousNetworkState?.networkStatus == NetworkState.State.RUNNING -> {
                Assert.assertEquals(NetworkState.State.SUCCESS, currentNetworkState?.networkStatus)
            }
            previousNetworkState?.networkStatus == NetworkState.State.SUCCESS || previousNetworkState?.networkStatus == null -> {
                Assert.assertEquals(NetworkState.State.RUNNING, currentNetworkState?.networkStatus)
            }
            else -> Assert.fail()
        }
    }

}