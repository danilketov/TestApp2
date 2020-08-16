package com.danilketov.testapp2.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.danilketov.testapp2.entity.Specialty
import com.danilketov.testapp2.entity.Worker

@Database(entities = [Worker::class, Specialty::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    companion object{
        val DB_NAME: String? = "workers_db"
    }

    abstract fun repositoryDao(): RepositoryDao
}