package pl.applover.android.mvvmtest.vvm.example.next_example

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import pl.applover.android.mvvmtest.util.architecture.Event

/**
 * Created by Janusz Hain on 2018-06-06.
 */
class NextExampleViewModel : ViewModel() {

    private val compositeDisposable by lazy { CompositeDisposable() }

    val someEvent = MutableLiveData<Event<String>>()
    val title = MutableLiveData<String>()

    fun activityOnResume(){
        someEvent.value = Event("Next Activity on Resume!")
        title.value = "Next Activity resumed"
    }

    override fun onCleared() {
        super.onCleared()
        println("OnCleared")
        compositeDisposable.clear()
    }
}