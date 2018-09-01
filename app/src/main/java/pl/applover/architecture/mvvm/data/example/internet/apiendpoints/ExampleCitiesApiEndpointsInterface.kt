package pl.applover.architecture.mvvm.data.example.internet.apiendpoints

import io.reactivex.Single
import pl.applover.architecture.mvvm.data.example.internet.response.ExampleCityResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

/**
 * Created by Janusz Hain on 2018-01-12.
 */

interface ExampleCitiesApiEndpointsInterface {

    /*
    @POST("/api/v1/events")
    abstract fun saveEvent(
            @Header("Content-Type") contentType: String,
            @Header("Authorization") authorization: String,
            @Header("Login") login: String,
            @Header("Device") device: String,
            @Body jsonBody: String
    ): Single<Response<ResponseBackendEvent>>

    @HTTP(method = "DELETE", path = "/api/v1/events", hasBody = true)
    abstract fun deleteEvent(
            @Header("Content-Type") contentType: String,
            @Header("Authorization") authorization: String,
            @Header("Login") login: String,
            @Header("Device") device: String,
            @Body jsonBody: String
    ): Single<Response<Void>>

    @GET("/api/v1/events")
    abstract fun getEvents(
            @Header("Content-Type") contentType: String,
            @Header("Authorization") authorization: String,
            @Header("Login") login: String,
            @Header("Device") device: String
    ): Single<Response<List<ResponseBackendEvent>>>
    */


    @GET("/JanuszHain/MyJsonServerCities/cities")
    fun getCitiesList(
            @Header("Content-Type") contentType: String = "application/json"
    ): Single<Response<List<ExampleCityResponse>>>

    @GET("/JanuszHain/MyJsonServerCities/{lastId}")
    fun getPagedCitiesList(
            @Path("lastId") lastId: String,
            @Header("Content-Type") contentType: String = "application/json"
    ): Single<Response<List<ExampleCityResponse>>>
}
