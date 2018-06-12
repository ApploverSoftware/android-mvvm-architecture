package pl.applover.android.mvvmtest.dependency_injections.activities.next_example

import dagger.Module
import dagger.Provides
import pl.applover.android.mvvmtest.util.architecture.dependency_injection.ActivityScope
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
        fun provideExampleListFragmentNavigator() = ExampleListFragmentNavigator()
    }

    @Provides
    @ActivityScope
    fun provideRouter(exampleListFragmentNavigator: ExampleListFragmentNavigator) = NextExampleRouter(exampleListFragmentNavigator)


    @Provides
    @ActivityScope
    fun provideViewModelFactory(nextExampleRouter: NextExampleRouter) = NextExampleViewModelFactory(nextExampleRouter)

}