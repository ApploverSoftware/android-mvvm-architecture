package pl.applover.android.mvvmtest.dependency_injections.fragments.modules

import dagger.Module
import dagger.Provides
import pl.applover.android.mvvmtest.util.architecture.dependency_injection.scopes.PerActivity
import pl.applover.android.mvvmtest.vvm.example.ExampleMainViewModelFactory

/**
 * Created by Janusz Hain on 2018-06-06.
 */
@Module
class ExampleListFragmentModule {

    @Provides
    @PerActivity
    fun provideViewModelFactory() = ExampleMainViewModelFactory()
}