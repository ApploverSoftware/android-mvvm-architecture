package pl.applover.android.mvvmtest.dependencyInjection.activities.example.nextExample

import dagger.Module
import dagger.android.ContributesAndroidInjector
import pl.applover.android.mvvmtest.dependencyInjection.fragments.example.ExampleListFragmentModule
import pl.applover.android.mvvmtest.dependencyInjection.fragments.example.ExamplePagedListFragmentModule
import pl.applover.android.mvvmtest.util.architecture.dependencyInjection.FragmentScope
import pl.applover.android.mvvmtest.vvm.example.nextExample.exampleList.ExampleListFragment
import pl.applover.android.mvvmtest.vvm.example.nextExample.examplePagedList.ExamplePagedListFragment

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