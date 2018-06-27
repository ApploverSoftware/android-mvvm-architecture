package pl.applover.android.mvvmtest.dependencyInjection.activities.example.exampleMain

import dagger.Module
import dagger.Provides
import pl.applover.android.mvvmtest.util.architecture.dependencyInjection.ActivityScope
import pl.applover.android.mvvmtest.util.other.SchedulerProvider
import pl.applover.android.mvvmtest.vvm.example.mainExample.ExampleActivityRouter
import pl.applover.android.mvvmtest.vvm.example.mainExample.ExampleMainViewModelFactory

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