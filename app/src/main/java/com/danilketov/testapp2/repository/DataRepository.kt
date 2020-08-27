package com.danilketov.testapp2.repository

import com.danilketov.testapp2.App
import com.danilketov.testapp2.db.AppDatabase
import com.danilketov.testapp2.entity.Specialty
import com.danilketov.testapp2.entity.Worker
import java.util.*

class DataRepository {

    private var db: AppDatabase? = App.getAppDatabase()

    fun getWorkers(workers: ArrayList<Worker>?): ArrayList<Worker>? {
        workers?.let {
            db?.repositoryDao()?.deleteWorkers()
            db?.repositoryDao()?.insertWorkers(it)
        }
        return db?.repositoryDao()?.getWorkers() as ArrayList<Worker>?
    }

    fun getSpecialties(specialties: ArrayList<Specialty>?): ArrayList<Specialty>? {
        specialties?.let {
            db?.repositoryDao()?.deleteSpecialties()
            db?.repositoryDao()?.insertSpecialties(it)
        }
        return db?.repositoryDao()?.getSpecialties() as ArrayList<Specialty>?
    }
}