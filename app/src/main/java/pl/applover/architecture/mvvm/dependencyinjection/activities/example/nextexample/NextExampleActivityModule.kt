package pl.applover.architecture.mvvm.dependencyinjection.activities.example.nextexample

import dagger.Module
import dagger.Provides
import pl.applover.architecture.mvvm.util.architecture.dependencyinjection.ActivityScope
import pl.applover.architecture.mvvm.util.other.SchedulerProvider
import pl.applover.architecture.mvvm.vvm.example.nextexample.NextExampleActivityRouter
import pl.applover.architecture.mvvm.vvm.example.nextexample.NextExampleViewModelFactory
import pl.applover.architecture.mvvm.vvm.example.nextexample.examplelist.ExampleListFragmentRouter
import pl.applover.architecture.mvvm.vvm.example.nextexample.examplepagedList.ExamplePagedListFragmentRouter

/**
 * Created by Janusz Hain on 2018-06-06.
 */
@Module(includes = [NextExampleActivityModule.ChildRoutersModule::class])
class NextExampleActivityModule {

    /**
     * Module with all routers that belong to activity router
     */
    @Module
    class ChildRoutersModule {

        @Provides
        @ActivityScope
        fun provideExampleListFragmentRouter() = ExampleListFragmentRouter()

        @Provides
        @ActivityScope
        fun provideExamplePagedListFragmentRouter() = ExamplePagedListFragmentRouter()
    }

    @Provides
    @ActivityScope
    fun provideRouter(exampleListFragmentRouter: ExampleListFragmentRouter,
                      examplePagedListFragmentRouter: ExamplePagedListFragmentRouter) =
            NextExampleActivityRouter(exampleListFragmentRouter, examplePagedListFragmentRouter)


    @Provides
    @ActivityScope
    fun provideViewModelFactory(router: NextExampleActivityRouter,
                                schedulerProvider: SchedulerProvider) =
            NextExampleViewModelFactory(router, schedulerProvider)

}