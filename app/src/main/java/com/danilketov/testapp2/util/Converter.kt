package com.danilketov.testapp2.util

import com.danilketov.testapp2.R
import com.danilketov.testapp2.entity.Worker
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class Converter {

    companion object {
        // Форматирование дня рождения к единому формату
        fun getFormattedBirthday(dateString: String?): String? {
            if (dateString == null || dateString == "") {
                return "-"
            } else {
                val inputFormat1: DateFormat =
                    SimpleDateFormat("yyyy-mm-dd", Locale.getDefault())
                val inputFormat2: DateFormat =
                    SimpleDateFormat("dd-mm-yyyy", Locale.getDefault())
                val date1: Date
                val date2: Date
                val outputFormat: DateFormat =
                    SimpleDateFormat("dd.mm.yyyy", Locale.getDefault())
                try {
                    date1 = inputFormat1.parse(dateString)
                    if (date1.time > 0) {
                        return outputFormat.format(date1) + Const.WORKER_BIRTHDAY_YEAR_END
                    }
                } catch (e: ParseException) {
                    e.printStackTrace()
                }
                try {
                    date2 = inputFormat2.parse(dateString)
                    if (date2.time > 0) {
                        return outputFormat.format(date2) + Const.WORKER_BIRTHDAY_YEAR_END
                    }
                } catch (e: ParseException) {
                    e.printStackTrace()
                }
            }
            return null
        }

        //Преобразование возраста из дня рождения
        fun getFormattedAge(birthdayString: String?): String {
            return if (birthdayString == null || birthdayString == "") {
                "-"
            } else {
                val birthday = getFormattedBirthday(birthdayString)
                var date: Date? = null
                val inputFormat =
                    SimpleDateFormat("dd.mm.yyyy", Locale.getDefault())
                try {
                    date = inputFormat.parse(birthday)
                } catch (e: ParseException) {
                    e.printStackTrace()
                }
                val dob = Calendar.getInstance()
                val today = Calendar.getInstance()
                dob.time = date
                var age = today[Calendar.YEAR] - dob[Calendar.YEAR]
                if (today[Calendar.DAY_OF_YEAR] < dob[Calendar.DAY_OF_YEAR]) {
                    age--
                }
                val result: String = age.toString() + Const.WORKER_AGE_END
                return result
            }
        }

        // Форматирование строки с заглавной буквы
        fun getFormattedString(str: String?): String {
            return if (str == null || str == "") {
                "-"
            } else {
                str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase()
            }
        }

        // Отображение фото работника
        fun getAvatarWorker(avatarUrl: String?, worker: Worker): String? {
            return if (avatarUrl == null || avatarUrl == "") {
                java.lang.String.valueOf(R.drawable.no_avatar)
            } else {
                worker.avatarUrl
            }
        }
    }


}