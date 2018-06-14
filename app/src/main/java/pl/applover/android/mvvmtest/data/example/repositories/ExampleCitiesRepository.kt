package pl.applover.android.mvvmtest.data.example.repositories

import io.reactivex.Scheduler
import pl.applover.android.mvvmtest.data.example.internet.api_endpoints.ExampleCitiesApiEndpointsInterface
import pl.applover.android.mvvmtest.util.other.MyScheduler
import javax.inject.Inject

/**
 * Created by Janusz Hain on 2018-06-14.
 */
class ExampleCitiesRepository @Inject constructor(private val api: ExampleCitiesApiEndpointsInterface) {

    fun loadCitiesFromNetwork(observeOn: Scheduler = MyScheduler.getMainThreadScheduler(),
                              subscribeOn: Scheduler = MyScheduler.getScheduler()) =
            api.getCitiesList()
                    .subscribeOn(subscribeOn)
                    .observeOn(observeOn)

}