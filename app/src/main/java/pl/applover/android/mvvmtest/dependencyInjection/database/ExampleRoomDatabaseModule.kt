package pl.applover.android.mvvmtest.dependencyInjection.database

import android.app.Application
import android.arch.persistence.room.Room
import dagger.Module
import dagger.Provides
import pl.applover.android.mvvmtest.data.example.database.ExampleRoomDatabase
import javax.inject.Singleton


@Module
class ExampleRoomDatabaseModule {
    @Provides
    @Singleton
    fun provideExampleRoomDatabase(application: Application): ExampleRoomDatabase = Room.databaseBuilder(application,
            ExampleRoomDatabase::class.java, "example-room-database").build()
}
