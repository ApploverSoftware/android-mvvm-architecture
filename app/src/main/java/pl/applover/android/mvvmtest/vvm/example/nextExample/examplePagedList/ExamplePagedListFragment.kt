package pl.applover.android.mvvmtest.vvm.example.nextExample.examplePagedList


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.example_fragment_paged_list.*
import kotlinx.android.synthetic.main.example_item_network_state.*
import pl.applover.android.mvvmtest.App
import pl.applover.android.mvvmtest.R
import pl.applover.android.mvvmtest.adapters.cities.ExamplePagedLibCityAdapter
import pl.applover.android.mvvmtest.util.architecture.network.NetworkState
import pl.applover.android.mvvmtest.util.ui.hide
import pl.applover.android.mvvmtest.util.ui.show
import javax.inject.Inject

class ExamplePagedListFragment : DaggerFragment() {

    @Inject
    internal lateinit var viewModelFactory: ExamplePagedListViewModelFactory
    private lateinit var viewModel: ExamplePagedListViewModel

    private val adapter = ExamplePagedLibCityAdapter {
        viewModel.retry()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ExamplePagedListViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        setViewModelListeners()
        return inflater.inflate(R.layout.example_fragment_paged_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerViewCities.adapter = adapter
        viewModel.ldCitiesPagedList.observe(this, Observer { adapter.submitList(it) })
        setViewListeners()
        setViewModelObservers()
    }

    private fun setViewModelObservers() {
        viewModel.mldInitialNetworkState.observe(this, Observer {
            it?.let {
                manageNetworkStateView(it)
            }
        })

        viewModel.mldNetworkState.observe(this, Observer {
            it?.let {
                adapter.setNetworkState(it)
            }
        })
    }

    private fun manageNetworkStateView(networkState: NetworkState) {
        textViewNetworkState.text = networkState.networkStatus.name

        when (networkState.networkStatus) {
            NetworkState.State.FAILED -> {
                layoutExampleItemNetworkState.show()
                progressBarNetwork.hide()
                buttonRetry.show()
            }
            NetworkState.State.RUNNING -> {
                layoutExampleItemNetworkState.show()
                progressBarNetwork.show()
                buttonRetry.hide()
            }
            NetworkState.State.SUCCESS -> {
                layoutExampleItemNetworkState.hide()
            }
        }
    }

    private fun setViewModelListeners() {
        viewModel.mldCities.observe(this, Observer {
            if (it == true) {
                buttonDataFromDb.text = "Show online data"
                buttonDataFromDb.setOnClickListener {
                    viewModel.loadCitiesFromOnlineSource(this)
                    viewModel.ldCitiesPagedList.observe(this, Observer {
                        adapter.submitList(it)
                    })
                }
            } else {
                buttonDataFromDb.text = "Show db data"
                buttonDataFromDb.setOnClickListener {
                    setOnCitiesDbClicked()
                }
            }
        })
    }

    private fun setViewListeners() {
        buttonNavigatorTest.setOnClickListener {
            viewModel.saveCitiesToDb()
        }

        buttonRetry.setOnClickListener {
            viewModel.retry()
        }

        buttonDataFromDb.setOnClickListener {
            setOnCitiesDbClicked()
        }
    }

    private fun setOnCitiesDbClicked() {
        viewModel.loadCitiesFromDb(this)
        viewModel.ldCitiesPagedList.observe(this, Observer {
            adapter.submitList(it)
        })
    }

    override fun onResume() {
        super.onResume()

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
        fun newInstance() = ExamplePagedListFragment()
    }
}
