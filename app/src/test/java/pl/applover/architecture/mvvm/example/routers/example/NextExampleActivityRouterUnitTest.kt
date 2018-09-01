package pl.applover.architecture.mvvm.example.routers.example

import io.mockk.spyk
import io.mockk.verify
import io.reactivex.Observer
import org.junit.Test
import pl.applover.architecture.mvvm.util.architecture.rx.EmptyEvent
import pl.applover.architecture.mvvm.vvm.example.nextexample.NextExampleActivityRouter
import pl.applover.architecture.mvvm.vvm.example.nextexample.examplelist.ExampleListFragmentRouter
import pl.applover.architecture.mvvm.vvm.example.nextexample.examplepagedList.ExamplePagedListFragmentRouter

/**
 * Created by Janusz Hain on 2018-06-29.
 */
class NextExampleActivityRouterUnitTest {

    private val exampleListFragmentRouter = ExampleListFragmentRouter()

    private val examplePagedListFragmentRouter = ExamplePagedListFragmentRouter()

    private var nextExampleActivityRouter = NextExampleActivityRouter(exampleListFragmentRouter, examplePagedListFragmentRouter)

    @Test
    fun `fragment clicked event sent`() {
        val observer: Observer<EmptyEvent> = spyk()
        nextExampleActivityRouter.receiver.fragmentClicked.subscribe(observer)
        exampleListFragmentRouter.sender.fragmentClicked.onNext(EmptyEvent())
        verify { observer.onNext(any()) }
    }
}