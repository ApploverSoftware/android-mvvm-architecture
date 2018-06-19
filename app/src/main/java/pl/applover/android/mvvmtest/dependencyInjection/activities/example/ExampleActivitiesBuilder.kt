package pl.applover.android.mvvmtest.dependencyInjection.activities.example

import dagger.Module
import dagger.android.ContributesAndroidInjector
import pl.applover.android.mvvmtest.dependencyInjection.activities.example.exampleMain.ExampleMainActivityModule
import pl.applover.android.mvvmtest.dependencyInjection.activities.example.exampleMain.ExampleMainFragmentsBuilder
import pl.applover.android.mvvmtest.dependencyInjection.activities.example.nextExample.NextExampleActivityModule
import pl.applover.android.mvvmtest.dependencyInjection.activities.example.nextExample.NextExampleFragmentsBuilder
import pl.applover.android.mvvmtest.util.architecture.dependencyInjection.ActivityScope
import pl.applover.android.mvvmtest.vvm.example.main_example.ExampleMainActivity
import pl.applover.android.mvvmtest.vvm.example.next_example.NextExampleActivity

/**
 * Created by Janusz Hain on 2018-06-06.
 */
@Module
abstract class ExampleActivitiesBuilder {

    @ContributesAndroidInjector(modules = [ExampleMainActivityModule::class, ExampleMainFragmentsBuilder::class])
    @ActivityScope
    abstract fun bindExampleMainActivity(): ExampleMainActivity

    @ContributesAndroidInjector(modules = [NextExampleActivityModule::class, NextExampleFragmentsBuilder::class])
    @ActivityScope
    abstract fun bindNextExampleActivity(): NextExampleActivity
}