package pl.applover.android.mvvmtest.vvm.example.next_example.example_list

import io.reactivex.subjects.PublishSubject

/**
 * Created by Janusz Hain on 2018-06-11.
 */
class ExampleListFragmentRouter {

    val sender = Sender()
    val receiver = Receiver()

    class Sender {
        val fragmentClicked: PublishSubject<String> = PublishSubject.create()
    }

    class Receiver {

    }
}