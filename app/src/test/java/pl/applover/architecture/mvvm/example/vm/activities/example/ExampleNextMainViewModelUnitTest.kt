package pl.applover.architecture.mvvm.example.vm.activities.example

import android.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import io.reactivex.subjects.PublishSubject
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import pl.applover.architecture.mvvm.util.architecture.livedata.SingleEvent
import pl.applover.architecture.mvvm.util.architecture.rx.EmptyEvent
import pl.applover.architecture.mvvm.vvm.example.nextexample.NextExampleActivityRouter
import pl.applover.architecture.mvvm.vvm.example.nextexample.NextExampleViewModel

/**
 * Created by Janusz Hain on 2018-06-27.
 */
class ExampleNextMainViewModelUnitTest {

    @Rule
    @JvmField
    //Rule for livedata same thread
    val rule = InstantTaskExecutorRule()

    private val mainActivityRouter: NextExampleActivityRouter = mockk()
    private lateinit var mainViewModel: NextExampleViewModel
    private val emptyEventSubject: PublishSubject<EmptyEvent> = PublishSubject.create()


    @Before
    fun setup() {
        every { mainActivityRouter.receiver.fragmentClicked }.returns(emptyEventSubject)

        mainViewModel = spyk(NextExampleViewModel(mainActivityRouter, mockk()))
    }

    @Test
    fun `fragment clicked event passed to livedata`() {
        val liveDataObserver: android.arch.lifecycle.Observer<SingleEvent<EmptyEvent>> = spyk()
        mainViewModel.mldFragmentClicked.observeForever(liveDataObserver)
        mainActivityRouter.receiver.fragmentClicked.onNext(EmptyEvent())
        verify { liveDataObserver.onChanged(any()) }
    }
}
