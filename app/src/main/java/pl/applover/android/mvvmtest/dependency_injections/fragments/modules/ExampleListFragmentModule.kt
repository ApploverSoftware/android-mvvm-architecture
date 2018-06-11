package pl.applover.android.mvvmtest.dependency_injections.fragments.modules

import dagger.Binds
import dagger.Module
import dagger.Provides
import pl.applover.android.mvvmtest.util.architecture.dependency_injection.ActivityScope
import pl.applover.android.mvvmtest.vvm.example.next_example.NextExampleNavigator
import pl.applover.android.mvvmtest.vvm.example.next_example.example_list.ExampleListFragmentNavigator
import pl.applover.android.mvvmtest.vvm.example.next_example.example_list.ExampleListViewModelFactory

/**
 * Created by Janusz Hain on 2018-06-06.
 */
@Module(includes = [ExampleListFragmentModule.Navigators::class])
class ExampleListFragmentModule {

    @Module
    interface Navigators {
        @Binds
        fun provideNavigator(nextExampleNavigator: NextExampleNavigator): ExampleListFragmentNavigator
    }

    @Provides
    fun provideViewModelFactory(exampleListFragmentNavigator: ExampleListFragmentNavigator) = ExampleListViewModelFactory(exampleListFragmentNavigator)
}