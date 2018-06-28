package pl.applover.android.mvvmtest.modelFactories.example

import pl.applover.android.mvvmtest.models.example.ExampleCityModel
import java.util.concurrent.atomic.AtomicInteger

/**
 * Created by Janusz Hain on 2018-06-28.
 */
class ExampleCityModelTestFactory {
    private val counter = AtomicInteger(0)

    fun create(countryId: Int? = null): ExampleCityModel {
        val id = counter.incrementAndGet()
        val countryString = if (countryId != null) "country_$countryId" else "country_$id"
        return ExampleCityModel(id = "id_$id", name = "city_$id", country = countryString, _numberOfClicks = 0, lat = id.toDouble()*2/3+2, lng = id.toDouble()*3/4+5)
    }

    fun create(countryId: Int) {
        val id = counter.incrementAndGet()
    }

    fun createList(size: Int): List<ExampleCityModel> {
        val arrayList: ArrayList<ExampleCityModel> = ArrayList(size)
        for (i in 0..size) {
            arrayList.add(create())
        }

        return arrayList
    }
}