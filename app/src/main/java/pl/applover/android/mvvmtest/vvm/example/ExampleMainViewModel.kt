package pl.applover.android.mvvmtest.vvm.example

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import pl.applover.android.mvvmtest.util.architecture.SingleEvent

/**
 * Created by Janusz Hain on 2018-06-06.
 */
class ExampleMainViewModel : ViewModel() {

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
    }
}