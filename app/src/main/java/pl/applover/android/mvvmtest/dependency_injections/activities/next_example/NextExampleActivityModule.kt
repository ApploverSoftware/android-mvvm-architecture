package pl.applover.android.mvvmtest.dependency_injections.activities.next_example

import dagger.Module
import dagger.Provides
import pl.applover.android.mvvmtest.vvm.example.next_example.NextExampleNavigator
import pl.applover.android.mvvmtest.vvm.example.next_example.NextExampleViewModelFactory

/**
 * Created by Janusz Hain on 2018-06-06.
 */
@Module
class NextExampleActivityModule {

    @Provides
    fun provideNavigator() = NextExampleNavigator()

    @Provides
    fun provideViewModelFactory(nextExampleNavigator: NextExampleNavigator) = NextExampleViewModelFactory(nextExampleNavigator)

}