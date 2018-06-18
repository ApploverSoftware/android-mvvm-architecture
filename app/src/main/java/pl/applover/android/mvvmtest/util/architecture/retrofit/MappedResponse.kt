package pl.applover.android.mvvmtest.util.architecture.retrofit

import okhttp3.ResponseBody

/**
 * Created by Janusz Hain on 2018-06-18.
 */

data class MappedResponse<T>(val rawResponse: okhttp3.Response, val body: T?,
                          val errorBody: ResponseBody?)