package com.danilketov.testapp2.db

import androidx.room.TypeConverter
import com.danilketov.testapp2.entity.Specialty
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.*

class CustomTypeConverter {

    companion object {
        private val gson = Gson()
    }

    @TypeConverter
    fun stringToListServer(data: String?): List<Specialty?> {
        if (data == null) {
            return Collections.emptyList()
        }
        val listType: Type = object :
            TypeToken<List<Specialty?>?>() {}.type
        return gson.fromJson<List<Specialty?>>(data, listType)
    }

    @TypeConverter
    fun listServerToString(someObjects: List<Specialty?>): String? {
        return gson.toJson(someObjects)
    }
}
