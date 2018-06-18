package pl.applover.android.mvvmtest.data.example.database.dao

import android.arch.paging.DataSource
import android.arch.persistence.room.*
import io.reactivex.Single
import pl.applover.android.mvvmtest.data.example.database.models.ExampleCityDbModel


/**
 * Created by Janusz Hain on 2018-06-14.
 */
@Dao
interface ExampleCityDao {
    @Query("SELECT * FROM example_cities ORDER BY id")
    fun citiesById(): Single<ArrayList<ExampleCityDbModel>>

    @Query("SELECT * FROM example_cities ORDER BY id")
    fun citiesPagedById(): DataSource.Factory<Int, ExampleCityDbModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrReplaceAll(cities: Collection<ExampleCityDbModel>)

    @Delete
    fun delete(city: ExampleCityDbModel)

    @Query("DELETE FROM example_cities")
    fun deleteAll()
}