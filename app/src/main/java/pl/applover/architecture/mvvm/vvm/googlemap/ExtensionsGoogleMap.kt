package pl.applover.architecture.mvvm.vvm.googlemap

import android.animation.ValueAnimator
import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.support.v4.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.*
import pl.applover.architecture.mvvm.App
import timber.log.Timber
import java.lang.Exception

/**
 * Created by Janusz Hain on 2017-08-28.
 */
fun GoogleMap.setCameraAtWarsaw() {
    setCameraPosition(LatLng(52.2297, 21.0122))
}

fun GoogleMap.setMarkerAtWarsaw() {
    setMarker(LatLng(52.2297, 21.0122))
}

fun GoogleMap.setCameraPosition(latLng: LatLng, animateCamera: Boolean = false, zoom: Float = 12f) {
    val cameraPosition = CameraPosition.Builder().target(latLng).zoom(zoom).build()
    if (animateCamera) {
        animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
    } else {
        moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
    }
}

/**
 * Method needs at least one latLng
 */
fun GoogleMap.setCameraPosition(latLngs: ArrayList<LatLng>, animateCamera: Boolean = false, padding: Int = 10) {
    if (latLngs.size < 1) {
        return
    }

    val boundsBuilder = LatLngBounds.Builder()

    latLngs.forEach { latLng ->
        boundsBuilder.include(latLng)
    }

    val bounds = boundsBuilder.build()

    try {
        if (animateCamera) {
            animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, padding))
        } else {
            moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, padding))
        }
    } catch (e: Exception) {

    }

}

fun GoogleMap.setMarker(latLng: LatLng, iconResId: Int? = null, title: String? = null, snippet: String? = null): Marker {
    return when {
        iconResId != null -> {
            addMarker(MarkerOptions()
                    .position(latLng)
                    .title(title)
                    .snippet(snippet)
                    .icon(BitmapDescriptorFactory.fromResource(iconResId)))
        }
        else -> {
            addMarker(MarkerOptions()
                    .position(latLng)
                    .title(title)
                    .snippet(snippet))
        }
    }
}

fun GoogleMap.animateMarker(marker: Marker?, end: LatLng) {
    marker?.let {
        val startLon = marker.position.longitude
        val startLat = marker.position.latitude
        val deltaLon = end.longitude - startLon
        val deltaLat = end.latitude - startLat
        val anim = ValueAnimator.ofFloat(0f, 100f).setDuration(500)
        anim.addUpdateListener {
            var position = LatLng(it.animatedFraction * deltaLat + startLat, it.animatedFraction * deltaLon + startLon)
            marker.remove()
            setMarker(position)
        }
        anim.start()
    }
}

fun GoogleMap.setRangeCircle(circleOptions: CircleOptions): Circle {
    return addCircle(circleOptions)
}

fun GoogleMap.drawPolyline(latLngs: ArrayList<LatLng>, colorRid: Int, width: Float = 5f, context: Context = App.instance): Polyline {
    return addPolyline(PolylineOptions()
            .addAll(latLngs)
            .width(width)
            .color(ContextCompat.getColor(context, colorRid)))
}

fun getAddress(latLng: LatLng, context: Context = App.instance): Address? {
    var addresses: List<Address>? = null
    try {
        val geocoder: Geocoder = Geocoder(context)
        addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
    } catch (t: Throwable) {
        Timber.e(t)
    }

    if (addresses == null || addresses.isEmpty()) {
        return null
    } else {
        return addresses[0]
    }
}

/**
 * @return distance between points or null if calculate failed
 */
fun getDistanceBetweenPoints(latLng: LatLng, latLng2: LatLng): Float? {
    return try {
        val resultArray = kotlin.FloatArray(1)
        Location.distanceBetween(latLng.latitude, latLng.longitude, latLng2.latitude, latLng2.longitude, resultArray)
        resultArray[0]
    } catch (e: Exception) {
        null
    }
}
