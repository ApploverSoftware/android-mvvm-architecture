package pl.applover.android.mvvmtest.adapters.cities

import android.arch.paging.PagedListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import pl.applover.android.mvvmtest.R
import pl.applover.android.mvvmtest.adapters.ExampleNetworkStateViewHolder
import pl.applover.android.mvvmtest.databinding.ExampleItemCityBinding
import pl.applover.android.mvvmtest.models.example.ExampleCityModel
import pl.applover.android.mvvmtest.util.architecture.network.NetworkState

/**
 * Created by Janusz Hain on 2018-06-20.
 */

/**
 * Adapter with paging library
 */
class ExamplePagedLibCityAdapter(private val retryCallback: () -> Unit) : PagedListAdapter<ExampleCityModel, RecyclerView.ViewHolder>(
        object : DiffUtil.ItemCallback<ExampleCityModel>() {
            override fun areItemsTheSame(oldItem: ExampleCityModel?, newItem: ExampleCityModel?): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ExampleCityModel?, newItem: ExampleCityModel?): Boolean {
                return true //let's return true always as we have dataBinding anyway!
            }
        }
) {

    private var networkState: NetworkState? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            R.layout.example_item_city -> {
                val binding: ExampleItemCityBinding = ExampleItemCityBinding.inflate(inflater, parent, false)
                ExampleCityViewHolder(binding)

            }
            R.layout.example_item_network_state -> {
                val view = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
                ExampleNetworkStateViewHolder(view, retryCallback)
            }
            else -> throw IllegalArgumentException("Unknown view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            R.layout.example_item_city -> currentList?.get(position)?.let { (holder as ExampleCityViewHolder).bind(it) }
            R.layout.example_item_network_state -> (holder as ExampleNetworkStateViewHolder).bind(networkState!!)
        }
    }

    override fun getItemCount(): Int {
        return currentList?.size ?: 0+if (hasExtraRow()) 1 else 0
    }


    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
            R.layout.example_item_network_state
        } else {
            R.layout.example_item_city
        }
    }

    /**
     * Set the current network state to the adapter
     * but this work only after the initial load
     * and the adapter already have list to add new loading raw to it
     * so the initial loading state the activity responsible for handle it
     *
     * @param newNetworkState the new network state
     */
    fun setNetworkState(newNetworkState: NetworkState) {
        if (currentList != null && currentList?.size != 0) {
            val hadExtraRow = hasExtraRow()
            this.networkState = newNetworkState
            val hasExtraRow = hasExtraRow()
            if (hadExtraRow != hasExtraRow) {
                if (hadExtraRow) {
                    notifyItemRemoved(itemCount)
                } else {
                    notifyItemInserted(itemCount)
                }
            } else if (hasExtraRow) {
                notifyItemChanged(itemCount - 1)
            }
        }
    }

    private fun hasExtraRow(): Boolean {
        return networkState != null && networkState != NetworkState.LOADED
    }
}