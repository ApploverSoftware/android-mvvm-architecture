package pl.applover.android.mvvmtest.vvm.example.next_example.example_list

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import javax.inject.Inject

/**
 * Created by Janusz Hain on 2018-06-06.
 */
class ExampleListViewModelFactory @Inject constructor(val exampleListFragmentNavigator: ExampleListFragmentNavigator) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ExampleListViewModel(exampleListFragmentNavigator) as T
    }
}