package pl.applover.android.mvvmtest

import org.mockito.Mockito.mock
import org.mockito.Mockito.spy

/**
 * Created by Janusz Hain on 2018-06-27.
 */
inline fun <reified T> lambdaMock(): T = mock(T::class.java)