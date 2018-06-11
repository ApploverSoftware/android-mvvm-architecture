package pl.applover.android.mvvmtest.vvm.example.next_example

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import dagger.android.support.DaggerAppCompatActivity
import pl.applover.android.mvvmtest.R
import pl.applover.android.mvvmtest.util.extensions.showFragment
import pl.applover.android.mvvmtest.vvm.example.next_example.example_list.ExampleListFragment
import javax.inject.Inject


class NextExampleActivity : DaggerAppCompatActivity() {

    @Inject
    internal lateinit var viewModelFactory: NextExampleViewModelFactory
    private lateinit var viewModel: NextExampleViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.example_next_activity_main)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(NextExampleViewModel::class.java)
        if (savedInstanceState == null) {
            showFragment(ExampleListFragment.newInstance(), R.id.frameLayoutExample, false, null, null)
        }

    }

    override fun onResume() {
        super.onResume()
        viewModel.someEvent.observe(this, Observer { event -> println(event?.getContentIfNotHandled()) })
        viewModel.activityOnResume()
    }
}
