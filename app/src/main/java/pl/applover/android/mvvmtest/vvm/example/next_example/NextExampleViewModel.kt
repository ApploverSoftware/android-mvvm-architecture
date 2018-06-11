package pl.applover.android.mvvmtest.vvm.example.next_example

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import pl.applover.android.mvvmtest.util.architecture.Event
import pl.applover.android.mvvmtest.util.architecture.SingleEvent

/**
 * Created by Janusz Hain on 2018-06-06.
 */
class NextExampleViewModel(val navigator: NextExampleNavigator) : ViewModel() {

    private val compositeDisposable by lazy { CompositeDisposable() }

    val someEvent = MutableLiveData<Event<String>>()
    val title = MutableLiveData<String>()

    init {
        println("Activity Navigator: $navigator")
        setNavigatorObservers()
    }

    private fun setNavigatorObservers() {
        navigator.fragmentClickedLiveData().observeForever { someClick: SingleEvent<String>? ->
            println("Navigator clicked event")
            someClick?.getContentIfNotHandled(this)?.let { println("SomeClick: " + it) }
        }
    }

    fun activityOnResume() {
        someEvent.value = Event("Next Activity on Resume!")
        title.value = "Next Activity resumed"
    }

    override fun onCleared() {
        super.onCleared()
        println("OnCleared")
        compositeDisposable.clear()
    }
}