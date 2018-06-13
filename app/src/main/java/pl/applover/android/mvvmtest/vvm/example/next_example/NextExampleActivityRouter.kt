package pl.applover.android.mvvmtest.vvm.example.next_example

import io.reactivex.subjects.PublishSubject
import pl.applover.android.mvvmtest.util.architecture.rx.EmptyEvent
import pl.applover.android.mvvmtest.vvm.example.next_example.example_list.ExampleListFragmentRouter
import javax.inject.Inject

/**
 * Created by Janusz Hain on 2018-06-11.
 */
class NextExampleActivityRouter @Inject constructor(exampleListFragmentRouter: ExampleListFragmentRouter) {
    val sender = Sender()
    val receiver = Receiver()

    init {
        exampleListFragmentRouter.sender.fragmentClicked.subscribe(receiver.fragmentClicked)
    }

    class Sender {

    }

    class Receiver {
        val fragmentClicked: PublishSubject<EmptyEvent> = PublishSubject.create()
    }
}