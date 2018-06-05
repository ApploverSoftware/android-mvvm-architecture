package pl.applover.android.mvvmtest.util.extensions

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import pl.applover.android.mvvmtest.App

/**
 * Created by Janusz Hain on 2017-08-28.
 */

fun checkPermissionGPSAndRequestIfNotGranted(activity: Activity) {
    if (!isGPSPermissionGranted(activity)) {
        requestPermissionsGPS(activity)
    }
}

fun isGPSPermissionGranted(activity: Activity): Boolean {
    return ActivityCompat.checkSelfPermission(activity,
            Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(activity,
                    Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
}

fun isGPSPermissionGranted(context: Context = App.instance): Boolean {
    return ActivityCompat.checkSelfPermission(context,
            Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(context,
                    Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
}

private fun requestPermissionsGPS(activity: Activity) {
    ActivityCompat.requestPermissions(activity, arrayOf<String>(Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION), 0)
}

