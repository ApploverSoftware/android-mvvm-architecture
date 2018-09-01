package pl.applover.architecture.mvvm.util.architecture.liveData

import android.arch.lifecycle.MutableLiveData
import android.databinding.ObservableList


/**
 * [MutableLiveData] for observing changes in [ObservableList]
 */
class ObservableListMutableLiveData<T : Any> : MutableLiveData<ObservableList<T>>() {

    override fun setValue(value: ObservableList<T>?) {
        super.setValue(value)

        //listen to property changes
        value!!.addOnListChangedCallback(callback)
    }

    private var callback = object : ObservableList.OnListChangedCallback<ObservableList<T>>() {
        override fun onItemRangeMoved(sender: ObservableList<T>?, fromPosition: Int, toPosition: Int, itemCount: Int) {
            value = value
        }

        override fun onItemRangeInserted(sender: ObservableList<T>?, positionStart: Int, itemCount: Int) {
            value = value
        }

        override fun onItemRangeChanged(sender: ObservableList<T>?, positionStart: Int, itemCount: Int) {
            value = value
        }

        override fun onItemRangeRemoved(sender: ObservableList<T>?, positionStart: Int, itemCount: Int) {
            value = value
        }

        override fun onChanged(sender: ObservableList<T>?) {
            value = value
        }

    }
}