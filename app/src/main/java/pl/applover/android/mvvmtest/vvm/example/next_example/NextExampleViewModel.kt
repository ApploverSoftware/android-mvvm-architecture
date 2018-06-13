package pl.applover.android.mvvmtest.vvm.example.next_example

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import pl.applover.android.mvvmtest.App
import pl.applover.android.mvvmtest.util.architecture.live_data.Event

/**
 * Created by Janusz Hain on 2018-06-06.
 */
class NextExampleViewModel(private val router: NextExampleActivityRouter) : ViewModel() {

    private val compositeDisposable by lazy { CompositeDisposable() }

    val someEvent = MutableLiveData<Event<String>>()
    val title = MutableLiveData<String>()

    init {
        println("Navigator in activity: $router")
        setNavigatorObservers()
    }

    private fun setNavigatorObservers() {
        compositeDisposable.add(router.receiver.fragmentClicked.subscribe({ println("Navigator clicked event") }))
    }

    fun activityOnResume() {
        someEvent.value = Event("Next Activity on Resume!")
        title.value = "Next Activity resumed"
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
        compositeDisposable.clear()
        watchForLeaks()
    }

    private fun watchForLeaks() {
        App.refWatcher.watch(this)
        App.refWatcher.watch(someEvent)
        App.refWatcher.watch(title)
        App.refWatcher.watch(router)
    }
}