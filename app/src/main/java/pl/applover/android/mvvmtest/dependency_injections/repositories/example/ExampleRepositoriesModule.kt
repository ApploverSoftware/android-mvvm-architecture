package pl.applover.android.mvvmtest.dependency_injections.repositories.example

import dagger.Module
import dagger.Provides
import pl.applover.android.mvvmtest.data.example.internet.api_endpoints.ExampleCitiesApiEndpointsInterface
import pl.applover.android.mvvmtest.data.example.repositories.ExampleCitiesRepository
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton


@Module
class ExampleRepositoriesModule {
    @Provides
    @Singleton
    fun provideExampleCitiesApiEndpointsInterface(@Named("example") retrofit: Retrofit): ExampleCitiesApiEndpointsInterface = retrofit.create(ExampleCitiesApiEndpointsInterface::class.java)

    @Provides
    @Singleton
    fun provideExampleCitiesRepository(exampleCitiesApiEndpointsInterface: ExampleCitiesApiEndpointsInterface): ExampleCitiesRepository = ExampleCitiesRepository(exampleCitiesApiEndpointsInterface)
}
