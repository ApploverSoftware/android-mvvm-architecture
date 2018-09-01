package pl.applover.architecture.mvvm.example.routers.example

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

        var fragmentClicked = false

        nextExampleActivityRouter.receiver.fragmentClicked.subscribe {
            fragmentClicked = true
        }

        exampleListFragmentRouter.sender.fragmentClicked.onNext(EmptyEvent())

        assert(fragmentClicked)
    }
}