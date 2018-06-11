package pl.applover.android.mvvmtest.dependency_injections.activities.example_main

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