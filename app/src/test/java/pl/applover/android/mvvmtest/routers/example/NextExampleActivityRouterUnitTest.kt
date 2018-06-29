package pl.applover.android.mvvmtest.routers.example

import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations
import pl.applover.android.mvvmtest.util.architecture.rx.EmptyEvent
import pl.applover.android.mvvmtest.vvm.example.nextExample.NextExampleActivityRouter
import pl.applover.android.mvvmtest.vvm.example.nextExample.exampleList.ExampleListFragmentRouter
import pl.applover.android.mvvmtest.vvm.example.nextExample.examplePagedList.ExamplePagedListFragmentRouter

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
    fun testRouterSubjectPipes() {

        var fragmentClicked = false

        nextExampleActivityRouter.receiver.fragmentClicked.subscribe {
            fragmentClicked = true
        }

        exampleListFragmentRouter.sender.fragmentClicked.onNext(EmptyEvent())

        assert(fragmentClicked)
    }
}