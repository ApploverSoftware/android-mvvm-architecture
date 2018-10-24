package pl.applover.architecture.mvvm.util.ui

import android.content.Context
import android.support.v4.app.Fragment
import android.view.inputmethod.InputMethodManager
import pl.applover.architecture.mvvm.App


/**
 * Created by Janusz Hain on 2018-03-07.
 */

fun Fragment.hideKeyboard(context: Context = App.instance) {
    val windowToken = view?.rootView?.windowToken
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    windowToken?.let {
        imm.hideSoftInputFromWindow(windowToken, 0)
    } ?: run {
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS)
    }
}