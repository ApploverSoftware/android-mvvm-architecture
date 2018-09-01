package pl.applover.architecture.mvvm.vvm.example.nextExample

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.example_next_activity_main.*
import pl.applover.architecture.mvvm.App
import pl.applover.architecture.mvvm.R
import pl.applover.architecture.mvvm.util.extensions.showFragment
import pl.applover.architecture.mvvm.vvm.example.nextExample.exampleList.ExampleListFragment
import pl.applover.architecture.mvvm.vvm.example.nextExample.examplePagedList.ExamplePagedListFragment
import javax.inject.Inject


class NextExampleActivity : DaggerAppCompatActivity() {

    @Inject
    internal lateinit var viewModelFactory: NextExampleViewModelFactory
    private lateinit var viewModel: NextExampleViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.example_next_activity_main)
        setViewListeners()
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(NextExampleViewModel::class.java)
    }

    private fun setViewListeners() {
        buttonCitiesWithoutPaging.setOnClickListener {
            showFragment(ExampleListFragment.newInstance(), R.id.frameLayoutExample, false, null, null)
        }

        buttonCitiesWithPagingLib.setOnClickListener {
            showFragment(ExamplePagedListFragment.newInstance(), R.id.frameLayoutExample, false, null, null)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.someEvent.observe(this, Observer { event -> println(event?.getContentIfNotHandled(this)) })
        viewModel.activityOnResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        watchLeaks()
    }

    private fun watchLeaks() {
        App.refWatcher.watch(this)
        App.refWatcher.watch(viewModelFactory)
    }
}
