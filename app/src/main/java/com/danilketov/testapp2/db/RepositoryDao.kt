package com.danilketov.testapp2.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.danilketov.testapp2.entity.Specialty
import com.danilketov.testapp2.entity.Worker

@Dao
interface RepositoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWorkers(workers: List<Worker>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSpecialties(specialties: List<Specialty>)

    @Query("DELETE FROM Worker")
    fun deleteWorkers()

    @Query("DELETE FROM Specialty")
    fun deleteSpecialties()

    @Query("SELECT * FROM Worker")
    fun getWorkers(): List<Worker>

    @Query("SELECT * FROM Specialty")
    fun getSpecialties(): List<Specialty>
}