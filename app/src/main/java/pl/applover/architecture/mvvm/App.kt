package pl.applover.architecture.mvvm

import android.app.Activity
import android.app.Application
import com.squareup.leakcanary.LeakCanary
import com.squareup.leakcanary.RefWatcher
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import pl.applover.architecture.mvvm.dependencyinjection.app.components.DaggerAppComponent
import pl.applover.architecture.mvvm.util.extensions.DelegatesExt
import timber.log.Timber
import javax.inject.Inject


/**
 * Created by Janusz Hain on 2018-01-08.
 */
class App : Application(), HasActivityInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun activityInjector(): DispatchingAndroidInjector<Activity> {
        return dispatchingAndroidInjector
    }

    override fun onCreate() {
        super.onCreate()

        initCanaryLeak()

        instance = this

        DaggerAppComponent
                .builder()
                .application(this)
                .build()
                .inject(this)

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    private fun initCanaryLeak() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return
        }
        refWatcher = LeakCanary.install(this);
    }

    companion object {
        var instance: App by DelegatesExt.notNullSingleValue()
        var refWatcher: RefWatcher by DelegatesExt.notNullSingleValue()
    }
}