package pl.applover.android.mvvmtest.util.architecture.rx

/**
 * Created by Janusz Hain on 2018-06-13.
 */

/**
 * We can't pass nulls in RxJava, but we can't send empty object indicating that we don't send anything important
 */
open class EmptyEvent