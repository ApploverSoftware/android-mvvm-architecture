package pl.applover.android.mvvmtest.data.example.internet.headers

/**
 * Created by Janusz Hain on 2018-01-12.
 */
/**
 * Note that I used header that can be not used in some cases. For example if we know contentType will be used in every http request the same,
 * then we can create default variable in API call and don't pass anything there.
 * I used this only to demonstrate how we can store data for headers
 */
data class ExampleHeaders(val contentType: String = "application/json")
