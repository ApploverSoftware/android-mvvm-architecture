package pl.applover.android.mvvmtest.util.ui

import android.view.View
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import pl.applover.android.mvvmtest.util.other.MyScheduler
import java.util.concurrent.TimeUnit


/**
 * Created by Janusz Hain on 2017-07-26.
 *
 * Use it before calling [View.setVisibility] to [View.VISIBLE]
 * Not thread-safe!
 */
class OnViewTurnedVisibleListener() {

    private var disposable: Disposable? = null

    /**
     * Use it before calling [View.setVisibility] to [View.VISIBLE]
     * Not thread-safe!
     */
    fun startListening(view: View, listener: OnViewTurnVisibleCallback) {
        stopListening()
        disposable = Observable.timer(50, TimeUnit.MILLISECONDS).subscribeOn(MyScheduler.getMainThreadScheduler()).subscribe {
            if (!view.getRect().isEmpty) {
                disposable?.dispose()
                listener.onViewTurnedVisible()
            }
        }

    }

    fun stopListening() {
        disposable?.dispose()
    }

    interface OnViewTurnVisibleCallback {
        fun onViewTurnedVisible()
    }

}