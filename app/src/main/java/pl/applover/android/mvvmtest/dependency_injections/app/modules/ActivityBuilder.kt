package pl.applover.android.mvvmtest.dependency_injections.app.modules

import dagger.Module
import dagger.android.ContributesAndroidInjector
import pl.applover.android.mvvmtest.dependency_injections.activities.modules.MainActivityModule
import pl.applover.android.mvvmtest.vvm.example.ExampleMainActivity

/**
 * Created by Janusz Hain on 2018-06-06.
 */
@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = arrayOf(MainActivityModule::class))
    abstract fun bindMainActivity(): ExampleMainActivity
}