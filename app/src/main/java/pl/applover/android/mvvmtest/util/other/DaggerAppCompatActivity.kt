package pl.applover.android.mvvmtest.util.other

import android.app.Fragment
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasFragmentInjector
import javax.inject.Inject


/**
 * Created by Janusz Hain on 2018-06-06.
 */
abstract class DaggerAppCompatActivity : AppCompatActivity(), HasFragmentInjector {
    @Inject
    internal lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

    override fun fragmentInjector(): AndroidInjector<Fragment> {
        return fragmentInjector
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
    }
}