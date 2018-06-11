package pl.applover.android.mvvmtest.vvm.example.main_example

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.example_activity_main.*
import pl.applover.android.mvvmtest.R
import pl.applover.android.mvvmtest.util.extensions.goToActivity
import pl.applover.android.mvvmtest.vvm.example.next_example.NextExampleActivity
import javax.inject.Inject


class ExampleMainActivity : DaggerAppCompatActivity() {


    @Inject
    internal lateinit var viewModelFactory: ExampleMainViewModelFactory
    private lateinit var viewModel: ExampleMainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.example_activity_main)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ExampleMainViewModel()::class.java)
        viewModel.someEvent.observe(this, Observer { event -> println(event?.getContentIfNotHandled(this)) })
        viewModel.title.observe(this, Observer { title -> textViewHelloWorld.text = title })

        textViewHelloWorld.setOnClickListener {
            println("Go to next Activity")
            goToNextActivity()
        }
    }

    private fun goToNextActivity() {
        goToActivity(NextExampleActivity::class.java, true)
    }

    override fun onResume() {
        super.onResume()
        viewModel.activityOnResume()
    }

}
