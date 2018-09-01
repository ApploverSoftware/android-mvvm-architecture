package pl.applover.architecture.mvvm.example.repositories.example

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.paging.PagedList
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.reactivex.Single
import junit.framework.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import pl.applover.architecture.mvvm.data.example.internet.apiendpoints.ExampleCitiesApiEndpointsInterface
import pl.applover.architecture.mvvm.data.example.internet.response.ExampleCityResponse
import pl.applover.architecture.mvvm.data.example.repositories.ExampleCitiesRepository
import pl.applover.architecture.mvvm.modelfactories.example.ExampleCityResponseTestFactory
import pl.applover.architecture.mvvm.models.example.ExampleCityModel
import pl.applover.architecture.mvvm.util.architecture.network.NetworkState
import pl.applover.architecture.mvvm.util.architecture.paging.ListingFactory
import pl.applover.architecture.mvvm.util.extensions.removeLastItems
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

    private val exampleCitiesApiEndpointsInterface: ExampleCitiesApiEndpointsInterface = mockk()

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
    fun setup() {
        createStubsBeforeRepository()
        repository = ExampleCitiesRepository(exampleCitiesApiEndpointsInterface, mockk())
    }

    private fun createStubsBeforeRepository() {
        every { exampleCitiesApiEndpointsInterface.getCitiesList(any()) }.returns(getCitiesListApi())

        every { exampleCitiesApiEndpointsInterface.getPagedCitiesList(any(), any()) }.returns(getCitiesPagedCities())
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

        listingFactory.build(repository.citiesDataSourceFactory(spyk())).observeForever {
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