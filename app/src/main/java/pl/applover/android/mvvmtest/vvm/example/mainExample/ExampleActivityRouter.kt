package pl.applover.android.mvvmtest.vvm.example.mainExample

import io.reactivex.subjects.PublishSubject
import pl.applover.android.mvvmtest.util.architecture.rx.EmptyEvent
import pl.applover.android.mvvmtest.vvm.example.nextExample.exampleList.ExampleListFragmentRouter
import javax.inject.Inject

/**
 * Created by Janusz Hain on 2018-06-11.
 */
class ExampleActivityRouter @Inject constructor() {
    val sender = Sender()
    val receiver = Receiver()

    init {

    }

    class Sender {

    }

    class Receiver {

    }
}