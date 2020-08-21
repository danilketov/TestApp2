package com.danilketov.testapp2.entity

import com.google.gson.annotations.SerializedName
import java.util.*

data class Response(
    @SerializedName("response")
    val response: ArrayList<Worker>? = null
)