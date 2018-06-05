package pl.janusz.hain.mvvmtest.util.ui.google_map

import com.google.android.gms.maps.GoogleMap
import com.google.maps.android.clustering.ClusterManager
import pl.applover.enegivetest.util.ui.google_map.GoogleMapClusterRenderer

/**
 * Created by Janusz Hain on 2018-06-05.
 */
class GoogleMapReadyEvent(googleMap: GoogleMap, googleMapClusterManager: ClusterManager<GoogleMapClusterRenderer.ClusterMarker>)