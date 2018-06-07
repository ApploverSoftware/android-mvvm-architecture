package pl.applover.android.mvvmtest.vvm.example.next_example.list


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.android.support.DaggerFragment
import pl.applover.android.mvvmtest.R

class ExampleListFragment : DaggerFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.example_fragment_list, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() = ExampleListFragment()
    }
}
