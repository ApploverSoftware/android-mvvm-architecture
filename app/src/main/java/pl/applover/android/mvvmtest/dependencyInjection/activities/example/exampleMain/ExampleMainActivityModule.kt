package pl.applover.android.mvvmtest.dependencyInjection.activities.example.exampleMain

import dagger.Module
import dagger.Provides
import pl.applover.android.mvvmtest.vvm.example.main_example.ExampleMainViewModelFactory

/**
 * Created by Janusz Hain on 2018-06-06.
 */
@Module
class ExampleMainActivityModule {

    @Provides
    fun provideViewModelFactory() = ExampleMainViewModelFactory()
}