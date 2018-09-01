package pl.applover.architecture.mvvm.vvm.example.nextexample.examplepagedList

import pl.applover.architecture.mvvm.vvm.example.nextexample.examplepagedList.ExamplePagedListFragmentRouter.Receiver
import pl.applover.architecture.mvvm.vvm.example.nextexample.examplepagedList.ExamplePagedListFragmentRouter.Sender

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
class ExamplePagedListFragmentRouter {

    val sender = Sender()
    val receiver = Receiver()

    class Sender {

    }

    class Receiver {

    }
}