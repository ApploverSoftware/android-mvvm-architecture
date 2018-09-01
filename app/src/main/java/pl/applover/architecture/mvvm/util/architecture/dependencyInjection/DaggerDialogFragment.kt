package pl.applover.architecture.mvvm.util.architecture.dependencyInjection

import android.content.Context
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

/**
 * Created by Janusz Hain on 2018-01-16.
 */
abstract class DaggerDialogFragment : DialogFragment(), HasSupportFragmentInjector {

    @Inject
    lateinit var childFragmentInjector: DispatchingAndroidInjector<Fragment>

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun supportFragmentInjector(): DispatchingAndroidInjector<Fragment>? {
        return childFragmentInjector
    }
}
