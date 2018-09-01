package pl.applover.architecture.mvvm.vvm.googlemap

import android.content.Context
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.Cluster
import com.google.maps.android.clustering.ClusterItem
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.view.DefaultClusterRenderer


/**
 * Created by Janusz Hain on 2018-02-01.
 *
 * If [colorRid] is null, then default colors will be displayed
 */
open class GoogleMapClusterRenderer(googleMap: GoogleMap,
                                    clusterManager: ClusterManager<ClusterMarker>,
                                    context: Context,
                                    private val colorRid: Int? = null) : DefaultClusterRenderer<GoogleMapClusterRenderer.ClusterMarker>(context, googleMap, clusterManager) {

    override fun onBeforeClusterItemRendered(clusterMarker: ClusterMarker, markerOptions: MarkerOptions) {
        markerOptions.icon(clusterMarker.getIcon())
        markerOptions.snippet(if (clusterMarker.snippet.isNotEmpty()) clusterMarker.snippet else null)
        markerOptions.title(if (clusterMarker.title.isNotEmpty()) clusterMarker.title else null)
        super.onBeforeClusterItemRendered(clusterMarker, markerOptions)
    }

    /**
     * To change number of items to render the cluster, change in "other"
     */
    override fun shouldRenderAsCluster(cluster: Cluster<ClusterMarker>?): Boolean {
        return cluster?.size?.compareTo(2) == 1
    }

    override fun getColor(clusterSize: Int): Int = super.getColor(colorRid ?: clusterSize)


    open class ClusterMarker(protected var positionMarker: LatLng, protected var titleMarker: String, protected var snippetMarker: String, protected val drawableRid: Int?, protected val tag: String? = null) : ClusterItem {

        override fun getSnippet(): String = snippetMarker

        override fun getTitle(): String = titleMarker

        override fun getPosition(): LatLng = positionMarker

        var id: Int? = null

        fun getIcon(): BitmapDescriptor? = if (drawableRid != null) BitmapDescriptorFactory.fromResource(drawableRid) else null
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as ClusterMarker

            if (positionMarker != other.positionMarker) return false
            if (titleMarker != other.titleMarker) return false
            if (snippetMarker != other.snippetMarker) return false
            if (drawableRid != other.drawableRid) return false
            if (tag != other.tag) return false

            return true
        }

        override fun hashCode(): Int {
            var result = positionMarker.hashCode()
            result = 31 * result + titleMarker.hashCode()
            result = 31 * result + snippetMarker.hashCode()
            result = 31 * result + (drawableRid ?: 0)
            result = 31 * result + (tag?.hashCode() ?: 0)
            return result
        }


    }
}

