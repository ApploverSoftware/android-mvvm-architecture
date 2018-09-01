package pl.applover.architecture.mvvm.example.routers.example

import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations
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


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun testRouting() {

        var fragmentClicked = false

        nextExampleActivityRouter.receiver.fragmentClicked.subscribe {
            fragmentClicked = true
        }

        exampleListFragmentRouter.sender.fragmentClicked.onNext(EmptyEvent())

        assert(fragmentClicked)
    }
}