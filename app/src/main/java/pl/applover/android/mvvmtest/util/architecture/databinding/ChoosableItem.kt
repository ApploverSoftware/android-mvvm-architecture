package pl.applover.android.mvvmtest.util.architecture.databinding

import android.annotation.SuppressLint
import android.databinding.BaseObservable
import android.databinding.Bindable
import android.os.Parcelable
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize
import  pl.applover.android.mvvmtest.BR
import kotlin.properties.Delegates

/**
 * Created by Janusz Hain on 2018-07-30.
 */

/**
 * Helper for setting temporary chosen items
 */

@SuppressLint("ParcelCreator")
@Parcelize
data class ChoosableItem<Item : Parcelable>(val item: Item) : BaseObservable(), Parcelable {

    constructor(choosableItem: ChoosableItem<Item>) : this(choosableItem.item) {
        isChosen = choosableItem.isChosen
    }

    @IgnoredOnParcel
    @get:Bindable
    @delegate:Transient
    var isChosen by Delegates.observable(false) { _, _, _ ->
        notifyPropertyChanged(BR.chosen)
    }

    fun contains(item: Item) = this.item == item


    override fun toString(): String {
        return "ChoosableItem(item=$item, isChosen: $isChosen)"
    }


}