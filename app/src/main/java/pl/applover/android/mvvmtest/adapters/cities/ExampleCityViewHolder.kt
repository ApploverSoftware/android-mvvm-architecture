package pl.applover.android.mvvmtest.adapters.cities

import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.example_item_city.*
import pl.applover.android.mvvmtest.models.example.ExampleCityModel

/**
 * Created by Janusz Hain on 2018-06-20.
 */
class ExampleCityViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    fun bind(city: ExampleCityModel) {
        textViewHelloWorld.text = city.name
    }

}