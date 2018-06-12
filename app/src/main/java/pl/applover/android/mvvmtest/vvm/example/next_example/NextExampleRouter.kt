package pl.applover.android.mvvmtest.vvm.example.next_example

import pl.applover.android.mvvmtest.vvm.example.next_example.example_list.ExampleListFragmentNavigator
import javax.inject.Inject

/**
 * Created by Janusz Hain on 2018-06-11.
 */
class NextExampleRouter @Inject constructor(nextExampleActivityNavigator: NextExampleActivityNavigator, exampleListFragmentNavigator: ExampleListFragmentNavigator) {
    init {
        exampleListFragmentNavigator.sender.fragmentClicked.subscribe(nextExampleActivityNavigator.receiver.fragmentClicked)
    }
}