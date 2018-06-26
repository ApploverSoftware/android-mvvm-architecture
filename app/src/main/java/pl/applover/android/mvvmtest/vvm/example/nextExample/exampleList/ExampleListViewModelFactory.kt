package pl.applover.android.mvvmtest.vvm.example.nextExample.exampleList

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import pl.applover.android.mvvmtest.data.example.repositories.ExampleCitiesRepository
import javax.inject.Inject

/**
 * Created by Janusz Hain on 2018-06-06.
 */
class ExampleListViewModelFactory @Inject constructor(private val router: ExampleListFragmentRouter, private val repository: ExampleCitiesRepository) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ExampleListViewModel(router, repository) as T
    }
}