package pl.applover.android.mvvmtest.vvm.example.next_example

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import pl.applover.android.mvvmtest.util.architecture.live_data.Event
import pl.applover.android.mvvmtest.util.architecture.live_data.SingleEvent

/**
 * Created by Janusz Hain on 2018-06-06.
 */
class NextExampleViewModel(val router: NextExampleRouter) : ViewModel() {

    private val compositeDisposable by lazy { CompositeDisposable() }

    val someEvent = MutableLiveData<Event<String>>()
    val title = MutableLiveData<String>()

    init {
        println("Navigator in activity: $router")
        setNavigatorObservers()
    }

    private fun setNavigatorObservers() {
        router.exampleListFragmentNavigator.sender.fragmentClickedLiveData.observeForever { someClick: SingleEvent<String>? ->
            someClick?.getContentIfNotHandled(this)?.let { println("Navigator clicked event: " + it) }
        }
    }

    fun activityOnResume() {
        someEvent.value = Event("Next Activity on Resume!")
        title.value = "Next Activity resumed"
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
        compositeDisposable.clear()
    }
}