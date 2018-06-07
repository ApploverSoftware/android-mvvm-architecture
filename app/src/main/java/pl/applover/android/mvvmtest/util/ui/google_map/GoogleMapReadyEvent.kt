package pl.applover.android.mvvmtest.util.ui.google_map

import com.google.android.gms.maps.GoogleMap
import com.google.maps.android.clustering.ClusterManager

/**
 * Created by Janusz Hain on 2018-06-05.
 */
class GoogleMapReadyEvent(val googleMap: GoogleMap, val googleMapClusterManager: ClusterManager<GoogleMapClusterRenderer.ClusterMarker>)