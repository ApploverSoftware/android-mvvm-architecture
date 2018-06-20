package pl.applover.android.mvvmtest.adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.example_item_network_state.*
import pl.applover.android.mvvmtest.util.architecture.network.NetworkState

/**
 * Created by Janusz Hain on 2018-06-20.
 */
class ExampleNetworkStateViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    fun bind(networkState: NetworkState) {
        textViewHelloWorld.text = networkState.status.name
    }

}