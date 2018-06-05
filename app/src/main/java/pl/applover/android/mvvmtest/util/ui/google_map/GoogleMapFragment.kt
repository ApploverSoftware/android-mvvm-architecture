package pl.applover.enegivetest.util.ui.google_map.google_map_fragment

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.UiSettings
import com.google.android.gms.maps.model.*
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.algo.CustomNonHierarchicalDistanceBasedAlgorithm
import kotlinx.android.synthetic.main.fragment_google_map.*
import org.greenrobot.eventbus.EventBus
import pl.applover.enegivetest.util.extensions.isGPSPermissionGranted
import pl.applover.enegivetest.util.ui.google_map.GoogleMapClusterRenderer
import pl.applover.enegivetest.util.ui.google_map.GoogleMapPadding
import pl.applover.enegivetest.util.ui.google_map.extensions.*
import pl.applover.android.mvvmtest.App
import pl.applover.android.mvvmtest.R
import pl.applover.android.mvvmtest.util.ui.google_map.GoogleMapReadyEvent


/**
 * Created by Janusz Hain on 2018-02-02.
 *
 *  GoogleMap sends event about being ready to use as [GoogleMapReadyEvent] using [EventBus]
 */
open class GoogleMapFragment : Fragment() {

    protected lateinit var googleMap: GoogleMap
    protected var googleMapClusterManager: ClusterManager<GoogleMapClusterRenderer.ClusterMarker>? = null
    protected lateinit var mapView: MapView


    var googleMapPadding: GoogleMapPadding? = null
        set(value) {
            value?.let {
                googleMap.setPadding(value.rectPadding.left, value.rectPadding.top, value.rectPadding.right, value.rectPadding.bottom)
            }
        }

    companion object {
        fun newInstance(): GoogleMapFragment {
            return GoogleMapFragment()
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_google_map, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mapView = googleMapView
        initializeMap(savedInstanceState, googleMapView)
    }

    fun initializeMap(savedInstanceState: Bundle?, mMapView: MapView) {
        try {
            MapsInitializer.initialize(App.instance)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        mMapView.onCreate(savedInstanceState)
        mMapView.onResume() // needed to get the map to display immediately
        mMapView.getMapAsync({ mMap ->
            googleMap = mMap
            onMapViewReady()
        })
    }

    private fun onMapViewReady() {
        setupGoogleMapClusterManager()
        EventBus.getDefault().post(GoogleMapReadyEvent(googleMap, googleMapClusterManager!!))
    }

    private fun setupGoogleMapClusterManager() {
        if (activity != null) {
            googleMapClusterManager = ClusterManager<GoogleMapClusterRenderer.ClusterMarker>(activity, googleMap)
            val googleMapClusterRenderer = GoogleMapClusterRenderer(googleMap, googleMapClusterManager!!, context!!)
            googleMap.setOnCameraIdleListener(googleMapClusterManager)
            googleMap.setOnInfoWindowClickListener(googleMapClusterManager)
            googleMap.setOnMarkerClickListener(googleMapClusterManager)
            googleMapClusterManager!!.renderer = googleMapClusterRenderer
            googleMapClusterManager!!.algorithm = CustomNonHierarchicalDistanceBasedAlgorithm<GoogleMapClusterRenderer.ClusterMarker>()
        }

    }

    fun setPadding(rect: Rect) {
        googleMap.setPadding(rect.left, rect.top, rect.right, rect.bottom)
    }

    fun setMarker(latLng: LatLng, iconResId: Int?, title: String?, snippet: String?): Marker {
        return googleMap.setMarker(latLng, iconResId, title, snippet)
    }

    fun setMarkerForClusterManager(latLng: LatLng, title: String, snippet: String, iconResId: Int?): GoogleMapClusterRenderer.ClusterMarker {
        val clusterItem = GoogleMapClusterRenderer.ClusterMarker(latLng, title, snippet, iconResId)
        googleMapClusterManager!!.addItem(clusterItem)
        return clusterItem
    }

    fun forceCluster() {
        googleMapClusterManager!!.cluster()
    }

    fun resetClusterManager() {
        if (activity != null) {
            val googleMapClusterRenderer = GoogleMapClusterRenderer(googleMap, googleMapClusterManager!!, context!!)
            googleMapClusterManager!!.renderer = googleMapClusterRenderer
            googleMapClusterManager!!.algorithm = CustomNonHierarchicalDistanceBasedAlgorithm<GoogleMapClusterRenderer.ClusterMarker>()
        }

    }

    fun setMarkerForClusterManager(latLng: LatLng, iconResId: Int?): GoogleMapClusterRenderer.ClusterMarker {
        val clusterItem = GoogleMapClusterRenderer.ClusterMarker(latLng, "", "", iconResId)
        googleMapClusterManager!!.addItem(clusterItem)
        return clusterItem
    }

    fun setMarkerForClusterManager(latLng: LatLng): GoogleMapClusterRenderer.ClusterMarker {
        val clusterItem = GoogleMapClusterRenderer.ClusterMarker(latLng, "", "", null)
        googleMapClusterManager!!.addItem(clusterItem)
        return clusterItem
    }

    fun setMarkersForClusterManager(clusterMarkers: Collection<GoogleMapClusterRenderer.ClusterMarker>) {
        googleMapClusterManager!!.addItems(clusterMarkers)
    }

    fun setOnMarkerClusterClickListener(listener: ClusterManager.OnClusterItemClickListener<GoogleMapClusterRenderer.ClusterMarker>) {
        googleMapClusterManager!!.setOnClusterItemClickListener(listener)
    }

    fun setOnMarkerDragListener(onMarkerDragListener: GoogleMap.OnMarkerDragListener) {
        googleMap.setOnMarkerDragListener(onMarkerDragListener)
    }

    fun removeMarkerFromCluster(clusterItem: GoogleMapClusterRenderer.ClusterMarker) {
        googleMapClusterManager!!.removeItem(clusterItem)
    }

    fun animateMarker(marker: Marker?, end: LatLng) {
        googleMap.animateMarker(marker, end)
    }

    fun setCameraPosition(latLng: LatLng, animateCamera: Boolean, zoom: Float = 12f) {
        googleMap.setCameraPosition(latLng, animateCamera, zoom)
    }

    fun setCameraPosition(latLngs: ArrayList<LatLng>, animateCamera: Boolean, padding: Int) {
        googleMap.setCameraPosition(latLngs, animateCamera, padding)
    }

    fun setCameraListener(listener: GoogleMap.OnCameraMoveListener?) {
        googleMap.setOnCameraMoveListener(listener)
    }

    fun setRangeCircle(circleOptions: CircleOptions): Circle {
        return googleMap.setRangeCircle(circleOptions)
    }

    fun getLastGpsPosition(): LatLng? {
        return getLastLocation()
    }

    fun drawRoute(latLngs: ArrayList<LatLng>, colorRid: Int, width: Float, context: Context): Polyline {
        return googleMap.drawPolyline(latLngs, colorRid, width, context)
    }

    fun setOnMapClickListener(onMapClickListener: GoogleMap.OnMapClickListener) {
        googleMap.setOnMapClickListener(onMapClickListener)
    }

    fun setOnMarkerClickListener(onMarkerClickListener: GoogleMap.OnMarkerClickListener) {
        googleMap.setOnMarkerClickListener(onMarkerClickListener)
    }

    fun clear() {
        googleMap.clear()
    }

    fun removeMarkersFromClusterManager() {
        googleMapClusterManager!!.clearItems()
    }

    fun setRotationGestures(enabled: Boolean) {
        googleMap.uiSettings.isRotateGesturesEnabled = enabled
    }

    fun getGoogleMapUiSettings(): UiSettings = googleMap.uiSettings

    @SuppressLint("MissingPermission")
    fun setUserLocation(enable: Boolean): Boolean {
        if (isGPSPermissionGranted()) {
            googleMap.isMyLocationEnabled = enable
            return enable
        } else {
            return false
        }
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mapView.onDestroy()
    }
}