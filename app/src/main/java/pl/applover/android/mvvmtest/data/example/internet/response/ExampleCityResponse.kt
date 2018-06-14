package pl.applover.android.mvvmtest.data.example.internet.response

import com.squareup.moshi.Json

data class ExampleCityResponse(
    @Json(name = "id") val id: Int,
    @Json(name = "name") val name: String,
    @Json(name = "lat") val lat: Double,
    @Json(name = "lng") val lng: Double,
    @Json(name = "country") val country: String
)