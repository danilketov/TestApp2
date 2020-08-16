package com.danilketov.testapp2.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Specialty(
    @PrimaryKey
    @SerializedName("specialty_id")
    val id: Int? = 0,
    val name: String? = null
)