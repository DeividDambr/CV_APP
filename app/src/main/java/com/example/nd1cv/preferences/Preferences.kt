package com.example.nd1cv.preferences

import android.content.Context
import android.content.SharedPreferences

class Preferences(context: Context) {
    private val sharedPref: SharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

    fun getCVParaTitle(id: Int): String {
        return sharedPref.getString("savedCVTitle$id", "") ?: ""
    }

    fun getCVParaText(id: Int): String {
        return sharedPref.getString("savedCVText$id", "") ?: ""
    }

    fun getTextId(): Int {
        return sharedPref.getInt("textId", 0)
    }

    fun getDeletedId(): Int{
        return sharedPref.getInt("deletedId", -1)
    }

    fun saveTextId(id: Int) {
        sharedPref.edit().putInt("textId", id).apply()
    }

    fun saveDeletedId(id: Int){
        sharedPref.edit().putInt("deletedId", id).apply()
    }

    fun saveText(id: Int, title: String, text: String) {
        sharedPref.edit()
            .putString("savedCVTitle$id", title)
            .putString("savedCVText$id", text)
            .apply()
    }

    fun deleteText(id: Int) {
        sharedPref.edit()
            .remove("savedCVTitle$id")
            .remove("savedCVText$id")
            .apply()
    }
}