package pl.applover.android.mvvmtest.vvm.example.nextExample.exampleList

import io.reactivex.subjects.PublishSubject
import pl.applover.android.mvvmtest.util.architecture.rx.EmptyEvent
import pl.applover.android.mvvmtest.vvm.example.nextExample.exampleList.ExampleListFragmentRouter.Receiver
import pl.applover.android.mvvmtest.vvm.example.nextExample.exampleList.ExampleListFragmentRouter.Sender

/**
 * Created by Janusz Hain on 2018-06-11.
 */

/**
 * Router is the class for exposing what given ViewModel offers ([Sender]) and what it needs ([Receiver]).
 * Routers that are passed in constructor are child routers that have to communicate with each other using parent router.
 * Router is responsible for connecting [Sender] subjects with [Receiver] subjects to create data flow between routers.
 *
 * [Sender] subject in his router's ViewModel has to be called using onNext to send data.
 * Router has to subscribe [Sender] setting [Receiver] of another router as subject who listens to [Sender].
 * [Receiver] can be subscribed in router's ViewModel in order to receive data
 */
class ExampleListFragmentRouter {

    val sender = Sender()
    val receiver = Receiver()

    class Sender {
        val fragmentClicked: PublishSubject<EmptyEvent> = PublishSubject.create()
    }

    class Receiver {

    }
}