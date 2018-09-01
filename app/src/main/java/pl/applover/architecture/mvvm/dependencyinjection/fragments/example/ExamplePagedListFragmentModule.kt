package pl.applover.architecture.mvvm.dependencyinjection.fragments.example

import dagger.Module
import dagger.Provides
import pl.applover.architecture.mvvm.data.example.repositories.ExampleCitiesRepository
import pl.applover.architecture.mvvm.util.other.SchedulerProvider
import pl.applover.architecture.mvvm.vvm.example.nextexample.examplepagedList.ExamplePagedListFragmentRouter
import pl.applover.architecture.mvvm.vvm.example.nextexample.examplepagedList.ExamplePagedListViewModelFactory

/**
 * Created by Janusz Hain on 2018-06-06.
 */
@Module
class ExamplePagedListFragmentModule {
    @Provides
    fun provideViewModelFactory(router: ExamplePagedListFragmentRouter,
                                schedulerProvider: SchedulerProvider,
                                repository: ExampleCitiesRepository) =
            ExamplePagedListViewModelFactory(router, schedulerProvider, repository)
}