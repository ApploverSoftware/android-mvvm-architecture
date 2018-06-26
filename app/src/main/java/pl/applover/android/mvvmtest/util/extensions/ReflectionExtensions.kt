package pl.applover.android.mvvmtest.util.extensions

import java.lang.reflect.Field
import java.lang.reflect.Modifier

/**
 * Created by Janusz Hain on 2018-06-26.
 */
fun Field.toPublicVar(){
    isAccessible = true
    val modifiersField = Field::class.java.getDeclaredField("modifiers")
    modifiersField.isAccessible = true
    modifiersField.setInt(this, this.modifiers and Modifier.FINAL.inv())
}