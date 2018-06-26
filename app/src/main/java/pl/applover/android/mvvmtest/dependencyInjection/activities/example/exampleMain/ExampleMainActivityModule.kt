package pl.applover.android.mvvmtest.dependencyInjection.activities.example.exampleMain

import dagger.Module
import dagger.Provides
import pl.applover.android.mvvmtest.util.architecture.dependencyInjection.ActivityScope
import pl.applover.android.mvvmtest.vvm.example.mainExample.ExampleActivityRouter
import pl.applover.android.mvvmtest.vvm.example.mainExample.ExampleMainViewModelFactory

/**
 * Created by Janusz Hain on 2018-06-06.
 */
@Module(includes = [ExampleMainActivityModule.RoutersModule::class])
class ExampleMainActivityModule {

    /**
     * Module with all routers that belong to activity router
     */
    @Module
    class RoutersModule {

    }

    @Provides
    @ActivityScope
    fun provideRouter() = ExampleActivityRouter()

    @Provides
    fun provideViewModelFactory(router: ExampleActivityRouter) = ExampleMainViewModelFactory(router)

}