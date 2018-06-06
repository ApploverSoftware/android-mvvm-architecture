package pl.applover.android.mvvmtest.vvm.example

import android.os.Bundle
import dagger.android.DaggerActivity
import pl.applover.android.mvvmtest.R
import javax.inject.Inject

class ExampleMainActivity : DaggerActivity() {

    @Inject
    lateinit var viewModel: ExampleMainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
