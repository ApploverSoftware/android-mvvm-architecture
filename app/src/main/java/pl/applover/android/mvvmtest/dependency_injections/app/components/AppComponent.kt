package pl.applover.android.mvvmtest.dependency_injections.app.components

import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import pl.applover.android.mvvmtest.App
import pl.applover.android.mvvmtest.dependency_injections.activities.example.ExampleActivitiesBuilder
import pl.applover.android.mvvmtest.dependency_injections.app.modules.AppModule
import pl.applover.android.mvvmtest.dependency_injections.internet.example.ExampleNetModule
import pl.applover.android.mvvmtest.dependency_injections.repositories.example.ExampleRepositoriesModule
import javax.inject.Singleton

/**
 * Created by Janusz Hain on 2018-01-08.
 */
@Singleton
@Component(modules = [AndroidSupportInjectionModule::class, AppModule::class, ExampleActivitiesBuilder::class, ExampleNetModule::class, ExampleRepositoriesModule::class])
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: App): Builder

        fun build(): AppComponent
    }

    fun inject(app: App)

}