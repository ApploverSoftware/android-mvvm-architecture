package pl.applover.android.mvvmtest.dependencyInjection.activities.example.nextExample

import dagger.Module
import dagger.Provides
import pl.applover.android.mvvmtest.util.architecture.dependencyInjection.ActivityScope
import pl.applover.android.mvvmtest.util.other.SchedulerProvider
import pl.applover.android.mvvmtest.vvm.example.nextExample.NextExampleActivityRouter
import pl.applover.android.mvvmtest.vvm.example.nextExample.NextExampleViewModelFactory
import pl.applover.android.mvvmtest.vvm.example.nextExample.exampleList.ExampleListFragmentRouter
import pl.applover.android.mvvmtest.vvm.example.nextExample.examplePagedList.ExamplePagedListFragmentRouter

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