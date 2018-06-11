package pl.applover.android.mvvmtest.vvm.example.next_example

import android.arch.lifecycle.MutableLiveData
import pl.applover.android.mvvmtest.util.architecture.live_data.SingleEvent
import pl.applover.android.mvvmtest.vvm.example.next_example.example_list.ExampleListFragmentNavigator

/**
 * Created by Janusz Hain on 2018-06-11.
 */
class NextExampleNavigator : ExampleListFragmentNavigator {

    private val fragmentClickedLiveData: MutableLiveData<SingleEvent<String>> = MutableLiveData()

    override fun fragmentClickedLiveData(): MutableLiveData<SingleEvent<String>> = fragmentClickedLiveData
}