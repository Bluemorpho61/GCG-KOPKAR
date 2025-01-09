package com.alkindi.kopkar.utils

import com.google.gson.Gson
import java.net.URLEncoder

object ApiNetworkingUtils {
    fun jsonFormatter(rawDataValue: Map<String, String>): String {
        val jsonString = Gson().toJson(rawDataValue)
        val encodedJson = URLEncoder.encode(jsonString, "UTF-8")
        return encodedJson
    }
}