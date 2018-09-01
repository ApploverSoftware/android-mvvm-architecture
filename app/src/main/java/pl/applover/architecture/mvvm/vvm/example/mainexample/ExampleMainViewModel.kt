package pl.applover.architecture.mvvm.vvm.example.mainexample

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import pl.applover.architecture.mvvm.App
import pl.applover.architecture.mvvm.util.architecture.livedata.SingleEvent
import pl.applover.architecture.mvvm.util.other.SchedulerProvider

/**
 * Created by Janusz Hain on 2018-06-06.
 */
class ExampleMainViewModel(private val router: ExampleActivityRouter,
                           private val schedulerProvider: SchedulerProvider) : ViewModel() {

    private val compositeDisposable by lazy { CompositeDisposable() }

    val someEvent = MutableLiveData<SingleEvent<String>>()
    val title = MutableLiveData<String>()

    fun activityOnResume() {
        someEvent.value = SingleEvent("Activity on Resume!")
        title.value = "Activity resumed"
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
        watchForLeaks()
    }

    private fun watchForLeaks() {
        App.refWatcher.watch(compositeDisposable)
        App.refWatcher.watch(someEvent)
        App.refWatcher.watch(title)
        App.refWatcher.watch(this)
    }
}