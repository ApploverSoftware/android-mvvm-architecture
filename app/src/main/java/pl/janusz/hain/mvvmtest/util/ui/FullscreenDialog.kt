package pl.applover.enegivetest.util.ui

import android.os.Bundle
import android.support.v4.app.DialogFragment
import pl.janusz.hain.mvvmtest.R

/**
 * Created by Janusz Hain on 2018-01-08.
 */
abstract class FullscreenDialog : DialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(R.style.FullscreenDialog, R.style.FullscreenDialog)
    }

}