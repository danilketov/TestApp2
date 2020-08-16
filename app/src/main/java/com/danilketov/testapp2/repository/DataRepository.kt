package com.danilketov.testapp2.repository

import com.danilketov.testapp2.App
import com.danilketov.testapp2.db.AppDatabase
import com.danilketov.testapp2.entity.Specialty
import com.danilketov.testapp2.entity.Worker
import java.util.*

class DataRepository {

    private var db: AppDatabase? = App.getAppDatabase()

    fun getWorkers(workers: ArrayList<Worker>): ArrayList<Worker> {
        var workers: ArrayList<Worker> = workers
        db?.repositoryDao()?.deleteWorkers()
        db?.repositoryDao()?.insertWorkers(workers)
        workers = db?.repositoryDao()?.getWorkers() as ArrayList<Worker>
        return workers
    }

    fun getSpecialties(specialties: ArrayList<Specialty>): ArrayList<Specialty> {
        var specialties: ArrayList<Specialty> = specialties
        db?.repositoryDao()?.deleteSpecialties()
        db?.repositoryDao()?.insertSpecialties(specialties)
        specialties = db?.repositoryDao()?.getSpecialties() as ArrayList<Specialty>
        return specialties
    }
}