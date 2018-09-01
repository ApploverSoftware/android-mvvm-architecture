package pl.applover.architecture.mvvm.staticclasses

import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import pl.applover.architecture.mvvm.App

/**
 * Created by Janusz Hain on 2018-07-18.
 */

/**
 * Create object to mockk Application class with mocked context
 */
class AppCompanionMocker {
    init {
        mockkObject(App)
        every { App.instance }.returns(mockk())
    }
}