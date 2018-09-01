package pl.applover.architecture.mvvm.dependencyinjection.activities.example.nextexample

import dagger.Module
import dagger.android.ContributesAndroidInjector
import pl.applover.architecture.mvvm.dependencyinjection.fragments.example.ExampleListFragmentModule
import pl.applover.architecture.mvvm.dependencyinjection.fragments.example.ExamplePagedListFragmentModule
import pl.applover.architecture.mvvm.util.architecture.dependencyinjection.FragmentScope
import pl.applover.architecture.mvvm.vvm.example.nextexample.examplelist.ExampleListFragment
import pl.applover.architecture.mvvm.vvm.example.nextexample.examplepagedList.ExamplePagedListFragment

/**
 * Created by Janusz Hain on 2018-06-06.
 */
@Module
abstract class NextExampleFragmentsBuilder {

    @ContributesAndroidInjector(modules = [ExampleListFragmentModule::class])
    @FragmentScope
    abstract fun bindExampleListFragment(): ExampleListFragment

    @ContributesAndroidInjector(modules = [ExamplePagedListFragmentModule::class])
    @FragmentScope
    abstract fun bindExamplePagedListFragment(): ExamplePagedListFragment
}