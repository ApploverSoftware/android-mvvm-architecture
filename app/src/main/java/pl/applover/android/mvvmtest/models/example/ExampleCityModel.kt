package pl.applover.android.mvvmtest.models.example

import android.annotation.SuppressLint
import android.databinding.BaseObservable
import android.databinding.Bindable
import android.os.Parcelable
import com.android.databinding.library.baseAdapters.BR
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize
import pl.applover.android.mvvmtest.data.example.database.models.ExampleCityDbModel
import pl.applover.android.mvvmtest.data.example.internet.response.ExampleCityResponse
import kotlin.properties.Delegates


/**
 * Model for DataBinding
 * Notice that vars have to be built differently as var's set will have to notify about property being changed
 */
@SuppressLint("ParcelCreator")
@Parcelize
data class ExampleCityModel(
        @get:Bindable
        val id: String,
        @get:Bindable
        val name: String,
        @get:Bindable
        val lat: Double,
        @get:Bindable
        val lng: Double,
        @get:Bindable
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

    fun getInfoAboutCity() = "City $name in country $country with coordinates: $lat lat and $lng lng"

}