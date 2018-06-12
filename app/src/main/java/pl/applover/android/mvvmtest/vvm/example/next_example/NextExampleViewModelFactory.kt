package pl.applover.android.mvvmtest.vvm.example.next_example

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import javax.inject.Inject

/**
 * Created by Janusz Hain on 2018-06-06.
 */
class NextExampleViewModelFactory @Inject constructor(private val router: NextExampleRouter, private val navigator: NextExampleActivityNavigator) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return NextExampleViewModel(router, navigator) as T
    }
}