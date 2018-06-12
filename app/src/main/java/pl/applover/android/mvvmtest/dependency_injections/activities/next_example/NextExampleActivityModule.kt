package pl.applover.android.mvvmtest.dependency_injections.activities.next_example

import dagger.Module
import dagger.Provides
import pl.applover.android.mvvmtest.util.architecture.dependency_injection.ActivityScope
import pl.applover.android.mvvmtest.vvm.example.next_example.NextExampleActivityNavigator
import pl.applover.android.mvvmtest.vvm.example.next_example.NextExampleRouter
import pl.applover.android.mvvmtest.vvm.example.next_example.NextExampleViewModelFactory
import pl.applover.android.mvvmtest.vvm.example.next_example.example_list.ExampleListFragmentNavigator

/**
 * Created by Janusz Hain on 2018-06-06.
 */
@Module(includes = [NextExampleActivityModule.NavigatorsModule::class])
class NextExampleActivityModule {

    @Module
    class NavigatorsModule {
        @Provides
        @ActivityScope
        fun provideNextExampleActivityNavigator() = NextExampleActivityNavigator()

        @Provides
        @ActivityScope
        fun provideExampleListFragmentNavigator() = ExampleListFragmentNavigator()
    }

    @Provides
    @ActivityScope
    fun provideRouter(nextExampleActivityNavigator: NextExampleActivityNavigator, exampleListFragmentNavigator: ExampleListFragmentNavigator) = NextExampleRouter(nextExampleActivityNavigator, exampleListFragmentNavigator)


    @Provides
    @ActivityScope
    fun provideViewModelFactory(nextExampleRouter: NextExampleRouter, nextExampleActivityNavigator: NextExampleActivityNavigator) = NextExampleViewModelFactory(nextExampleRouter, nextExampleActivityNavigator)

}