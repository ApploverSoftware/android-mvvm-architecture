package pl.applover.architecture.mvvm.dependencyInjection.fragments.example

import dagger.Module
import dagger.Provides
import pl.applover.architecture.mvvm.data.example.repositories.ExampleCitiesRepository
import pl.applover.architecture.mvvm.util.other.SchedulerProvider
import pl.applover.architecture.mvvm.vvm.example.nextExample.examplePagedList.ExamplePagedListFragmentRouter
import pl.applover.architecture.mvvm.vvm.example.nextExample.examplePagedList.ExamplePagedListViewModelFactory

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