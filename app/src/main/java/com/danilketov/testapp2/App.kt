package com.danilketov.testapp2

import android.app.Application
import androidx.room.Room
import com.danilketov.testapp2.db.AppDatabase
import com.danilketov.testapp2.repository.DataRepository

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }

    companion object {
        private var db: AppDatabase? = null
        private var dataRepository: DataRepository? = null
        private var INSTANCE: App? = null


        fun getDataRepository(): DataRepository? {
            if (dataRepository == null) {
                dataRepository = DataRepository()
            }
            return dataRepository
        }

        fun getAppDatabase(): AppDatabase? {
            if (db == null) {
                val appDatabase = AppDatabase.DB_NAME?.let {
                    INSTANCE?.let { it1 ->
                        Room.databaseBuilder(it1, AppDatabase::class.java, it)
                            .build()
                    }
                }
                db = appDatabase
            }
            return db
        }
    }
}