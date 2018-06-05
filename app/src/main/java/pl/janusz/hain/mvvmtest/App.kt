package pl.janusz.hain.mvvmtest

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasDispatchingActivityInjector
import org.jetbrains.anko.locationManager
import pl.applover.enegivetest.dependency_injection.application.components.DaggerAppComponent
import pl.applover.enegivetest.util.extensions.DelegatesExt
import pl.applover.enegivetest.util.extensions.isGPSPermissionGranted
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Named


/**
 * Created by Janusz Hain on 2018-01-08.
 */
class App : Application(), HasDispatchingActivityInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    @Inject
    @field:Named("example")
    lateinit var exampleRetrofit: Retrofit

    private val locationListeners = ArrayList<LocationListener>()

    private var lastLocation: Location? = null

    override fun activityInjector(): DispatchingAndroidInjector<Activity> {
        return dispatchingAndroidInjector
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        DaggerAppComponent
                .builder()
                .application(this)
                .build()
                .inject(this)
    }


    @SuppressLint("MissingPermission")
    fun startLocationUpdates() {
        if (!isGPSPermissionGranted()) return

        val providers = locationManager.getProviders(true)
        for (provider in providers) {

            val locationListener = object : LocationListener {
                override fun onLocationChanged(location: Location?) {
                    if (location != null)
                        lastLocation = location
                }

                override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {
                }

                override fun onProviderEnabled(p0: String?) {
                }

                override fun onProviderDisabled(p0: String?) {
                }
            }

            locationListeners.add(locationListener)

            locationManager.requestLocationUpdates(provider, 10000, 100f,
                    locationListener)
        }
    }

    fun removeLocationUpdates() {
        if (!isGPSPermissionGranted(applicationContext)) {
            return
        }

        locationListeners.forEach {
            locationManager.removeUpdates(it)
        }

        locationListeners.clear()
    }

    @SuppressLint("MissingPermission")
    fun getLastLocation(): Location? {

        if (!isGPSPermissionGranted(applicationContext)) {
            return null
        }

        var location: Location?

        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)

        location ?: kotlin.run {
            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
        }

        location ?: kotlin.run {
            location = lastLocation
        }

        return location
    }

    companion object {
        var instance: App by DelegatesExt.notNullSingleValue()

        fun getExampleRetrofit() = instance.exampleRetrofit
    }
}