package pl.applover.android.mvvmtest.vvm.example.next_example

import android.arch.lifecycle.MutableLiveData
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import pl.applover.android.mvvmtest.util.architecture.live_data.SingleEvent

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