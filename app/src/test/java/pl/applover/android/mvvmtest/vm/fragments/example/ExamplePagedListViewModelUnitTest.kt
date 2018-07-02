package pl.applover.android.mvvmtest.vm.fragments.example

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.paging.ItemKeyedDataSource
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.spy
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import junit.framework.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.Spy
import pl.applover.android.mvvmtest.data.example.repositories.ExampleCitiesRepository
import pl.applover.android.mvvmtest.dataSources.example.cities.CitiesDataSource
import pl.applover.android.mvvmtest.dataSources.example.cities.CitiesDataSourceFactory
import pl.applover.android.mvvmtest.modelFactories.example.ExampleCityModelTestFactory
import pl.applover.android.mvvmtest.models.example.ExampleCityModel
import pl.applover.android.mvvmtest.util.architecture.network.NetworkState
import pl.applover.android.mvvmtest.util.architecture.network.NetworkStatus
import pl.applover.android.mvvmtest.util.architecture.paging.ListingFactoryItemKeyed
import pl.applover.android.mvvmtest.util.extensions.removeLastItems
import pl.applover.android.mvvmtest.util.other.SchedulerProvider
import pl.applover.android.mvvmtest.vvm.example.nextExample.examplePagedList.ExamplePagedListFragmentRouter
import pl.applover.android.mvvmtest.vvm.example.nextExample.examplePagedList.ExamplePagedListViewModel


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

    @Mock
    private lateinit var repository: ExampleCitiesRepository
    private lateinit var examplePagedListViewModel: ExamplePagedListViewModel

    @Spy
    private lateinit var router: ExamplePagedListFragmentRouter

    @Spy
    private val dataSourceFactory: CitiesDataSourceFactory = CitiesDataSourceFactory(spy(), spy())

    @Mock
    private lateinit var dataSource: CitiesDataSource


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

        whenever(dataSource.initialStateSubject).thenReturn(initialStateSubject)
        whenever(dataSource.networkStateSubject).thenReturn(networkStateSubject)

        whenever(repository.citiesListingFactory(any(), any())).thenAnswer {
            return@thenAnswer ListingFactoryItemKeyed(dataSourceFactory, it.getArgument(1))
        }

        whenever(dataSourceFactory.create()).thenAnswer {
            dataSourceFactory.subjectCitiesDataSource.onNext(dataSource)
            return@thenAnswer dataSource
        }

        whenever(dataSource.loadInitial(any(), any())).then {
            val callback = it.getArgument<ItemKeyedDataSource.LoadInitialCallback<ExampleCityModel>>(1)
            dataSource.initialStateSubject.onNext(NetworkState.LOADING)
            dataSource.networkStateSubject.onNext(NetworkState.LOADING)
            callback!!.onResult(getCities())
            dataSource.initialStateSubject.onNext(NetworkState.LOADED)
            dataSource.networkStateSubject.onNext(NetworkState.LOADED)
        }

        whenever(dataSource.loadAfter(any(), any())).then {
            val callback = it.getArgument<ItemKeyedDataSource.LoadCallback<ExampleCityModel>>(1)
            dataSource.networkStateSubject.onNext(NetworkState.LOADING)
            callback!!.onResult(getCities())
            dataSource.networkStateSubject.onNext(NetworkState.LOADED)
        }
    }

    private fun getCities(): MutableList<ExampleCityModel> {
        return ArrayList(citiesForPaging.removeLastItems(cityCountPerPage))
    }

    /**
     * Verifies if listing gets network states and new pages correctly
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
            it.loadAround(it.size - 1)
            Assert.assertEquals(20, it.size)

            it.loadAround(it.size - 1)
            Assert.assertEquals(25, it.size)

            it.loadAround(it.size - 1)
            Assert.assertEquals(25, it.size)
        }

    }

    private fun checkNetworkStatusForSuccessfulRun(previousNetworkState: NetworkState?, currentNetworkState: NetworkState?) {
        when {
            previousNetworkState?.networkStatus == NetworkStatus.RUNNING -> {
                Assert.assertEquals(NetworkStatus.SUCCESS, currentNetworkState?.networkStatus)
            }
            previousNetworkState?.networkStatus == NetworkStatus.SUCCESS || previousNetworkState?.networkStatus == null -> {
                Assert.assertEquals(NetworkStatus.RUNNING, currentNetworkState?.networkStatus)
            }
            else -> Assert.fail()
        }
    }
}
