package pl.applover.architecture.mvvm.staticclasses

import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import pl.applover.architecture.mvvm.util.other.SharedPreferencesHelper

/**
 * Created by Janusz Hain on 2018-07-18.
 */

/**
 * Create object to mockk MySharedPreferences
 * Mock every method that doesn't return anything
 */
class SharedPreferencesHelperMocker {
    init {
        mockkObject(SharedPreferencesHelper)
        every { SharedPreferencesHelper.setStringToSharedPreferences(any(), any()) }.returns(mockk())
    }
}