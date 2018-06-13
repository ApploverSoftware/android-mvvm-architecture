package pl.applover.android.mvvmtest.vvm.example.next_example.example_list

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import pl.applover.android.mvvmtest.App
import pl.applover.android.mvvmtest.util.architecture.live_data.Event

/**
 * Created by Janusz Hain on 2018-06-06.
 */
class ExampleListViewModel(val router: ExampleListFragmentRouter) : ViewModel() {

    private val compositeDisposable by lazy { CompositeDisposable() }

    val someToast = MutableLiveData<Event<String>>()

    init {
        println("Navigator: $router")
    }

    fun showSomeToast() {
        someToast.value = Event("someToast")
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
        watchForLeaks()
    }


    private fun watchForLeaks() {
        App.refWatcher.watch(someToast)
        App.refWatcher.watch(compositeDisposable)
        App.refWatcher.watch(this)
        //we don't watch for router leak, as router has to stay alive, as it is router for activity scope
    }
}