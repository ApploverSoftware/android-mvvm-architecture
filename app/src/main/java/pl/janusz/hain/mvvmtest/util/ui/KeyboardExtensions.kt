package pl.applover.enegivetest.util.ui

import android.app.Activity
import android.content.Context
import android.support.v4.app.Fragment
import android.view.inputmethod.InputMethodManager
import pl.janusz.hain.mvvmtest.App


/**
 * Created by Janusz Hain on 2018-03-07.
 */

fun Fragment.hideKeyboard(context: Context = App.instance) {
    val windowToken = view?.rootView?.windowToken
    windowToken?.let {
        val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }
}