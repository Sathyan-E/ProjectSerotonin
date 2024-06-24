package com.example.projectserotonin.dashboard

import android.util.Log
import com.example.projectserotonin.MyApplication
import com.example.projectserotonin.data.DashboardResponse
import com.example.projectserotonin.data.preference.SharedPreferenceManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DashboardRepository @Inject constructor() {
    suspend fun fetchDashboardData() : Flow<DashboardResponse?> = flow {
        val response = SharedPreferenceManager(MyApplication.appContext).getDashboardResponse()
       if (response != null) {
           emit(response)
       } else {
           emit(null)
       }
    }
}