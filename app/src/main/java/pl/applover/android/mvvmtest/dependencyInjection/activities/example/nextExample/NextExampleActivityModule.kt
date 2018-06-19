package pl.applover.android.mvvmtest.dependencyInjection.activities.example.nextExample

import dagger.Module
import dagger.Provides
import pl.applover.android.mvvmtest.util.architecture.dependencyInjection.ActivityScope
import pl.applover.android.mvvmtest.vvm.example.next_example.NextExampleActivityRouter
import pl.applover.android.mvvmtest.vvm.example.next_example.NextExampleViewModelFactory
import pl.applover.android.mvvmtest.vvm.example.next_example.example_list.ExampleListFragmentRouter

/**
 * Created by Janusz Hain on 2018-06-06.
 */
@Module(includes = [NextExampleActivityModule.NavigatorsModule::class])
class NextExampleActivityModule {

    @Module
    class NavigatorsModule {

        @Provides
        @ActivityScope
        fun provideExampleListFragmentRouter() = ExampleListFragmentRouter()
    }

    @Provides
    @ActivityScope
    fun provideRouter(exampleListFragmentRouter: ExampleListFragmentRouter) = NextExampleActivityRouter(exampleListFragmentRouter)


    @Provides
    @ActivityScope
    fun provideViewModelFactory(router: NextExampleActivityRouter) = NextExampleViewModelFactory(router)

}