package pl.applover.architecture.mvvm.example.vm.fragments.example

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.paging.ItemKeyedDataSource
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import junit.framework.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.MockitoAnnotations
import pl.applover.architecture.mvvm.data.example.repositories.ExampleCitiesRepository
import pl.applover.architecture.mvvm.datasources.example.cities.CitiesDataSource
import pl.applover.architecture.mvvm.datasources.example.cities.CitiesDataSourceFactory
import pl.applover.architecture.mvvm.modelfactories.example.ExampleCityModelTestFactory
import pl.applover.architecture.mvvm.models.example.ExampleCityModel
import pl.applover.architecture.mvvm.util.architecture.network.NetworkState
import pl.applover.architecture.mvvm.util.extensions.removeLastItems
import pl.applover.architecture.mvvm.util.other.SchedulerProvider
import pl.applover.architecture.mvvm.vvm.example.nextexample.examplepagedList.ExamplePagedListFragmentRouter
import pl.applover.architecture.mvvm.vvm.example.nextexample.examplepagedList.ExamplePagedListViewModel


/**
 * Created by Janusz Hain on 2018-06-27.
 */
class ExamplePagedListViewModelUnitTest {

    /**
     * A JUnit Test Rule that swaps the background executor used by the Architecture Components with a
     * different one which executes each task synchronously.
     * <p>
     * You can use this rule for your host side tests that use Architecture Components.
     */
    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    private val schedulerProvider = SchedulerProvider(Schedulers.trampoline(), Schedulers.trampoline())

    private val repository: ExampleCitiesRepository = mockk()
    private lateinit var examplePagedListViewModel: ExamplePagedListViewModel

    private val router: ExamplePagedListFragmentRouter = spyk()

    private val dataSourceFactory: CitiesDataSourceFactory = spyk(CitiesDataSourceFactory(spyk(), spyk()))

    private val dataSource: CitiesDataSource = mockk()


    //Data Generator

    private val exampleCityModelTestFactory = ExampleCityModelTestFactory()

    //City list for testing
    private val cityCountPerPage = 10
    private val maxCities = 25
    private val citiesForPaging = ArrayList(exampleCityModelTestFactory.createList(maxCities))

    private val networkStateSubject: BehaviorSubject<NetworkState> = BehaviorSubject.create()
    private val initialStateSubject: BehaviorSubject<NetworkState> = BehaviorSubject.create()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        createStubsBeforeViewModel()
        examplePagedListViewModel = ExamplePagedListViewModel(router, schedulerProvider, repository)
    }

    private fun createStubsBeforeViewModel() {

        every { dataSource.initialStateSubject }.returns(initialStateSubject)
        every { dataSource.networkStateSubject }.returns(networkStateSubject)

        every { repository.citiesDataSourceFactory(any()) }.returns(dataSourceFactory)

        every { dataSourceFactory.create() }.answers {
            dataSourceFactory.subjectDataSource.onNext(dataSource)
            return@answers dataSource
        }

        every { dataSource.loadInitial(any(), any()) }.answers {
            val callback = arg<ItemKeyedDataSource.LoadInitialCallback<ExampleCityModel>>(1)
            dataSource.initialStateSubject.onNext(NetworkState.LOADING)
            dataSource.networkStateSubject.onNext(NetworkState.LOADING)
            callback.onResult(getCities())
            dataSource.initialStateSubject.onNext(NetworkState.LOADED)
            dataSource.networkStateSubject.onNext(NetworkState.LOADED)
        }

        every { dataSource.loadAfter(any(), any()) }.answers {
            val callback = arg<ItemKeyedDataSource.LoadCallback<ExampleCityModel>>(1)
            dataSource.networkStateSubject.onNext(NetworkState.LOADING)
            callback.onResult(getCities())
            dataSource.networkStateSubject.onNext(NetworkState.LOADED)
        }
    }

    private fun getCities(): MutableList<ExampleCityModel> {
        return ArrayList(citiesForPaging.removeLastItems(cityCountPerPage))
    }

    /**
     * Verify if network states were called, this test doesn't verify if network states are called in right order etc.
     *
     * Verify if new pages are loaded into PagedList
     */
    @Test
    fun verifyListingCreatedCorrectly() {
        var previousInitialNetworkState: NetworkState? = null

        examplePagedListViewModel.mldInitialNetworkState.observeForever {
            checkNetworkStatusForSuccessfulRun(previousInitialNetworkState, it)
            previousInitialNetworkState = it
        }

        var previousNetworkState: NetworkState? = null

        examplePagedListViewModel.mldNetworkState.observeForever {
            checkNetworkStatusForSuccessfulRun(previousNetworkState, it)
            previousNetworkState = it
        }

        examplePagedListViewModel.ldCitiesPagedList.observeForever {
            it!!

            Assert.assertNotNull(previousInitialNetworkState)
            Assert.assertEquals(10, it.size)

            Assert.assertNotNull(previousNetworkState)
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
