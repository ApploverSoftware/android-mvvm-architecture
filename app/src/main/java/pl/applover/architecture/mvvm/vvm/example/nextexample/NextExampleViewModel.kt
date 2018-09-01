package pl.applover.architecture.mvvm.vvm.example.nextexample

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import pl.applover.architecture.mvvm.App
import pl.applover.architecture.mvvm.util.architecture.livedata.SingleEvent
import pl.applover.architecture.mvvm.util.architecture.rx.EmptyEvent
import pl.applover.architecture.mvvm.util.other.SchedulerProvider
import timber.log.Timber

/**
 * Created by Janusz Hain on 2018-06-06.
 */
class NextExampleViewModel(private val router: NextExampleActivityRouter,
                           private val schedulerProvider: SchedulerProvider) : ViewModel() {

    private val compositeDisposable by lazy { CompositeDisposable() }

    val mldSomeEvent = MutableLiveData<SingleEvent<String>>()
    val mldTitle = MutableLiveData<String>()
    val mldFragmentClicked = MutableLiveData<SingleEvent<EmptyEvent>>()


    init {
        setRouterObservers()
    }

    private fun setRouterObservers() {
        compositeDisposable.add(router.receiver.fragmentClicked.subscribe {
            mldFragmentClicked.value = SingleEvent(EmptyEvent())
            Timber.i("Router sent event about click in some fragment!")
        })
    }

    fun activityOnResume() {
        mldSomeEvent.value = SingleEvent("Next Activity on Resume!")
        mldTitle.value = "Next Activity resumed"
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
        watchForLeaks()
    }

    private fun watchForLeaks() {
        App.refWatcher.watch(this)
        App.refWatcher.watch(mldSomeEvent)
        App.refWatcher.watch(mldTitle)
        App.refWatcher.watch(router)
    }
}