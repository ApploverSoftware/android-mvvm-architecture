package pl.applover.android.mvvmtest.adapters.cities

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import pl.applover.android.mvvmtest.models.example.ExampleCityModel

/**
 * Created by Janusz Hain on 2018-06-20.
 */
class ExampleCityAdapter(private val cities: ArrayList<ExampleCityModel>, private val retryCallback: () -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        return ExampleCityViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ExampleCityViewHolder).bind(cities[position])
    }

    override fun getItemCount(): Int {
        return cities.size
    }
}