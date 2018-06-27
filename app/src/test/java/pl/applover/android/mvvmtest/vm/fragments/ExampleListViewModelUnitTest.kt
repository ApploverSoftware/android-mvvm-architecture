package pl.applover.android.mvvmtest.vm.fragments

import android.arch.core.executor.testing.InstantTaskExecutorRule
import io.reactivex.subjects.PublishSubject
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.Spy
import pl.applover.android.mvvmtest.data.example.repositories.ExampleCitiesRepository
import pl.applover.android.mvvmtest.util.architecture.rx.EmptyEvent
import pl.applover.android.mvvmtest.vvm.example.nextExample.exampleList.ExampleListFragmentRouter
import pl.applover.android.mvvmtest.vvm.example.nextExample.exampleList.ExampleListViewModel


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleListViewModelUnitTest {

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


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        setUpMocksForRouter()

        exampleListViewModel = ExampleListViewModel(spiedRouter, mockRepository)
    }

    private fun setUpMocksForRouter() {
        Mockito.`when`(spiedRouter.sender).thenReturn(mockedSender)
        Mockito.`when`(mockedSender.fragmentClicked).thenReturn(mockedFragmentClickedSubject)
    }

    @Test
    fun testRouterSender() {
        exampleListViewModel.fragmentClicked()
        Mockito.verify(spiedRouter.sender.fragmentClicked, Mockito.times(1)).onNext(Mockito.any())
    }


}
