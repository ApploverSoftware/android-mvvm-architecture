package pl.applover.android.mvvmtest.dependencyInjection.app.components

import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import pl.applover.android.mvvmtest.App
import pl.applover.android.mvvmtest.dependencyInjection.activities.example.ExampleActivitiesBuilder
import pl.applover.android.mvvmtest.dependencyInjection.app.modules.AppModule
import pl.applover.android.mvvmtest.dependencyInjection.database.ExampleRoomDatabaseModule
import pl.applover.android.mvvmtest.dependencyInjection.internet.example.ExampleNetModule
import pl.applover.android.mvvmtest.dependencyInjection.other.SchedulerProviderModule
import pl.applover.android.mvvmtest.dependencyInjection.repositories.example.ExampleRepositoriesModule
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