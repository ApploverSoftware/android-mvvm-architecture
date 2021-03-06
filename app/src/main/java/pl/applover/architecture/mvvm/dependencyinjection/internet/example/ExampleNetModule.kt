package pl.applover.architecture.mvvm.dependencyinjection.internet.example

import dagger.Module
import dagger.Provides
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import pl.applover.architecture.mvvm.BuildConfig
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton


@Module
class ExampleNetModule {
    private var webServiceUrl = "http://my-json-server.typicode.com/" //JanuszHain/MyJsonServerCities/cities/
    private val timeoutInSec = 20


    @Provides
    @Named("example")
    @Singleton
    internal fun provideRetrofit(): Retrofit {
        val logging = HttpLoggingInterceptor()
        logging.level = if (BuildConfig.DEBUG)
            HttpLoggingInterceptor.Level.BODY
        else
            HttpLoggingInterceptor.Level.NONE


        val certificatePinner = CertificatePinner.Builder()
                //.add("my-json-server.typicode.com", "sha256/Vuy2zjFSPqF5Hz18k88DpUViKGbABaF3vZx5Raghplc=")
                .build()


        val okHttpClient = OkHttpClient.Builder()
                .addNetworkInterceptor(logging)
                .addInterceptor(logging)
                //.certificatePinner(certificatePinner)
                .writeTimeout(timeoutInSec.toLong(), TimeUnit.SECONDS)
                .readTimeout(timeoutInSec.toLong(), TimeUnit.SECONDS)
                .build()

        return Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(MoshiConverterFactory.create())
                .baseUrl(webServiceUrl)
                .client(okHttpClient)
                .build()
    }
}
