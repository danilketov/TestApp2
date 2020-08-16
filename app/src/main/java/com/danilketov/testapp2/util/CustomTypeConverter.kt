package com.danilketov.testapp2.util

import androidx.room.TypeConverter
import com.danilketov.testapp2.entity.Specialty
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class CustomTypeConverter {

    private val gson = Gson()

    @TypeConverter
    fun stringToSomeObjectList(data: String?): List<Specialty> {
        if (data == null) {
            return emptyList<Specialty>()
        }
        val listType =
            object : TypeToken<List<Specialty>>() {}.type
        return gson.fromJson<List<Specialty>>(data, listType)
    }

    @TypeConverter
    fun someObjectListToString(someObjects: List<Specialty>): String? {
        return gson.toJson(someObjects)
    }
}