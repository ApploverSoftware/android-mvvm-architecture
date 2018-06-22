package pl.applover.android.mvvmtest.vvm.example.nextExample.exampleList


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.example_fragment_list.*
import kotlinx.android.synthetic.main.example_item_network_state.*
import pl.applover.android.mvvmtest.App
import pl.applover.android.mvvmtest.R
import pl.applover.android.mvvmtest.adapters.cities.ExampleCityAdapter
import pl.applover.android.mvvmtest.util.architecture.network.NetworkState
import pl.applover.android.mvvmtest.util.architecture.network.NetworkStatus
import pl.applover.android.mvvmtest.util.extensions.showToast
import pl.applover.android.mvvmtest.util.ui.hide
import pl.applover.android.mvvmtest.util.ui.show
import javax.inject.Inject

class ExampleListFragment : DaggerFragment() {

    @Inject
    internal lateinit var viewModelFactory: ExampleListViewModelFactory
    private lateinit var viewModel: ExampleListViewModel

    private val adapter = ExampleCityAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ExampleListViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        setViewModelListeners()
        return inflater.inflate(R.layout.example_fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerViewCities.adapter = adapter
        setViewListeners()
        setViewModelObservers()
    }

    private fun setViewModelObservers() {
        viewModel.mldNetworkState.observe(this, Observer {
            it?.let {
                manageNetworkStateView(it)
            }
        })

        viewModel.mldCitiesLiveData.observe(this, Observer {
            it?.let {
                adapter.replaceCities(it)
            }
        })
    }

    private fun manageNetworkStateView(networkState: NetworkState) {
        textViewNetworkState.text = networkState.networkStatus.name

        when (networkState.networkStatus) {
            NetworkStatus.FAILED -> {
                layoutExampleItemNetworkState.show()
                progressBarNetwork.hide()
                buttonRetry.show()
            }
            NetworkStatus.RUNNING -> {
                layoutExampleItemNetworkState.show()
                progressBarNetwork.show()
                buttonRetry.hide()
            }
            NetworkStatus.SUCCESS -> {
                layoutExampleItemNetworkState.hide()
            }
        }
    }

    private fun setViewModelListeners() {
        viewModel.mldSomeToast.observe(this, Observer {
            it?.getContentIfNotHandled()?.let {
                showToast(it)
            }
        })
    }

    private fun setViewListeners() {
        buttonNavigatorTest.setOnClickListener {
            viewModel.fragmentClicked()
            viewModel.saveCitiesToDb()
        }

        buttonRetry.setOnClickListener {
            viewModel.loadCities()
        }

        buttonDataFromDb.setOnClickListener {
            viewModel.loadCitiesFromDb()
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.showSomeToast()
        if (viewModel.mldCitiesLiveData.value!!.size == 0)
            viewModel.loadCities()
    }

    override fun onDestroy() {
        super.onDestroy()
        watchLeaks()
    }

    private fun watchLeaks() {
        App.refWatcher.watch(this)
        App.refWatcher.watch(viewModelFactory)
    }

    companion object {
        @JvmStatic
        fun newInstance() = ExampleListFragment()
    }
}
