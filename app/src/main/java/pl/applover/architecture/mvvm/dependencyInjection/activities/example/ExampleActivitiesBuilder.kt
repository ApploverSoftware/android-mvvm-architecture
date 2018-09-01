package pl.applover.architecture.mvvm.dependencyInjection.activities.example

import dagger.Module
import dagger.android.ContributesAndroidInjector
import pl.applover.architecture.mvvm.dependencyInjection.activities.example.exampleMain.ExampleMainActivityModule
import pl.applover.architecture.mvvm.dependencyInjection.activities.example.exampleMain.ExampleMainFragmentsBuilder
import pl.applover.architecture.mvvm.dependencyInjection.activities.example.nextExample.NextExampleActivityModule
import pl.applover.architecture.mvvm.dependencyInjection.activities.example.nextExample.NextExampleFragmentsBuilder
import pl.applover.architecture.mvvm.util.architecture.dependencyInjection.ActivityScope
import pl.applover.architecture.mvvm.vvm.example.mainExample.ExampleMainActivity
import pl.applover.architecture.mvvm.vvm.example.nextExample.NextExampleActivity

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