package com.danilketov.testapp2.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.danilketov.testapp2.db.CustomTypeConverter
import com.google.gson.annotations.SerializedName

@Entity
data class Worker(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,

    @SerializedName("f_name")
    val firstName: String? = null,

    @SerializedName("l_name")
    val lastName: String? = null,

    val birthday: String? = null,

    @SerializedName("avatr_url")
    val avatarUrl: String? = null,

    @TypeConverters(CustomTypeConverter::class)
    val specialty: List<Specialty>? = null
)