package com.example.harajtask.data

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException

class HarajReopImpl(private val context: Context) :HarajReop{

    override  fun getProducts(): List<Product> {
        lateinit var jsonString: String
        try {
            jsonString = context.assets.open("data.json")
                .bufferedReader()
                .use { it.readText() }
        } catch (ioException: IOException) {
            Log.d("JsonIOException", "Faild to parse json ${ioException.printStackTrace()}")
        }

        val listCountryType = object : TypeToken<List<Product>>() {}.type
        return Gson().fromJson(jsonString, listCountryType)
    }


}