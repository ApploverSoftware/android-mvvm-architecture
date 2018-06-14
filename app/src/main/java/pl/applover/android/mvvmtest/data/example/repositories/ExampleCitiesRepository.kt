package pl.applover.android.mvvmtest.data.example.repositories

import pl.applover.android.mvvmtest.data.example.internet.api_endpoints.ExampleCitiesApiEndpointsInterface
import javax.inject.Inject
import javax.inject.Named

/**
 * Created by Janusz Hain on 2018-06-14.
 */
class ExampleCitiesRepository @Inject constructor(val api: ExampleCitiesApiEndpointsInterface) {

}