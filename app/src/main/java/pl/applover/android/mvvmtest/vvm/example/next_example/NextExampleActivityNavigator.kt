package pl.applover.android.mvvmtest.vvm.example.next_example

import io.reactivex.subjects.PublishSubject

/**
 * Created by Janusz Hain on 2018-06-11.
 */
class NextExampleActivityNavigator {

    val sender = Sender()
    val receiver = Receiver()

    class Sender {

    }

    class Receiver {
        val fragmentClicked: PublishSubject<String> = PublishSubject.create()
    }
}