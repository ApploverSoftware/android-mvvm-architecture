package pl.applover.android.mvvmtest.dependencyInjection.activities.example.nextExample

import dagger.Module
import dagger.android.ContributesAndroidInjector
import pl.applover.android.mvvmtest.dependencyInjection.fragments.example.ExampleListFragmentModule
import pl.applover.android.mvvmtest.util.architecture.dependencyInjection.FragmentScope
import pl.applover.android.mvvmtest.vvm.example.nextExample.exampleList.ExampleListFragment

/**
 * Created by Janusz Hain on 2018-06-06.
 */
@Module
abstract class NextExampleFragmentsBuilder {

    @ContributesAndroidInjector(modules = [ExampleListFragmentModule::class])
    @FragmentScope
    abstract fun bindExampleListFragment(): ExampleListFragment
}