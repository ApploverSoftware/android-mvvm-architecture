package pl.applover.android.mvvmtest.adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.example_item_network_state.*
import pl.applover.android.mvvmtest.util.architecture.network.NetworkState
import pl.applover.android.mvvmtest.util.ui.hide
import pl.applover.android.mvvmtest.util.ui.show

/**
 * Created by Janusz Hain on 2018-06-20.
 */

/**
 * ViewHolder for showing progressBar and retry button in case of failed loading
 */
class ExampleNetworkStateViewHolder(override val containerView: View, private val retryCallback: () -> Unit) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    fun bind(networkState: NetworkState) {
        textViewNetworkState.text = networkState.networkStatus.name
        buttonRetry.setOnClickListener {
            retryCallback()
        }

        when (networkState.networkStatus) {
            NetworkState.State.RUNNING -> {
                progressBarNetwork.show()
                buttonRetry.hide()
            }
            NetworkState.State.FAILED -> {
                progressBarNetwork.hide()
                buttonRetry.show()
            }
            else -> {
            }
        }
    }

}