package com.example.projectserotonin.dashboard

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectserotonin.MyApplication
import com.example.projectserotonin.utils.CommonUtils
import com.example.projectserotonin.data.DashboardResponse
import com.example.projectserotonin.data.preference.RegimenDataResponse
import com.example.projectserotonin.data.preference.SharedPreferenceManager
import com.example.projectserotonin.regimen.RegimenActivity
import com.example.projectserotonin.utils.PrefUtils
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(private val repository: DashboardRepository) :
    ViewModel() {

    var pagerCount = 2
    private var _dashboardResponse: MutableStateFlow<DashboardResponse?> = MutableStateFlow(null)
    var dashboardResponse = _dashboardResponse.asStateFlow()

    private fun parseDashboardJson() {
        val jsonString = CommonUtils().readJsonFromAssets(
            context = MyApplication.appContext,
            fileName = "dashboard.json"
        )
        val result = Gson().fromJson(jsonString,
            object : TypeToken<DashboardResponse>() {}.type) as DashboardResponse
        _dashboardResponse.value = result
        saveSupplementsInLocal(result)
        SharedPreferenceManager(MyApplication.appContext).saveBoolean(PrefUtils.DASHBOARD_API_FETCHED_FLAG,true)
        Log.i("Pakka", "Response $result")
    }

    fun loadData() {
        if (SharedPreferenceManager(MyApplication.appContext).getBoolean(PrefUtils.DASHBOARD_API_FETCHED_FLAG)
                .not()
        ) {
            parseDashboardJson()
        } else {
            fetchSupplementsFromLocal()
        }
    }

    fun navigateToRegimen(context: Context) {
        val intent = Intent(context, RegimenActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        context.startActivity(intent)
    }

    private fun saveSupplementsInLocal(response : DashboardResponse) {
        SharedPreferenceManager(MyApplication.appContext).saveDashboardResponse(response)
    }

    private fun fetchSupplementsFromLocal()  {
        viewModelScope.launch(Dispatchers.Default) {
            repository.fetchDashboardData().onEach {
                it?.let {
                    _dashboardResponse.value = it
                }
            }.launchIn(viewModelScope)
        }

    }

    fun checkAndRefreshData() {
        if (SharedPreferenceManager(MyApplication.appContext).getBoolean(PrefUtils.IS_HOME_REFRESH_NEEDED)) {
            loadData()
        }
    }
}
