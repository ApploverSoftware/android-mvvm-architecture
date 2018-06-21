package pl.applover.android.mvvmtest.adapters.cities

import android.support.v7.widget.RecyclerView
import pl.applover.android.mvvmtest.databinding.ExampleItemCityBinding
import pl.applover.android.mvvmtest.models.example.ExampleCityModel

/**
 * Created by Janusz Hain on 2018-06-20.
 */
class ExampleCityViewHolder(private val binding: ExampleItemCityBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(city: ExampleCityModel) {
        binding.city = city
    }

}