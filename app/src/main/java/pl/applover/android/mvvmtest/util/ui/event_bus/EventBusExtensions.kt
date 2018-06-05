package pl.applover.android.mvvmtest.util.ui.event_bus

import org.greenrobot.eventbus.EventBus

/**
 * Created by Janusz Hain on 2018-06-05.
 */

//todo test
fun EventBus.post(event: Any, noSubscriber: () -> Void) {
    if (hasSubscriberForEvent(event.javaClass::class.java)) {
        post(event)
    } else {
        noSubscriber()
    }
}

fun EventBus.postSticky(event: Any, noSubscriber: () -> Void) {
    if (hasSubscriberForEvent(event.javaClass::class.java)) {
        postSticky(event)
    } else {
        noSubscriber()
    }
}