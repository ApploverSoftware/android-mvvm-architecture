package pl.applover.android.mvvmtest.vm.fragments.example

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.paging.ItemKeyedDataSource
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.capture
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import junit.framework.Assert.assertEquals
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

    @Mock
    private lateinit var dataSourceFactory: CitiesDataSourceFactory

    @Mock
    private lateinit var dataSource: CitiesDataSource

    @Spy
    private lateinit var observable: Observable<NetworkState>

    @Captor
    private lateinit var captorInitialLoad: ArgumentCaptor<ItemKeyedDataSource.LoadInitialCallback<ExampleCityModel>>

    @Captor
    private lateinit var captorLoad: ArgumentCaptor<ItemKeyedDataSource.LoadCallback<ExampleCityModel>>


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        createStubsBeforeViewModel()
        examplePagedListViewModel = ExamplePagedListViewModel(spiedRouter, schedulerProvider, mockedRepository)

        createStubsAfterViewModel()

    }

    private fun createStubsBeforeViewModel() {
        whenever(dataSourceFactory.create()).thenReturn(dataSource)
        whenever(mockedRepository.citiesDataSourceFactory(any())).thenReturn(dataSourceFactory)

        whenever(mockedRepository.citiesInitialStateBehaviourSubject(any())).thenReturn(observable)
        whenever(mockedRepository.citiesNetworkStateBehaviorSubject(any())).thenReturn(observable)
    }

    private fun createStubsAfterViewModel() {
        whenever(dataSource.loadInitial(any(), capture(captorInitialLoad))).then {
            captorInitialLoad.value.onResult(getCities())
        }

        whenever(dataSource.loadAfter(any(), capture(captorLoad))).then {
            captorLoad.value.onResult(getCities())
        }
    }

    private fun getCities(): MutableList<ExampleCityModel> {
        return ArrayList(citiesForPaging.removeLastItems(cityCountPerPage))
    }


    @Test
    fun successfulyLoadCitiesFromOnlineSource() {
        examplePagedListViewModel.pagedList.observeForever {
            it?.let {

                assertEquals(10, it.size)

                it.loadAround(it.size - 1)
                assertEquals(20, it.size)

                it.loadAround(it.size - 1)
                assertEquals(25, it.size)

                it.loadAround(it.size - 1)
                assertEquals(25, it.size)
            }
        }
    }
}
