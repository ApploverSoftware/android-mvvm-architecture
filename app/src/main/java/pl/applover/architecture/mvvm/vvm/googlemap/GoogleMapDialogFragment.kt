package pl.applover.architecture.mvvm.vvm.googlemap

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.*
import com.google.maps.android.clustering.ClusterManager
import kotlinx.android.synthetic.main.fragment_google_map.*
import pl.applover.architecture.mvvm.App
import pl.applover.architecture.mvvm.R
import pl.applover.architecture.mvvm.util.architecture.dependencyinjection.DaggerFullscreenDialogFragment
import pl.applover.architecture.mvvm.util.architecture.rx.EmptyEvent
import pl.applover.architecture.mvvm.util.extensions.isGPSPermissionGranted
import javax.inject.Inject


/**
 * Created by Janusz Hain on 2018-02-02.
 * */
class GoogleMapDialogFragment : DaggerFullscreenDialogFragment() {

    protected lateinit var googleMap: GoogleMap
    protected var googleMapClusterManager: ClusterManager<GoogleMapClusterRenderer.ClusterMarker>? = null
    protected lateinit var mapView: MapView


    @Inject
    protected lateinit var router: GoogleMapDialogFragmentRouter

    var googleMapPadding: GoogleMapPadding? = null
        set(value) {
            value?.let {
                googleMap.setPadding(value.rectPadding.left, value.rectPadding.top, value.rectPadding.right, value.rectPadding.bottom)
            }
        }

    companion object {
        fun newInstance(): GoogleMapDialogFragment {
            return GoogleMapDialogFragment()
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

    private fun initializeMap(savedInstanceState: Bundle?, mMapView: MapView) {
        try {
            MapsInitializer.initialize(App.instance)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        mMapView.onCreate(savedInstanceState)
        mMapView.onResume() // needed to get the map to display immediately
        mMapView.getMapAsync { mMap ->
            googleMap = mMap
            onMapViewReady()
        }
    }

    private fun onMapViewReady() {
        setupGoogleMapClusterManager()
        router.sender.onMapReady.onNext(EmptyEvent())
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

    fun findMarkerFromClusterManager(latLng: LatLng): Marker? = googleMapClusterManager?.markerCollection?.markers?.find { it.position == latLng }

    fun findLastMarkerFromClusterManager(latLng: LatLng): Marker? = googleMapClusterManager?.markerCollection?.markers?.findLast { it.position == latLng }


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

    fun defaultMoveToMarker(marker: Marker) {
        googleMap.animateCamera(CameraUpdateFactory.newLatLng(marker.position))
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

    fun setStyle(style: String) {
        googleMap.setMapStyle(MapStyleOptions(style))
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