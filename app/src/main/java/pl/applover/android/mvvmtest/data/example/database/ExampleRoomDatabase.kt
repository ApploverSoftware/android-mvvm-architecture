package pl.applover.android.mvvmtest.data.example.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import pl.applover.android.mvvmtest.data.example.database.dao.ExampleCityDao
import pl.applover.android.mvvmtest.data.example.database.models.ExampleCityDbModel

/**
 * Created by Janusz Hain on 2018-06-14.
 */
@Database(entities = [ExampleCityDbModel::class], version = 1)
abstract class ExampleRoomDatabase : RoomDatabase() {
    abstract fun exampleCityDao(): ExampleCityDao
}