package pl.applover.android.mvvmtest.vvm.example

import android.arch.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by Janusz Hain on 2018-06-06.
 */
class ExampleMainViewModel : ViewModel() {

    private val compositeDisposable by lazy { CompositeDisposable() }

}