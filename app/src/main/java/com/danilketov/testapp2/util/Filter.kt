package com.danilketov.testapp2.util

import com.danilketov.testapp2.entity.Specialty
import com.danilketov.testapp2.entity.Worker
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

class Filter {

    companion object {
        // Фильтр отображения работников по выбранным специальностям
        fun getFilteredWorkers(result: ArrayList<Worker>?, nameSpecialty: String?): ArrayList<Worker> {
            val i: MutableIterator<Worker> = result!!.iterator()
            while (i.hasNext()) {
                val worker: Worker = i.next()
                var suitable = false
                for (oneSpecialty in worker.specialty!!) {
                    if (oneSpecialty.name.equals(nameSpecialty)) {
                        suitable = true
                    }
                }
                if (!suitable) i.remove()
            }
            return result
        }

        // Отображение специальности в описании работника
        fun getSpecialtyText(specialtyJSON: String?): String {
            val listType =
                object : TypeToken<ArrayList<Specialty?>?>() {}.type
            val specialties: List<Specialty>? =
                Gson().fromJson<List<Specialty>>(specialtyJSON, listType)
            var specialtyText = "-"
            if (specialties != null) {
                val specialtyTextBuilder = StringBuilder()
                for (specialty in specialties) {
                    specialtyTextBuilder.append(specialty.name).append(", ")
                }
                specialtyText = specialtyTextBuilder.toString()
            }
            if (specialtyText.endsWith(", ")) {
                specialtyText = specialtyText.substring(0, specialtyText.length - 2)
            }
            return specialtyText
        }

        // Выборка уникальных элементов для списка "Специальности"
        fun addUniqueItems(
            items: List<Specialty?>,
            specialties: MutableList<Specialty?>
        ) {
            for (specialty in items) {
                if (!specialties.contains(specialty)) {
                    specialties.add(specialty)
                }
            }
        }
    }
}