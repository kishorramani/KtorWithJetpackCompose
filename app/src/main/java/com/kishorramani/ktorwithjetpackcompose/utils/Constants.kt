package com.kishorramani.ktorwithjetpackcompose.utils

import android.content.Context
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken

class Constants {
    companion object {
        fun showToast(context: Context, msg: String) {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
        }

        // function to convert any data class to a JSON string
        inline fun <reified T> T.toJson(): String {
            return Gson().toJson(this, T::class.java)
        }

        inline fun <reified T> T.toJsonWithNullValue(): String {
            val gson = GsonBuilder().serializeNulls().create()
            return gson.toJson(this, T::class.java)
        }

        // function to convert a JSON string to a given data class object
        inline fun <reified T> String.fromJson(): T {
            return Gson().fromJson(this, object : TypeToken<T>() {}.type)
        }
    }
}