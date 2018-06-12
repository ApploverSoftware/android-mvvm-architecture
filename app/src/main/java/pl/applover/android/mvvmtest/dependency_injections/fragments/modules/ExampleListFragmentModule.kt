package pl.applover.android.mvvmtest.dependency_injections.fragments.modules

import dagger.Module
import dagger.Provides
import pl.applover.android.mvvmtest.vvm.example.next_example.example_list.ExampleListFragmentRouter
import pl.applover.android.mvvmtest.vvm.example.next_example.example_list.ExampleListViewModelFactory

/**
 * Created by Janusz Hain on 2018-06-06.
 */
@Module
class ExampleListFragmentModule {
    @Provides
    fun provideViewModelFactory(exampleListFragmentRouter: ExampleListFragmentRouter) = ExampleListViewModelFactory(exampleListFragmentRouter)
}