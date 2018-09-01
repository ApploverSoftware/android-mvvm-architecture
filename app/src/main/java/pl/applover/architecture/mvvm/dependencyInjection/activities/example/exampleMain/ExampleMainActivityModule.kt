package pl.applover.architecture.mvvm.dependencyInjection.activities.example.exampleMain

import dagger.Module
import dagger.Provides
import pl.applover.architecture.mvvm.util.architecture.dependencyInjection.ActivityScope
import pl.applover.architecture.mvvm.util.other.SchedulerProvider
import pl.applover.architecture.mvvm.vvm.example.mainExample.ExampleActivityRouter
import pl.applover.architecture.mvvm.vvm.example.mainExample.ExampleMainViewModelFactory

/**
 * Created by Janusz Hain on 2018-06-06.
 */
@Module(includes = [ExampleMainActivityModule.ChildRoutersModule::class])
class ExampleMainActivityModule {

    /**
     * Module with all routers that belong to activity router
     */
    @Module
    class ChildRoutersModule {

    }

    @Provides
    @ActivityScope
    fun provideRouter() = ExampleActivityRouter()

    @Provides
    fun provideViewModelFactory(router: ExampleActivityRouter,
                                schedulerProvider: SchedulerProvider) =
            ExampleMainViewModelFactory(router, schedulerProvider)

}