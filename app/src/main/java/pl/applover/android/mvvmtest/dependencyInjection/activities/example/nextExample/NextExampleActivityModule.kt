package pl.applover.android.mvvmtest.dependencyInjection.activities.example.nextExample

import dagger.Module
import dagger.Provides
import pl.applover.android.mvvmtest.util.architecture.dependencyInjection.ActivityScope
import pl.applover.android.mvvmtest.vvm.example.nextExample.NextExampleActivityRouter
import pl.applover.android.mvvmtest.vvm.example.nextExample.NextExampleViewModelFactory
import pl.applover.android.mvvmtest.vvm.example.nextExample.exampleList.ExampleListFragmentRouter

/**
 * Created by Janusz Hain on 2018-06-06.
 */
@Module(includes = [NextExampleActivityModule.RoutersModule::class])
class NextExampleActivityModule {

    /**
     * Module with all routers that belong to activity router
     */
    @Module
    class RoutersModule {

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