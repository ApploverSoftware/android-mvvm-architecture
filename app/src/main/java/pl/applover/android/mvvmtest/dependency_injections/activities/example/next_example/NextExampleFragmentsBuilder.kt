package pl.applover.android.mvvmtest.dependency_injections.activities.example.next_example

import dagger.Module
import dagger.android.ContributesAndroidInjector
import pl.applover.android.mvvmtest.dependency_injections.fragments.example.ExampleListFragmentModule
import pl.applover.android.mvvmtest.util.architecture.dependency_injection.FragmentScope
import pl.applover.android.mvvmtest.vvm.example.next_example.example_list.ExampleListFragment

/**
 * Created by Janusz Hain on 2018-06-06.
 */
@Module
abstract class NextExampleFragmentsBuilder {

    @ContributesAndroidInjector(modules = [ExampleListFragmentModule::class])
    @FragmentScope
    abstract fun bindExampleListFragment(): ExampleListFragment
}