package pl.applover.android.mvvmtest.data.example.database.models

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import pl.applover.android.mvvmtest.models.example.ExampleCityModel

/**
 * DataModel is different than original model as those models could look or work differently. After changing data model it can be much
 * less work to refactor other models and vice versa
 */
@Entity(tableName = "example_cities", primaryKeys = ["id"])
data class ExampleCityDbModel(
        @ColumnInfo(name = "id") val id: Int,
        @ColumnInfo(name = "name") val name: String,
        @ColumnInfo(name = "lat") val lat: Double,
        @ColumnInfo(name = "lng") val lng: Double,
        @ColumnInfo(name = "country") val country: String
) {
    constructor(exampleCityModel: ExampleCityModel) : this(exampleCityModel.id, exampleCityModel.name, exampleCityModel.lat, exampleCityModel.lng, exampleCityModel.country)

}