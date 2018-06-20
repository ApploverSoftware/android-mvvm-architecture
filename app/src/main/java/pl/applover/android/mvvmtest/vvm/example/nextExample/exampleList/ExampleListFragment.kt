package pl.applover.android.mvvmtest.vvm.example.nextExample.exampleList


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.example_fragment_list.*
import pl.applover.android.mvvmtest.App
import pl.applover.android.mvvmtest.R
import pl.applover.android.mvvmtest.util.extensions.showToast
import javax.inject.Inject

class ExampleListFragment : DaggerFragment() {

    @Inject
    internal lateinit var viewModelFactory: ExampleListViewModelFactory
    private lateinit var viewModel: ExampleListViewModel

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
        setViewListeners()
        viewModel.mldNetworkState.observe(this, Observer {
            it?.let {
                //todo
            }
        })
    }

    private fun setViewModelListeners() {
        viewModel.someToast.observe(this, Observer {
            it?.getContentIfNotHandled()?.let {
                showToast(it)
            }
        })
    }

    private fun setViewListeners() {
        buttonNavigatorTest.setOnClickListener {
            viewModel.fragmentClicked()
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.showSomeToast()
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
