package pl.applover.architecture.mvvm.dependencyinjection.activities.example.examplemain

import dagger.Module
import dagger.Provides
import pl.applover.architecture.mvvm.util.architecture.dependencyinjection.ActivityScope
import pl.applover.architecture.mvvm.util.other.SchedulerProvider
import pl.applover.architecture.mvvm.vvm.example.mainexample.ExampleActivityRouter
import pl.applover.architecture.mvvm.vvm.example.mainexample.ExampleMainViewModelFactory

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