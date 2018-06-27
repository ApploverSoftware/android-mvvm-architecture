package pl.applover.android.mvvmtest.vvm.example.nextExample

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import pl.applover.android.mvvmtest.util.other.SchedulerProvider
import javax.inject.Inject

/**
 * Created by Janusz Hain on 2018-06-06.
 */
class NextExampleViewModelFactory @Inject constructor(private val router: NextExampleActivityRouter,
                                                      private val schedulerProvider: SchedulerProvider) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return NextExampleViewModel(router, schedulerProvider) as T
    }
}