package pl.applover.android.mvvmtest.vvm.example.next_example

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import kotlinx.android.synthetic.main.example_activity_main.*
import pl.applover.android.mvvmtest.R
import pl.applover.android.mvvmtest.util.other.DaggerAppCompatActivity
import javax.inject.Inject


class NextExampleActivity : DaggerAppCompatActivity() {

    @Inject
    internal lateinit var viewModelFactory: NextExampleViewModelFactory
    private lateinit var viewModel: NextExampleViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.next_example_activity_main)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(NextExampleViewModel()::class.java)
        viewModel.someEvent.observe(this, Observer { event -> println(event?.getContentIfNotHandled()) })
        viewModel.title.observe(this, Observer { title -> textViewHelloWorld.text = title })
    }

    override fun onResume() {
        super.onResume()
        viewModel.activityOnResume()
    }
}
