package pl.applover.android.mvvmtest.vm.fragments

import android.arch.core.executor.testing.InstantTaskExecutorRule
import io.reactivex.subjects.PublishSubject
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import pl.applover.android.mvvmtest.data.example.repositories.ExampleCitiesRepository
import pl.applover.android.mvvmtest.util.architecture.rx.EmptyEvent
import pl.applover.android.mvvmtest.vvm.example.nextExample.exampleList.ExampleListFragmentRouter
import pl.applover.android.mvvmtest.vvm.example.nextExample.exampleList.ExampleListViewModel
import java.lang.reflect.Field
import java.lang.reflect.Modifier


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

    private val router = ExampleListFragmentRouter()

    @Mock
    private lateinit var mockedPublishSubject: PublishSubject<EmptyEvent>


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        val field = ExampleListFragmentRouter.Sender::class.java.getDeclaredField("fragmentClicked")
        field.isAccessible = true
        val modifiersField = Field::class.java.getDeclaredField("modifiers")
        modifiersField.isAccessible = true
        modifiersField.setInt(field, field.modifiers and Modifier.FINAL.inv())

        field.set(router.sender, mockedPublishSubject)

        println(router.sender.fragmentClicked)
        exampleListViewModel = ExampleListViewModel(router, mockRepository)
    }

    @Test
    fun testRouterSender() {
        exampleListViewModel.fragmentClicked()
        Mockito.verify(router.sender.fragmentClicked, Mockito.times(1)).onNext(Mockito.any())
    }


}
