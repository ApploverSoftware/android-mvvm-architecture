package pl.applover.android.mvvmtest.util.extensions

import android.util.Log

/**
 * Created by Janusz Hain on 2018-01-26.
 */
inline fun <reified T: Any> printError(thisObject: T, throwable: Throwable) {
    Log.e(T::class.java.simpleName, Log.getStackTraceString(throwable))
}