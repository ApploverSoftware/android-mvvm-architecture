package pl.applover.android.mvvmtest.dependency_injections.activities.modules

import dagger.Module
import dagger.Provides
import pl.applover.android.mvvmtest.vvm.example.next_example.NextExampleViewModel

/**
 * Created by Janusz Hain on 2018-06-06.
 */
@Module
class NextExampleActivityModule {

    @Provides
    fun provideViewModel() = NextExampleViewModel()
}