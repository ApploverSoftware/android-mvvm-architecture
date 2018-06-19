package pl.applover.android.mvvmtest.vvm.example.nextExample.exampleList

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import pl.applover.android.mvvmtest.App
import pl.applover.android.mvvmtest.data.example.repositories.ExampleCitiesRepository
import pl.applover.android.mvvmtest.models.example.ExampleCityModel
import pl.applover.android.mvvmtest.util.architecture.liveData.Event
import pl.applover.android.mvvmtest.util.architecture.retrofit.MappedResponse
import pl.applover.android.mvvmtest.util.architecture.rx.EmptyEvent
import pl.applover.android.mvvmtest.util.other.MyScheduler

/**
 * Created by Janusz Hain on 2018-06-06.
 */
class ExampleListViewModel(private val router: ExampleListFragmentRouter, private val citiesRepository: ExampleCitiesRepository) : ViewModel() {

    private val compositeDisposable by lazy { CompositeDisposable() }

    val someToast = MutableLiveData<Event<String>>()

    fun showSomeToast() {
        someToast.value = Event("someToast")
    }

    fun fragmentClicked() {
        router.sender.fragmentClicked.onNext(EmptyEvent())
    }

    fun loadCities() {
        citiesRepository.citiesFromNetwork()
                .subscribeOn(MyScheduler.getScheduler())
                .observeOn(MyScheduler.getMainThreadScheduler())
                .subscribe { response: MappedResponse<List<ExampleCityModel>>?, throwable: Throwable? ->

                }
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