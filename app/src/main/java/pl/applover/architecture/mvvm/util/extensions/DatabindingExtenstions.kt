package pl.applover.architecture.mvvm.util.extensions

import android.databinding.ObservableArrayList
import android.databinding.ObservableList

/**
 * Created by Janusz Hain on 2018-07-09.
 */

fun <E : Any> Collection<E>.toObservableArrayList(): ObservableArrayList<E> {
    val observableArrayList = ObservableArrayList<E>()
    observableArrayList.addAll(this)
    return observableArrayList
}

fun <E : Any> Collection<E>.toObservableList(): ObservableList<E> {
    val observableArrayList = ObservableArrayList<E>()
    observableArrayList.addAll(this)
    return observableArrayList
}
