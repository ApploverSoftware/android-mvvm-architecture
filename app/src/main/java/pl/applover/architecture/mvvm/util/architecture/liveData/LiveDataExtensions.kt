package pl.applover.architecture.mvvm.util.architecture.liveData

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer

/**
 * Created by Janusz Hain on 2018-07-13.
 */

/**
 * @param liveData live data that has to pass it's values further
 * @param lifecycleOwner for [LiveData.removeObservers] and [LiveData.observe]
 */
fun <T> MutableLiveData<T>.switchMap(liveData: LiveData<T>, lifecycleOwner: LifecycleOwner) {
    liveData.removeObservers(lifecycleOwner)
    liveData.observe(lifecycleOwner, Observer {
        this.value = it
    })
}

/**
 * @param liveData live data that has to pass it's values further
 * @param observer previous observer returned from this function.
 * Needed to call [LiveData.removeObserver]. Initial observer is null
 *
 * @return new observer that can be removed from [liveData]
 */
fun <T> MutableLiveData<T>.switchMap(liveData: LiveData<T>, observer: Observer<T>?): Observer<T> {
    observer?.let {
        liveData.removeObserver(observer)
    }
    val newObserver = Observer<T> {
        this.value = it
    }
    liveData.observeForever(newObserver)
    return newObserver
}