package pl.applover.android.mvvmtest.dependency_injections.activities.modules

import dagger.Module
import dagger.Provides
import pl.applover.android.mvvmtest.vvm.example.ExampleMainViewModel

/**
 * Created by Janusz Hain on 2018-06-06.
 */
@Module
class MainActivityModule {

    @Provides
    fun provideViewModel()
            = ExampleMainViewModel()
}