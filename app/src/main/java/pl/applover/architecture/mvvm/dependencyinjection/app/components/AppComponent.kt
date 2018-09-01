package pl.applover.architecture.mvvm.dependencyinjection.app.components

import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import pl.applover.architecture.mvvm.App
import pl.applover.architecture.mvvm.dependencyinjection.activities.example.ExampleActivitiesBuilder
import pl.applover.architecture.mvvm.dependencyinjection.app.modules.AppModule
import pl.applover.architecture.mvvm.dependencyinjection.database.ExampleRoomDatabaseModule
import pl.applover.architecture.mvvm.dependencyinjection.internet.example.ExampleNetModule
import pl.applover.architecture.mvvm.dependencyinjection.other.SchedulerProviderModule
import pl.applover.architecture.mvvm.dependencyinjection.repositories.example.ExampleRepositoriesModule
import javax.inject.Singleton

/**
 * Created by Janusz Hain on 2018-01-08.
 */
@Singleton
@Component(modules = [AndroidSupportInjectionModule::class, AppModule::class, SchedulerProviderModule::class, //those models are required
    ExampleActivitiesBuilder::class, ExampleNetModule::class,
    ExampleRoomDatabaseModule::class, ExampleRepositoriesModule::class])
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: App): Builder

        fun build(): AppComponent
    }

    fun inject(app: App)

}