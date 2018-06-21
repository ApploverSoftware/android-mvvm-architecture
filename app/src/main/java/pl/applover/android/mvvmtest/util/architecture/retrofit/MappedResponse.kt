package pl.applover.android.mvvmtest.util.architecture.retrofit

import okhttp3.Headers
import okhttp3.ResponseBody

/**
 * Created by Janusz Hain on 2018-06-18.
 */

/**
 * [okhttp3.Response] has private constructor, so to do mapping for Response, we have to use this custom class
 */
open class MappedResponse<T>(private val rawResponse: okhttp3.Response,
                             private val body: T?,
                             private val errorBody: ResponseBody?) {

    /** HTTP status code.  */
    fun code(): Int {
        return rawResponse.code()
    }

    /** HTTP status message or null if unknown.  */
    fun message(): String {
        return rawResponse.message()
    }

    /** HTTP headers.  */
    fun headers(): Headers {
        return rawResponse.headers()
    }

    /** Returns true if [.code] is in the range [200..300).  */
    fun isSuccessful(): Boolean {
        return rawResponse.isSuccessful
    }

    /** The deserialized response body of a [successful][.isSuccessful] response.  */
    fun body(): T? {
        return body
    }

    /** The raw response body of an [unsuccessful][.isSuccessful] response.  */
    fun errorBody(): ResponseBody? {
        return errorBody
    }

    override fun toString(): String {
        return rawResponse.toString()
    }
}