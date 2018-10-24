package pl.applover.architecture.mvvm.util.ui

import android.content.Context
import android.support.v4.app.Fragment
import android.view.inputmethod.InputMethodManager
import pl.applover.architecture.mvvm.App
import timber.log.Timber


/**
 * Created by Janusz Hain on 2018-03-07.
 */

/**
 * If no window token is found, keyboard is checked using reflection to know if keyboard visibility toggle is needed
 *
 * @param useReflection - whether to use reflection in case of no window token or not
 */
fun Fragment.hideKeyboard(context: Context = App.instance, useReflection: Boolean = true) {
    val windowToken = view?.rootView?.windowToken
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    windowToken?.let {
        imm.hideSoftInputFromWindow(windowToken, 0)
    } ?: run {
        if (useReflection) {
            try {
                if (getKeyboardHeight(imm) > 0) {
                    imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS)
                }
            } catch (exception: Exception) {
                Timber.e(exception)
            }
        }
    }
}

fun getKeyboardHeight(imm: InputMethodManager): Int = InputMethodManager::class.java.getMethod("getInputMethodWindowVisibleHeight").invoke(imm) as Int