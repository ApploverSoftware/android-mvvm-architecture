package pl.applover.architecture.mvvm.util.architecture.liveData

import android.arch.lifecycle.MutableLiveData
import android.databinding.BaseObservable
import android.databinding.Observable


/**
 * MutableLiveData for observing changes in given object
 */
class ObservableMutableLiveData<T : BaseObservable> : MutableLiveData<T>() {
    override fun setValue(value: T?) {
        super.setValue(value)

        //listen to property changes
        value!!.addOnPropertyChangedCallback(callback)
    }

    private var callback: Observable.OnPropertyChangedCallback = object : Observable.OnPropertyChangedCallback() {
        override fun onPropertyChanged(sender: Observable, propertyId: Int) {

            //Trigger LiveData observer on change of any property in object
            value = value

        }
    }
}