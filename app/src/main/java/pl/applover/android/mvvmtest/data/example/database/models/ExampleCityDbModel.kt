package pl.applover.android.mvvmtest.data.example.database.models

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity

@Entity(tableName = "example_cities", primaryKeys = ["id"])
data class ExampleCityDbModel(
        @ColumnInfo(name = "id") val id: Int,
        @ColumnInfo(name = "name") val name: String,
        @ColumnInfo(name = "lat") val lat: Double,
        @ColumnInfo(name = "lng") val lng: Double,
        @ColumnInfo(name = "country") val country: String
)