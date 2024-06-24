package com.example.projectserotonin.data.preference

import android.content.Context
import android.content.SharedPreferences
import com.example.projectserotonin.data.DashboardResponse
import com.example.projectserotonin.data.Item
import com.example.projectserotonin.data.ItemsToConsume
import com.example.projectserotonin.utils.PrefUtils
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SharedPreferenceManager(private val context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)

    private val gson = Gson()

    fun saveBoolean(key:String,value:Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    fun getBoolean(key: String): Boolean {
        return sharedPreferences.getBoolean(key, false)
    }

    fun saveDashboardResponse(dashboardResponse: DashboardResponse) {
        val editor = sharedPreferences.edit()
        val json = gson.toJson(dashboardResponse)
        editor.putString(PrefUtils.DASHBOARD_RESPONSE, json)
        editor.apply()
    }

    fun getDashboardResponse() : DashboardResponse? {
        val json = sharedPreferences.getString(PrefUtils.DASHBOARD_RESPONSE, null)
        return gson.fromJson(json, DashboardResponse::class.java)
    }

    fun saveRegimenData(itemsToConsume: List<ItemsToConsume>) {
        val editor = sharedPreferences.edit()
        val json = gson.toJson(itemsToConsume)
        editor.putString(PrefUtils.REGIMEN_ITEMS_TO_CONSUME, json)
        editor.apply()
    }

    fun getRegimenData() : List<ItemsToConsume>? {
        val json = sharedPreferences.getString(PrefUtils.REGIMEN_ITEMS_TO_CONSUME, null)
        val listType = object : TypeToken<List<ItemsToConsume>>() {}.type
        return gson.fromJson(json, listType)
    }


}

