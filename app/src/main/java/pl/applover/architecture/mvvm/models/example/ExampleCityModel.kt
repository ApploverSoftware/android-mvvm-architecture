package pl.applover.architecture.mvvm.models.example

import android.annotation.SuppressLint
import android.databinding.BaseObservable
import android.databinding.Bindable
import android.os.Parcelable
import com.android.databinding.library.baseAdapters.BR
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize
import pl.applover.architecture.mvvm.data.example.database.models.ExampleCityDbModel
import pl.applover.architecture.mvvm.data.example.internet.response.ExampleCityResponse
import kotlin.properties.Delegates


/**
 * Model for DataBinding
 * Notice that vars have to be built differently as var's set will have to notify about property being changed
 */
@SuppressLint("ParcelCreator")
@Parcelize
data class ExampleCityModel(
        val id: String,
        val name: String,
        val lat: Double,
        val lng: Double,
        //@get:Bindable - it is val, so bindable is useless. For var it would be okay, for example numberOfClicks
        val country: String,
        private var _numberOfClicks: Int = 0 //needed to save number of clicks for Parcelable
) : BaseObservable(), Parcelable {

    @IgnoredOnParcel
    @get:Bindable
    @delegate:Transient
    var numberOfClicks by Delegates.observable(_numberOfClicks) { _, _, value ->
        _numberOfClicks = value
        notifyPropertyChanged(BR.numberOfClicks)
    }

    constructor(exampleCityResponse: ExampleCityResponse) : this(
            exampleCityResponse.id,
            exampleCityResponse.name,
            exampleCityResponse.lat,
            exampleCityResponse.lng,
            exampleCityResponse.country)

    constructor(exampleCityDbModel: ExampleCityDbModel) : this(
            exampleCityDbModel.id,
            exampleCityDbModel.name,
            exampleCityDbModel.lat,
            exampleCityDbModel.lng,
            exampleCityDbModel.country
    )
}