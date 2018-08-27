package pl.applover.android.mvvmtest.staticclasses

import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import pl.applover.android.mvvmtest.util.other.MySharedPreferencesUtil

/**
 * Created by Janusz Hain on 2018-07-18.
 */

/**
 * Create object to mockk MySharedPreferences
 * Mock every method that doesn't return anything
 */
class MySharedPreferencesUtilMocker {
    init {
        mockkObject(MySharedPreferencesUtil)
        every { MySharedPreferencesUtil.setStringToSharedPreferences(any(), any()) }.returns(mockk())
    }
}