package pl.applover.android.mvvmtest.vvm.example.next_example.example_list

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import pl.applover.android.mvvmtest.util.architecture.Event

/**
 * Created by Janusz Hain on 2018-06-06.
 */
class ExampleListViewModel(val navigator: ExampleListFragmentNavigator) : ViewModel() {

    private val compositeDisposable by lazy { CompositeDisposable() }

    val someToast = MutableLiveData<Event<String>>()

    fun showSomeToast() {
        someToast.value = Event("someToast")
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}