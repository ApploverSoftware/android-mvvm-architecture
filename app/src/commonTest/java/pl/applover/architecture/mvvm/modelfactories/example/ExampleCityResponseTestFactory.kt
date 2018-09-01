package pl.applover.architecture.mvvm.modelfactories.example

import pl.applover.architecture.mvvm.data.example.internet.response.ExampleCityResponse
import java.util.concurrent.atomic.AtomicInteger

/**
 * Created by Janusz Hain on 2018-06-28.
 */
class ExampleCityResponseTestFactory {
    private val counter = AtomicInteger(0)

    fun create(countryId: Int? = null): ExampleCityResponse {
        val id = counter.incrementAndGet()
        val countryString = if (countryId != null) "country_$countryId" else "country_$id"
        return ExampleCityResponse(id = "id_$id", name = "city_$id", country = countryString, lat = id.toDouble() * 2 / 3 + 2, lng = id.toDouble() * 3 / 4 + 5)
    }

    fun createList(size: Int, countryId: Int? = null): List<ExampleCityResponse> {
        val arrayList: ArrayList<ExampleCityResponse> = ArrayList(size)
        for (i in 0 until size) {
            arrayList.add(create(countryId))
        }

        return arrayList
    }
}