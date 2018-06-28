package pl.applover.android.mvvmtest.vm.fragments.example

import android.arch.core.executor.testing.InstantTaskExecutorRule
import io.reactivex.schedulers.Schedulers
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.Spy
import pl.applover.android.mvvmtest.data.example.repositories.ExampleCitiesRepository
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
    private lateinit var mockRepository: ExampleCitiesRepository

    private lateinit var examplePagedListViewModel: ExamplePagedListViewModel

    @Spy
    private val spiedRouter: ExamplePagedListFragmentRouter = ExamplePagedListFragmentRouter()

    private val schedulerProvider = SchedulerProvider(Schedulers.trampoline(), Schedulers.trampoline())

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        examplePagedListViewModel = ExamplePagedListViewModel(spiedRouter, schedulerProvider, mockRepository)
    }


    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }
}
