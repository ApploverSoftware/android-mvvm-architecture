package pl.applover.android.mvvmtest.dependency_injections.activities

import dagger.Module
import dagger.android.ContributesAndroidInjector
import pl.applover.android.mvvmtest.dependency_injections.activities.example_main.ExampleMainActivityModule
import pl.applover.android.mvvmtest.dependency_injections.activities.example_main.ExampleMainFragmentsBuilder
import pl.applover.android.mvvmtest.dependency_injections.activities.next_example.NextExampleActivityModule
import pl.applover.android.mvvmtest.dependency_injections.activities.next_example.NextExampleFragmentsBuilder
import pl.applover.android.mvvmtest.vvm.example.main_example.ExampleMainActivity
import pl.applover.android.mvvmtest.vvm.example.next_example.NextExampleActivity

/**
 * Created by Janusz Hain on 2018-06-06.
 */
@Module
abstract class ActivitiesBuilder {

    @ContributesAndroidInjector(modules = arrayOf(ExampleMainActivityModule::class, ExampleMainFragmentsBuilder::class))
    abstract fun bindExampleMainActivity(): ExampleMainActivity

    @ContributesAndroidInjector(modules = arrayOf(NextExampleActivityModule::class, NextExampleFragmentsBuilder::class))
    abstract fun bindNextExampleActivity(): NextExampleActivity
}