package pl.applover.android.mvvmtest.data.example.internet.api_endpoints

import io.reactivex.Single
import pl.applover.android.mvvmtest.data.example.internet.response.ExampleResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

/**
 * Created by Janusz Hain on 2018-01-12.
 */

interface ExampleApiEndpointsInterface {

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


    @GET("/posts")
    fun getExampleList(
            @Header("Content-Type") contentType: String = "application/json",
            @Query("userId") userId: String
    ): Single<Response<List<ExampleResponse>>>
}
