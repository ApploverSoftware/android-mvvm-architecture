package pl.applover.android.mvvmtest.models.example

import pl.applover.android.mvvmtest.data.example.internet.response.ExampleCityResponse


class ExampleCityModel(
        val id: Int,
        val name: String,
        val lat: Double,
        val lng: Double,
        val country: String
) {

    constructor(exampleCityResponse: ExampleCityResponse) : this(id = exampleCityResponse.id,
            name = exampleCityResponse.name,
            lat = exampleCityResponse.lat,
            lng = exampleCityResponse.lng,
            country = exampleCityResponse.country)

    fun getInfoAboutCity() = "City $name in country $country with coordinates: $lat lat and $lng lng"

}