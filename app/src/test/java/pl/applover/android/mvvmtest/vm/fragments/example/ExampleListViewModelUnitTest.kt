package pl.applover.android.mvvmtest.vm.fragments.example

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LifecycleRegistry
import com.nhaarman.mockitokotlin2.*
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import junit.framework.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.Spy
import pl.applover.android.mvvmtest.data.example.repositories.ExampleCitiesRepository
import pl.applover.android.mvvmtest.lambdaMock
import pl.applover.android.mvvmtest.modelFactories.example.ExampleCityModelTestFactory
import pl.applover.android.mvvmtest.util.architecture.liveData.SingleEvent
import pl.applover.android.mvvmtest.util.architecture.network.NetworkStatus
import pl.applover.android.mvvmtest.util.architecture.rx.EmptyEvent
import pl.applover.android.mvvmtest.util.other.SchedulerProvider
import pl.applover.android.mvvmtest.vvm.example.nextExample.exampleList.ExampleListFragmentRouter
import pl.applover.android.mvvmtest.vvm.example.nextExample.exampleList.ExampleListViewModel
import retrofit2.Response
import java.util.concurrent.atomic.AtomicBoolean

/**
 * Created by Janusz Hain on 2018-06-27.
 */
class ExampleListViewModelUnitTest {

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
    private lateinit var mockRepository: ExampleCitiesRepository

    private lateinit var exampleListViewModel: ExampleListViewModel

    @Spy
    private val spiedRouter: ExampleListFragmentRouter = ExampleListFragmentRouter()

    @Mock
    private lateinit var mockedSender: ExampleListFragmentRouter.Sender

    @Mock
    private lateinit var mockedFragmentClickedSubject: PublishSubject<EmptyEvent>

    private val schedulerProvider = SchedulerProvider(Schedulers.trampoline(), Schedulers.trampoline())

    private val exampleCityModelTestFactory: ExampleCityModelTestFactory = ExampleCityModelTestFactory()


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        setUpMocksForRouter()

        exampleListViewModel = ExampleListViewModel(spiedRouter, schedulerProvider, mockRepository)
    }

    private fun setUpMocksForRouter() {
        whenever(spiedRouter.sender).thenReturn(mockedSender)
        whenever(mockedSender.fragmentClicked).thenReturn(mockedFragmentClickedSubject)
    }

    /**
     * Is sender's subject in router getting info about click from fragment
     */
    @Test
    fun testFragmentClicked() {
        exampleListViewModel.fragmentClicked()
        verify(spiedRouter.sender.fragmentClicked, times(1)).onNext(any())
    }


    /**
     * Is Event correctly set in LiveData
     */
    @Test
    fun testShowSomeToast() {
        exampleListViewModel.showSomeToast()
        assertEquals(SingleEvent("mldSomeToast"), exampleListViewModel.mldSomeToast.value)
    }

    /**
     * How does LiveData event behave for paused Fragment
     */
    @Test
    fun testShowSomeToastForFragmentPaused() {
        val lifecycle = LifecycleRegistry(mock<LifecycleOwner>())
        val observer = lambdaMock<(SingleEvent<String>?) -> Unit>()

        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)

        exampleListViewModel.showSomeToast()

        //assert sent Event wasn't handled
        assertEquals(false, exampleListViewModel.mldSomeToast.value?.hasBeenHandled(this))

        //verify no liveData event is sent when Fragment is created
        verify(observer, times(0)).invoke(any())

        //verify liveData event is sent after Fragment can manipulate UI
        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_START)

        exampleListViewModel.mldSomeToast.observe({ lifecycle }) {
            observer(it)
            it?.getContentIfNotHandled(this)
        }

        verify(observer, times(1)).invoke(any())

        //assert sent Event was handled
        assertEquals(true, exampleListViewModel.mldSomeToast.value?.hasBeenHandled(this))

        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_PAUSE)

        //verify no new event wasn't sent after onStop in Fragment
        verify(observer, times(1)).invoke(any())

        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)

        //verify new event wasn't sent again after onResume Fragment
        verify(observer, times(1)).invoke(any())
    }


    /**
     * How does LiveData event behave for restarted Fragment
     */
    @Test
    fun testShowSomeToastForFragmentRecreated() {
        val lifecycle = LifecycleRegistry(mock<LifecycleOwner>())
        val liveDataUnit = lambdaMock<(SingleEvent<String>?) -> Unit>()

        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)

        exampleListViewModel.showSomeToast()

        //verify no liveData event is sent when Fragment is created
        verify(liveDataUnit, times(0)).invoke(any())

        //verify liveData event is sent after Fragment can manipulate UI
        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_START)

        exampleListViewModel.mldSomeToast.observe({ lifecycle }) {
            liveDataUnit(it)

            //assert event is not null and content wasn't handled
            assertNotNull(it?.getContentIfNotHandled(this))
        }

        verify(liveDataUnit, times(1)).invoke(any())

        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_PAUSE)

        //verify no new event wasn't sent after onStop in Fragment
        verify(liveDataUnit, times(1)).invoke(any())

        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_STOP)

        //verify new event wasn't sent again after onStop Fragment
        verify(liveDataUnit, times(1)).invoke(any())

        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_START)

        //verify new event wasn't sent again after onStart in Fragment without subscribing again
        verify(liveDataUnit, times(1)).invoke(any())

        exampleListViewModel.mldSomeToast.observe({ lifecycle }) {
            liveDataUnit(it)

            //assert event is not null
            assertNotNull(it)
            //assert content was handled already
            assertNull(it!!.getContentIfNotHandled(this))
        }

        //verify new event was sent again after onStart in Fragment after subscribing livedata again
        verify(liveDataUnit, times(2)).invoke(any())
    }

    /**
     * Are cities loaded into live data
     */
    @Test
    fun testLoadCities() {
        whenever(mockRepository.citiesFromNetwork()).thenReturn(Single.just(Response.success(
                exampleCityModelTestFactory.createList(25))))

        val isInitialValue = AtomicBoolean(true)

        //assert cities are passed to live data correctly
        exampleListViewModel.mldCitiesLiveData.observeForever {
            if(!isInitialValue.get()) {
                it?.let {
                    assertEquals(25, it.size)
                }
            }
            isInitialValue.set(false)
        }

        var networkStateChangedCount = 0

        //assert network status is changed correctly
        exampleListViewModel.mldNetworkState.observeForever {
            it?.let {
                when (networkStateChangedCount) {
                    0 -> {
                        assert(it.networkStatus == NetworkStatus.RUNNING)
                        networkStateChangedCount++
                    }
                    1 -> {
                        assert(it.networkStatus == NetworkStatus.SUCCESS)
                    }
                    else -> {
                    }
                }
            }
        }

        exampleListViewModel.loadCities()
    }
}
