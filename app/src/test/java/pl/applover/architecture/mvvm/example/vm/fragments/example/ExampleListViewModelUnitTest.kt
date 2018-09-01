package pl.applover.architecture.mvvm.example.vm.fragments.example

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleRegistry
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import junit.framework.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import pl.applover.architecture.mvvm.data.example.repositories.ExampleCitiesRepository
import pl.applover.architecture.mvvm.modelFactories.example.ExampleCityModelTestFactory
import pl.applover.architecture.mvvm.util.architecture.liveData.SingleEvent
import pl.applover.architecture.mvvm.util.architecture.network.NetworkState
import pl.applover.architecture.mvvm.util.architecture.rx.EmptyEvent
import pl.applover.architecture.mvvm.util.other.SchedulerProvider
import pl.applover.architecture.mvvm.vvm.example.nextExample.exampleList.ExampleListFragmentRouter
import pl.applover.architecture.mvvm.vvm.example.nextExample.exampleList.ExampleListViewModel
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

    private val mockRepository: ExampleCitiesRepository = mockk()

    private lateinit var exampleListViewModel: ExampleListViewModel

    private val spiedRouter: ExampleListFragmentRouter = spyk()

    private val mockedSender: ExampleListFragmentRouter.Sender = mockk()

    private val fragmentClickedSubject: PublishSubject<EmptyEvent> = PublishSubject.create()

    private val schedulerProvider = SchedulerProvider(Schedulers.trampoline(), Schedulers.trampoline())

    private val exampleCityModelTestFactory: ExampleCityModelTestFactory = ExampleCityModelTestFactory()


    @Before
    fun setUp() {
        every { spiedRouter.sender }.returns(mockedSender)
        every { mockedSender.fragmentClicked }.returns(fragmentClickedSubject)

        exampleListViewModel = ExampleListViewModel(spiedRouter, schedulerProvider, mockRepository)
    }

    /**
     * Is sender's subject in router getting info about click from fragment
     */
    @Test
    fun testFragmentClicked() {
        val observer: TestObserver<EmptyEvent> = spyk()
        fragmentClickedSubject.subscribe(observer)
        exampleListViewModel.fragmentClicked()

        observer.assertNoErrors()
        verify { observer.onNext(any()) }
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
        val lifecycle = LifecycleRegistry(mockk())
        val observer = spyk<(SingleEvent<String>?) -> Unit>()

        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)

        exampleListViewModel.showSomeToast()

        //assert sent Event wasn't handled
        assertEquals(false, exampleListViewModel.mldSomeToast.value?.hasBeenHandled(this))

        //verify no liveData event is sent when Fragment is created
        verify(exactly = 0) { observer.invoke(any()) }

        //verify liveData event is sent after Fragment can manipulate UI
        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_START)

        exampleListViewModel.mldSomeToast.observe({ lifecycle }) {
            observer(it)
            it?.getContentIfNotHandled(this)
        }

        verify(exactly = 1) { observer.invoke(any()) }

        //assert sent Event was handled
        assertEquals(true, exampleListViewModel.mldSomeToast.value?.hasBeenHandled(this))

        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_PAUSE)

        //verify no new event wasn't sent after onStop in Fragment
        verify(exactly = 1) { observer.invoke(any()) }

        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)

        //verify new event wasn't sent again after onResume Fragment
        verify(exactly = 1) { observer.invoke(any()) }
    }


    /**
     * How does LiveData event behave for restarted Fragment
     */
    @Test
    fun testShowSomeToastForFragmentRecreated() {
        val lifecycle = LifecycleRegistry(mockk())
        val observer = spyk<(SingleEvent<String>?) -> Unit>()

        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)

        exampleListViewModel.showSomeToast()

        //verify no liveData event is sent when Fragment is created
        verify(exactly = 0) { observer.invoke(any()) }

        //verify liveData event is sent after Fragment can manipulate UI
        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_START)

        exampleListViewModel.mldSomeToast.observe({ lifecycle }) {
            observer(it)

            //assert event is not null and content wasn't handled
            assertNotNull(it?.getContentIfNotHandled(this))
        }

        verify(exactly = 1) { observer.invoke(any()) }

        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_PAUSE)

        //verify no new event wasn't sent after onStop in Fragment
        verify(exactly = 1) { observer.invoke(any()) }

        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_STOP)

        //verify new event wasn't sent again after onStop Fragment
        verify(exactly = 1) { observer.invoke(any()) }

        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_START)

        //verify new event wasn't sent again after onStart in Fragment without subscribing again
        verify(exactly = 1) { observer.invoke(any()) }

        exampleListViewModel.mldSomeToast.observe({ lifecycle }) {
            observer(it)

            //assert event is not null
            assertNotNull(it)
            //assert content was handled already
            assertNull(it!!.getContentIfNotHandled(this))
        }

        //verify new event was sent again after onStart in Fragment after subscribing livedata again
        verify(exactly = 2) { observer.invoke(any()) }
    }

    /**
     * Are cities loaded into live data
     */
    @Test
    fun testLoadCities() {
        every {
            mockRepository.citiesFromNetwork()
        }.returns(Single.just(Response.success(
                exampleCityModelTestFactory.createList(25))))

        val isInitialValue = AtomicBoolean(true)



        //assert cities are passed to live data correctly
        exampleListViewModel.mldCitiesLiveData.observeForever {
            if (!isInitialValue.get()) {
                it?.let {
                    assertEquals(25, it.size) //todo something catches error here, that's why test can pass with failed assert
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
                        assert(it.networkStatus == NetworkState.State.RUNNING)
                        networkStateChangedCount++
                    }
                    1 -> {
                        assert(it.networkStatus == NetworkState.State.SUCCESS)
                    }
                    else -> {
                    }
                }
            }
        }

        exampleListViewModel.loadCities()
    }
}
