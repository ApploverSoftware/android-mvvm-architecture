package pl.applover.android.mvvmtest.adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.example_item_network_state.*
import pl.applover.android.mvvmtest.util.architecture.network.NetworkState
import pl.applover.android.mvvmtest.util.architecture.network.NetworkStatus

/**
 * Created by Janusz Hain on 2018-06-20.
 */
class ExampleNetworkStateViewHolder(override val containerView: View, private val retryCallback: () -> Unit) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    fun bind(networkState: NetworkState) {
        textViewHelloWorld.text = networkState.networkStatus.name
        if (networkState.networkStatus == NetworkStatus.FAILED) {
            textViewHelloWorld.setOnClickListener {
                retryCallback()
            }
        } else {
            textViewHelloWorld.setOnClickListener(null)
        }
    }

}