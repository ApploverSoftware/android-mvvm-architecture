package pl.applover.android.mvvmtest.data.example.database.dao

import android.arch.paging.DataSource
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import pl.applover.android.mvvmtest.data.example.database.models.ExampleCityDbModel

/**
 * Created by Janusz Hain on 2018-06-14.
 */
@Dao
interface ExampleCityDao {
    @Query("SELECT * FROM example_cities ORDER BY id")
    abstract fun citiesById(): DataSource.Factory<Int, ExampleCityDbModel>
}