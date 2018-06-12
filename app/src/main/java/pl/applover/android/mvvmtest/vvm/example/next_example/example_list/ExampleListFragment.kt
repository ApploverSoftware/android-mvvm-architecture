package pl.applover.android.mvvmtest.vvm.example.next_example.example_list


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.example_fragment_list.*
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
            viewModel.navigator.sender.fragmentClicked.onNext("")
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.showSomeToast()
    }

    companion object {
        @JvmStatic
        fun newInstance() = ExampleListFragment()
    }
}
