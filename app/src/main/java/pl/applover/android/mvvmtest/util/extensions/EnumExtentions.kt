package pl.applover.android.mvvmtest.util.extensions

/**
 * Created by Janusz Hain on 2018-08-10.
 */
inline fun <reified E : Enum<E>> findEnum(condition: (E) -> Boolean) =
        enumValues<E>().find(condition)

inline fun <reified E : Enum<E>> findEnum(condition: (E) -> Boolean, default: E) =
        enumValues<E>().find(condition) ?: default
