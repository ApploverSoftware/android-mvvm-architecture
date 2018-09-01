package pl.applover.architecture.mvvm.vvm.example.mainexample

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import pl.applover.architecture.mvvm.util.other.SchedulerProvider
import javax.inject.Inject

/**
 * Created by Janusz Hain on 2018-06-06.
 */
class ExampleMainViewModelFactory @Inject constructor(private val router: ExampleActivityRouter,
                                                      private val schedulerProvider: SchedulerProvider) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ExampleMainViewModel(router, schedulerProvider) as T
    }
}