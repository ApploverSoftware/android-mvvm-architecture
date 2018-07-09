package pl.applover.android.mvvmtest.util.architecture.retrofit

import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import okhttp3.MediaType
import retrofit2.Response

/**
 * Created by Janusz Hain on 2018-06-27.
 */


fun <T : Any, V : Any> Single<Response<List<T>>>.mapResponseList(mapper: (T) -> V, mediaTypeForError: MediaType? = null): Single<Response<List<V>>> {
    return this.map {

        if (it.isSuccessful) {
            Response.success(it.body()?.map(mapper), it.raw())
        } else {
            Response.error(it.errorBody()
                    ?: okhttp3.ResponseBody.create(
                            mediaTypeForError ?: MediaType.parse("application/json"),
                            "{}"), it.raw())
        }
    }
}

fun <T : Any, V : Any> Observable<Response<List<T>>>.mapResponseList(mapper: (T) -> V, mediaTypeForError: MediaType? = null): Observable<Response<List<V>>> {
    return this.map {

        if (it.isSuccessful) {
            Response.success(it.body()?.map(mapper), it.raw())
        } else {
            Response.error(it.errorBody()
                    ?: okhttp3.ResponseBody.create(
                            mediaTypeForError ?: MediaType.parse("application/json"),
                            "{}"), it.raw())
        }
    }
}

fun <T : Any, V : Any> Flowable<Response<List<T>>>.mapResponseList(mapper: (T) -> V, mediaTypeForError: MediaType? = null): Flowable<Response<List<V>>> {
    return this.map {

        if (it.isSuccessful) {
            Response.success(it.body()?.map(mapper), it.raw())
        } else {
            Response.error(it.errorBody()
                    ?: okhttp3.ResponseBody.create(
                            mediaTypeForError ?: MediaType.parse("application/json"),
                            "{}"), it.raw())
        }
    }
}

fun <T : Any, V : Any> Single<Response<T>>.mapResponse(mapper: (T) -> V, mediaTypeForError: MediaType? = null): Single<Response<V>> {
    return this.map {

        if (it.isSuccessful) {
            Response.success(mapper(it.body()!!), it.raw())
        } else {
            Response.error(it.errorBody()
                    ?: okhttp3.ResponseBody.create(
                            mediaTypeForError ?: MediaType.parse("application/json"),
                            "{}"), it.raw())
        }
    }
}


fun <T : Any, V : Any> Observable<Response<T>>.mapResponse(mapper: (T) -> V, mediaTypeForError: MediaType? = null): Observable<Response<V>> {
    return this.map {

        if (it.isSuccessful) {
            Response.success(mapper(it.body()!!), it.raw())
        } else {
            Response.error(it.errorBody()
                    ?: okhttp3.ResponseBody.create(
                            mediaTypeForError ?: MediaType.parse("application/json"),
                            "{}"), it.raw())
        }
    }
}


fun <T : Any, V : Any> Flowable<Response<T>>.mapResponse(mapper: (T) -> V, mediaTypeForError: MediaType? = null): Flowable<Response<V>> {
    return this.map {

        if (it.isSuccessful) {
            Response.success(mapper(it.body()!!), it.raw())
        } else {
            Response.error(it.errorBody()
                    ?: okhttp3.ResponseBody.create(
                            mediaTypeForError ?: MediaType.parse("application/json"),
                            "{}"), it.raw())
        }
    }
}