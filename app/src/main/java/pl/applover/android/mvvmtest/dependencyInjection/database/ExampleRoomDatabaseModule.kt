package pl.applover.android.mvvmtest.dependencyInjection.database

import android.app.Application
import android.arch.persistence.room.Room
import dagger.Module
import dagger.Provides
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import pl.applover.android.mvvmtest.BuildConfig
import pl.applover.android.mvvmtest.data.example.database.ExampleRoomDatabase
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton


@Module
class ExampleRoomDatabaseModule {
    @Provides
    @Singleton
    fun provideExampleRoomDatabase(application: Application): ExampleRoomDatabase = Room.databaseBuilder(application,
            ExampleRoomDatabase::class.java, "example-room-database").build()
}
