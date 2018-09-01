package pl.applover.architecture.mvvm.util.ui

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

/**
 * Created by Janusz Hain on 2018-04-24.
 */

/**
 * EndlessScrollListener for LinearLayoutManager, should work with GridLayoutManager too (NOT TESTED!!!)
 */
class EndlessScrollListener() {
    private var visibleItemCount: Int = 0
    private var totalItemCount: Int = 0
    private var pastVisibleItems: Int = 0

    private var linearLayoutManager: LinearLayoutManager? = null

    private var minimumItemsToStartLoading: Int = 10

    private var recyclerView: RecyclerView? = null

    private var onEndlessScrollCallback: (() -> Unit)? = null


    private val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            if (dy > 0) {
                onScrolledDown()
            }
        }
    }

    private fun onScrolledDown() {
        linearLayoutManager?.let {
            visibleItemCount = it.childCount
            pastVisibleItems = it.findFirstVisibleItemPosition()
            totalItemCount = it.itemCount
            if (checkIfNeededToLoadMore(visibleItemCount, pastVisibleItems, totalItemCount)) {
                onEndlessScrollCallback?.invoke()
            }
        }
    }

    private fun checkIfNeededToLoadMore(visibleItemCount: Int, pastVisibleItems: Int, totalItemCount: Int): Boolean {
        return visibleItemCount + pastVisibleItems >= totalItemCount - minimumItemsToStartLoading
    }

    fun setEndlessScrollingListener(recyclerView: RecyclerView?, onEndlessScrollCallback: () -> Unit, minimumItemsToStartLoading: Int = 10) {
        removeEndlessScrollingListener()

        this.recyclerView = recyclerView
        this.minimumItemsToStartLoading = minimumItemsToStartLoading
        this.recyclerView!!.addOnScrollListener(scrollListener)
        this.onEndlessScrollCallback = onEndlessScrollCallback

        when (recyclerView?.layoutManager) {
            is LinearLayoutManager -> {
                linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
            }
            else -> {
                throw EndlessScrollListenerException("No linearManager in recyclerView: ${recyclerView?.layoutManager}")
            }
        }

    }

    fun removeEndlessScrollingListener() {
        minimumItemsToStartLoading = 10
        recyclerView?.removeOnScrollListener(scrollListener)
        recyclerView = null
        linearLayoutManager = null
        onEndlessScrollCallback = null
    }


    class EndlessScrollListenerException(message: String?) : Exception(message)
}

