package pl.applover.android.mvvmtest.vm.fragments.example

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.paging.ItemKeyedDataSource
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.capture
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import junit.framework.Assert.assertEquals
import junit.framework.Assert.fail
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.*
import pl.applover.android.mvvmtest.data.example.repositories.ExampleCitiesRepository
import pl.applover.android.mvvmtest.dataSources.example.cities.CitiesDataSource
import pl.applover.android.mvvmtest.dataSources.example.cities.CitiesDataSourceFactory
import pl.applover.android.mvvmtest.modelFactories.example.ExampleCityModelTestFactory
import pl.applover.android.mvvmtest.models.example.ExampleCityModel
import pl.applover.android.mvvmtest.util.architecture.network.NetworkState
import pl.applover.android.mvvmtest.util.architecture.network.NetworkStatus
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

    @Mock
    private lateinit var mockedRepository: ExampleCitiesRepository

    private lateinit var examplePagedListViewModel: ExamplePagedListViewModel

    @Spy
    private lateinit var spiedRouter: ExamplePagedListFragmentRouter

    private val schedulerProvider = SchedulerProvider(Schedulers.trampoline(), Schedulers.trampoline())

    private val exampleCityModelTestFactory = ExampleCityModelTestFactory()

    //City list for testing
    private val cityCountPerPage = 10
    private val maxCities = 25
    private val citiesForPaging = ArrayList(exampleCityModelTestFactory.createList(maxCities))

    private var networkStateRunningCount = 0

    @Mock
    private lateinit var dataSourceFactory: CitiesDataSourceFactory

    @Mock
    private lateinit var dataSource: CitiesDataSource

    @Captor
    private lateinit var captorInitialLoad: ArgumentCaptor<ItemKeyedDataSource.LoadInitialCallback<ExampleCityModel>>

    @Captor
    private lateinit var captorLoad: ArgumentCaptor<ItemKeyedDataSource.LoadCallback<ExampleCityModel>>

    private val subjectCitiesDataSource: BehaviorSubject<CitiesDataSource> = BehaviorSubject.create()

    private val networkStateSubject: BehaviorSubject<NetworkState> = BehaviorSubject.create()
    private val initialStateSubject: BehaviorSubject<NetworkState> = BehaviorSubject.create()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        createStubsBeforeViewModel()
        examplePagedListViewModel = ExamplePagedListViewModel(spiedRouter, schedulerProvider, mockedRepository)
    }

    private fun createStubsBeforeViewModel() {

        //Before returning dataSource, pass it to our new subject
        doAnswer {
            subjectCitiesDataSource.onNext(dataSource)
            return@doAnswer dataSource
        }.whenever(dataSourceFactory).create()

        whenever(mockedRepository.citiesDataSourceFactory(any())).thenReturn(dataSourceFactory)

        whenever(dataSource.initialStateSubject).thenReturn(initialStateSubject)
        whenever(dataSource.networkStateSubject).thenReturn(networkStateSubject)

        whenever(mockedRepository.citiesInitialStateBehaviourSubject(any())).thenReturn(subjectCitiesDataSource.switchMap { it.initialStateSubject })
        whenever(mockedRepository.citiesNetworkStateBehaviorSubject(any())).thenReturn(subjectCitiesDataSource.switchMap { it.networkStateSubject })
    }

    private fun getCities(): MutableList<ExampleCityModel> {
        return ArrayList(citiesForPaging.removeLastItems(cityCountPerPage))
    }

    @Test
    fun checkNetworkStateForSuccessfulCalls() {


        //When datasource have to load initial data then return new cities page
        whenever(dataSource.loadInitial(any(), capture(captorInitialLoad))).then {
            dataSource.initialStateSubject.onNext(NetworkState.LOADING)
            dataSource.networkStateSubject.onNext(NetworkState.LOADING)
            networkStateRunningCount++
            
            captorInitialLoad.value.onResult(getCities())
            dataSource.initialStateSubject.onNext(NetworkState.LOADED)
            dataSource.networkStateSubject.onNext(NetworkState.LOADED)
        }

        //When datasource have to load after data then return new cities page
        whenever(dataSource.loadAfter(any(), capture(captorLoad))).then {
            dataSource.networkStateSubject.onNext(NetworkState.LOADING)
            networkStateRunningCount++

            captorLoad.value.onResult(getCities())
            dataSource.networkStateSubject.onNext(NetworkState.LOADED)
        }

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

        //Not really needed to assert sizes of the list as dataSource is mocked
        examplePagedListViewModel.ldCitiesPagedList.observeForever {
            it?.let {
                assertEquals(10, it.size)

                it.loadAround(it.size - 1)
                assertEquals(20, it.size)

                it.loadAround(it.size - 1)
                assertEquals(25, it.size)

                it.loadAround(it.size - 1)
                assertEquals(25, it.size)

                assertEquals(4, networkStateRunningCount)
            }
        }
    }

    private fun checkNetworkStatusForSuccessfulRun(previousNetworkState: NetworkState?, currentNetworkState: NetworkState?) {
        when {
            previousNetworkState?.networkStatus == NetworkStatus.RUNNING -> {
                assertEquals(NetworkStatus.SUCCESS, currentNetworkState?.networkStatus)
            }
            previousNetworkState?.networkStatus == NetworkStatus.SUCCESS || previousNetworkState?.networkStatus == null -> {
                assertEquals(NetworkStatus.RUNNING, currentNetworkState?.networkStatus)
            }
            else -> fail()
        }
    }
}
