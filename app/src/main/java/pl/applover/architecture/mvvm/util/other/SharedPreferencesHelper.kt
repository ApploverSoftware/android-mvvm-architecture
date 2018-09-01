package pl.applover.architecture.mvvm.util.other

import android.content.Context
import android.content.SharedPreferences
import pl.applover.architecture.mvvm.App

/**
 * Created by Janusz Hain on 05/07/2017.
 */

object SharedPreferencesHelper {

    private val packageName: String by lazy { App.instance.packageName }

    /**
     * Saves int to SharedPreferences
     *
     * Default context is Application context
     */
    fun setIntToSharedPreferences(key: String, value: Int, context: Context = App.instance) {
        val sharedPreferences = getSharedPreferences(context)
        val editor = sharedPreferences.edit()
        editor.putInt(key, value)
        editor.apply()
    }

    /**
     * Gets int from SharedPreferences
     * If no value under the key, returns null
     *
     * Default context is Application context
     */
    fun getIntFromSharedPreferences(key: String, defaultValue: Int? = null, context: Context = App.instance): Int? {
        if (!getSharedPreferences(context).contains(key)) return null
        val sharedPreferences = getSharedPreferences(context)
        return sharedPreferences.getInt(key, defaultValue ?: -1)
    }

    /**
     * Saves int to SharedPreferences
     *
     * Default context is Application context
     */
    fun setDoubleToSharedPreferences(key: String, value: Double, context: Context = App.instance) {
        val sharedPreferences = getSharedPreferences(context)
        val editor = sharedPreferences.edit()
        editor.putString(key, value.toString())
        editor.apply()
    }

    /**
     * Gets int from SharedPreferences
     *
     * Default context is Application context
     */
    fun getDoubleFromSharedPreferences(key: String, defaultValue: Double, context: Context = App.instance): Double {
        val sharedPreferences = getSharedPreferences(context)
        return (sharedPreferences.getString(key, defaultValue.toString())).toDouble()
    }

    /**
     * Saves String to SharedPreferences
     *
     * Default context is Application context
     */
    fun setStringToSharedPreferences(key: String, value: String, context: Context = App.instance) {
        val sharedPreferences = getSharedPreferences(context)
        val editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    /**
     * Gets String from SharedPreferences
     *
     * Default context is Application context
     */
    fun getStringFromSharedPreferences(key: String, defaultValue: String? = null, context: Context = App.instance): String? {
        val sharedPreferences = getSharedPreferences(context)
        return sharedPreferences.getString(key, defaultValue)
    }

    /**
     * Saves Boolean to SharedPreferences
     *
     * Default context is Application context
     */
    fun setBooleanToSharedPreferences(key: String, value: Boolean, context: Context = App.instance) {
        val sharedPreferences = getSharedPreferences(context)
        val editor = sharedPreferences.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    /**
     * Gets boolean from SharedPreferences
     * Default context is Application context
     */
    fun getBooleanFromSharedPreferences(key: String, defaultValue: Boolean, context: Context = App.instance): Boolean {
        val sharedPreferences = getSharedPreferences(context)
        return sharedPreferences.getBoolean(key, defaultValue)
    }

    /**
     * Removes param from SharedPreferences
     *
     * Default context is Application context
     */
    fun removeFromSharedPreferences(key: String, context: Context = App.instance) {
        val sharedPreferences = getSharedPreferences(context)
        val editor = sharedPreferences.edit()
        editor.remove(key)
        editor.apply()
    }

    /**
     * Gets "global" SharedPreferences
     *
     * Default context is Application context
     */
    fun getSharedPreferences(context: Context = App.instance): SharedPreferences {
        return context.getSharedPreferences(
                packageName, Context.MODE_PRIVATE)
    }

    /**
     * Saves StringSet to SharedPreferences
     *
     * Default context is Application context
     */
    fun setStringSetToSharedPreferences(key: String, value: Set<String>, context: Context = App.instance) {
        val sharedPreferences = getSharedPreferences(context)
        val editor = sharedPreferences.edit()
        editor.putStringSet(key, value)
        editor.apply()
    }

    /**
     * Gets StringSet from SharedPreferences
     *
     * Default context is Application context
     */
    fun getStringSetFromSharedPreferences(key: String, defaultValue: Set<String>, context: Context = App.instance): Set<String> {
        val sharedPreferences = getSharedPreferences(context)
        return sharedPreferences.getStringSet(key, defaultValue)
    }

    fun defaultContains(vararg keys: String) = with(getSharedPreferences()) {
        keys.all { contains(it) }
    }
}
