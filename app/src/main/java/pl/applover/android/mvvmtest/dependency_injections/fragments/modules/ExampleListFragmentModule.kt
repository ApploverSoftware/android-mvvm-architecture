package pl.applover.android.mvvmtest.dependency_injections.fragments.modules

import dagger.Module
import dagger.Provides
import pl.applover.android.mvvmtest.vvm.example.next_example.NextExampleNavigator
import pl.applover.android.mvvmtest.vvm.example.next_example.example_list.ExampleListFragmentNavigator
import pl.applover.android.mvvmtest.vvm.example.next_example.example_list.ExampleListViewModelFactory

/**
 * Created by Janusz Hain on 2018-06-06.
 */
@Module
class ExampleListFragmentModule {

    @Provides
    fun provideNavigator(nextExampleNavigator: NextExampleNavigator) = nextExampleNavigator as ExampleListFragmentNavigator

    @Provides
    fun provideViewModelFactory(exampleListFragmentNavigator: ExampleListFragmentNavigator) = ExampleListViewModelFactory(exampleListFragmentNavigator)
}