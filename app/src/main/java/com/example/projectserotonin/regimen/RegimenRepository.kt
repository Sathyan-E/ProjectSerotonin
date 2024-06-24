package com.example.projectserotonin.regimen

import com.example.projectserotonin.MyApplication
import com.example.projectserotonin.data.DashboardResponse
import com.example.projectserotonin.data.Item
import com.example.projectserotonin.data.ItemsToConsume
import com.example.projectserotonin.data.preference.RegimenDataResponse
import com.example.projectserotonin.data.preference.SharedPreferenceManager
import com.example.projectserotonin.utils.PrefUtils
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RegimenRepository @Inject constructor() {

    suspend fun updateSupplement(item: Item, section: String): Flow<List<ItemsToConsume>?> = flow {
        val regimenDataList = SharedPreferenceManager(MyApplication.appContext).getRegimenData()
        if (regimenDataList != null) {
            var sectionIndex = (regimenDataList.indexOfFirst { it.code == section })
            val supplmentIndex =
                regimenDataList[sectionIndex].items.indexOfFirst { it.product.id == item.product.id }
            regimenDataList[sectionIndex].items[supplmentIndex] = item
            SharedPreferenceManager(MyApplication.appContext).saveRegimenData(regimenDataList)
            delay(500)
            emit(SharedPreferenceManager(MyApplication.appContext).getRegimenData())
        } else {
            emit(null)
        }

    }

    suspend fun fetchRegimentData(): Flow<List<ItemsToConsume>?> = flow {
        val regimenData = SharedPreferenceManager(MyApplication.appContext).getRegimenData()
        if (regimenData != null) {
            emit(regimenData)
        } else {
            emit(null)
        }
    }

    fun updateDashboardData(item: Item, isPositive: Boolean) {
        var dashboardData = SharedPreferenceManager(MyApplication.appContext).getDashboardResponse()
        val supplementId = item.product.id
        dashboardData?.let { dashboardResponse ->
            val additionalSupplementIndex =
                dashboardResponse.userAddedSupplements.indexOfFirst { it.id == supplementId }
            val recommendedSupplementIndex =
                dashboardResponse.supplements.all.indexOfFirst { it.id == supplementId }
            if (additionalSupplementIndex != -1 && additionalSupplementIndex < dashboardResponse.userAddedSupplements.size) {
                dashboardResponse.userAddedSupplements[additionalSupplementIndex].consumed =
                    isPositive
            } else if (recommendedSupplementIndex != -1 && recommendedSupplementIndex < dashboardResponse.supplements.all.size) {
                dashboardResponse.supplements.all[recommendedSupplementIndex].consumed = isPositive
            }
            saveDashboardData(dashboardResponse)
        }
    }

    fun saveDashboardData(dashboardResponse: DashboardResponse) {
        SharedPreferenceManager(MyApplication.appContext).saveDashboardResponse(dashboardResponse)
        SharedPreferenceManager(MyApplication.appContext).saveBoolean(PrefUtils.IS_HOME_REFRESH_NEEDED,true)
    }

}