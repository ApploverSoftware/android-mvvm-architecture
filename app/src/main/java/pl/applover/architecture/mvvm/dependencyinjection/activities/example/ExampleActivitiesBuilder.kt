package pl.applover.architecture.mvvm.dependencyinjection.activities.example

import dagger.Module
import dagger.android.ContributesAndroidInjector
import pl.applover.architecture.mvvm.dependencyinjection.activities.example.examplemain.ExampleMainActivityModule
import pl.applover.architecture.mvvm.dependencyinjection.activities.example.examplemain.ExampleMainFragmentsBuilder
import pl.applover.architecture.mvvm.dependencyinjection.activities.example.nextexample.NextExampleActivityModule
import pl.applover.architecture.mvvm.dependencyinjection.activities.example.nextexample.NextExampleFragmentsBuilder
import pl.applover.architecture.mvvm.util.architecture.dependencyinjection.ActivityScope
import pl.applover.architecture.mvvm.vvm.example.mainexample.ExampleMainActivity
import pl.applover.architecture.mvvm.vvm.example.nextexample.NextExampleActivity

/**
 * Created by Janusz Hain on 2018-06-06.
 */

/**
 * Binding for all example activities
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