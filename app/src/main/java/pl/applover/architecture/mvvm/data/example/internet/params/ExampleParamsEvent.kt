package pl.applover.architecture.mvvm.data.example.internet.params

import com.squareup.moshi.Json


/**
 * Created by Janusz Hain on 2018-01-12.
 */
data class ExampleParamsEvent(private val paramsBody: ParamsBody) {

    /**
     * If you need json string then:
     * Generate classes using JsonToKotlinClass:
     * 1. Code
     * 2. Generate
     * 3. Convert Json into Kotlin
     */

    data class ParamsBody(
            @Json(name = "event") val event: Event
    )

    data class Event(
            @Json(name = "to") val to: String,
            @Json(name = "from") val from: String
    )

}

