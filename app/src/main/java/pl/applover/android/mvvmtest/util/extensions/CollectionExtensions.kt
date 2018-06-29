package pl.applover.android.mvvmtest.util.extensions

import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by Janusz Hain on 2018-01-08.
 */

/**
 * Example of use:
 *
 *  restaurant.meals!!.values.iterator().removeAll({ meal ->
 * meal.ownerId == User.current!!.id
 * || meal.acceptedUser != null
 * || meal.rejectedUsers.contains(User.current!!.id)
 * || meal.requestedUsers.containsKey(User.current!!.id)
 * })
 */
fun <T : Any> MutableIterator<T>.removeAll(condition: (value: T) -> Boolean) {
    while (hasNext()) {
        val value = next()
        if (condition(value)) {
            remove()
        }
    }
}

/**
 * @return Object [V] under [key] if exists or null if put successful
 */
fun <K : Any, V : Any> HashMap<K, V>.putIfNotExists(key: K, value: V): V? {
    val valueUnderKey = get(key)
    if (valueUnderKey == null) {
        put(key, value)
        return null
    } else {
        return valueUnderKey
    }
}

/**
 * Removes last n elements from list
 */
fun <T> MutableList<T>.removeLastItems(n: Int): List<T> {
    val sublist = subList(maxOf(0, size - n), maxOf(0, size))
    val arrayList = ArrayList(sublist)
    sublist.clear()
    return arrayList
}
