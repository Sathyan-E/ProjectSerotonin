package com.example.projectserotonin.regimen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectserotonin.MyApplication
import com.example.projectserotonin.data.Item
import com.example.projectserotonin.data.ItemsToConsume
import com.example.projectserotonin.utils.CommonUtils
import com.example.projectserotonin.data.RegimenResponse
import com.example.projectserotonin.data.TodayConsumption
import com.example.projectserotonin.data.preference.RegimenDataResponse
import com.example.projectserotonin.data.preference.SharedPreferenceManager
import com.example.projectserotonin.utils.PrefUtils
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


enum class RegimenConsumptionType {
    DAYTIME, ANY, NIGHT_TIME, ON_DEMAND, PAUSED, ADDITIONAL
}

sealed interface UiAction {
    data class ConsumptionDeleteAction(
        val productId: String,
        val isPositive: Boolean
    ) : UiAction
}


@HiltViewModel
class RegimenViewModel @Inject constructor(val repo: RegimenRepository) : ViewModel() {
    private var _regimenResponse: MutableStateFlow<RegimenDataResponse?> = MutableStateFlow(null)
    var regimenResponse = _regimenResponse.asStateFlow()

    private var _showAlert = MutableStateFlow(false)
    var showAlert = _showAlert.asStateFlow()

    var currentSection : String? = null
    var currentSupplementItem : Item? = null

    var _showCustomToast = MutableStateFlow(false)
    var showCustomToast = _showCustomToast.asStateFlow()

    var _showConsumedAnimationView = MutableStateFlow(false)
    var showConsumedAnimationView = _showConsumedAnimationView.asStateFlow()

    var _showAppBar = MutableStateFlow(true)
    var showAppBar = _showAppBar.asStateFlow()


    init {
        checkForRefreshData()
    }

    fun updateShowCustomToastFlag(flag: Boolean) {
        Log.i("Pakka", "updateShowCustomToastFlag $flag")
        _showCustomToast.value = flag
    }

    fun updateShowAppBar(flag: Boolean) {
        _showAppBar.value = flag
    }

    fun checkForRefreshData() {
        if (SharedPreferenceManager(MyApplication.appContext).getBoolean(PrefUtils.REGIMEN_API_FETCHED_FLAG)
                .not()
        ) {
            parseRegimenJson()
        } else {
            // Fetch It from Local
            getRegimenData()
        }
    }

   private fun saveRegimenData(data: List<ItemsToConsume>) {
       SharedPreferenceManager(MyApplication.appContext).saveRegimenData(data)
   }

    private fun getRegimenData() {
        viewModelScope.launch(Dispatchers.Default) {
            repo.fetchRegimentData().onEach {
                it?.let {
                    _regimenResponse.value = RegimenDataResponse(it)
                }
            }.launchIn(viewModelScope)
        }
    }

    private fun parseRegimenJson() {
        val context = MyApplication.appContext
        val jsonString =
            CommonUtils().readJsonFromAssets(context = context, fileName = "regimen.json")
        val result = Gson().fromJson(jsonString,
            object : TypeToken<RegimenResponse>() {}.type
        ) as RegimenResponse
        result.data.let {
            _regimenResponse.value = RegimenDataResponse( it.itemsToConsume.toMutableList())
            saveRegimenData(it.itemsToConsume)
            SharedPreferenceManager(context).saveBoolean(key = PrefUtils.REGIMEN_API_FETCHED_FLAG, true)
        }
        Log.i("Pakka", "Response $result")
    }


    fun deleteConsumptionTapped(isPositive: Boolean = true, currentItem: Item? = null, section: String? = null) {
        currentSupplementItem = currentItem
        currentSection = section
        _showAlert.value = isPositive
    }

    fun consumedSupplement(item: Item, section: String, isPositive: Boolean) {
        currentSupplementItem = item
        currentSection = section
        _showAlert.value = false
        viewModelScope.launch {
            var list = _regimenResponse.value?.list
            if (!list.isNullOrEmpty()) {
                var sectionIndex = (list.indexOfFirst { it.code == section })
                val supplmentIndex = list[sectionIndex].items.indexOfFirst { it.product.id == item.product.id }
                val exactItem = list[sectionIndex].items[supplmentIndex]
                if (isPositive) {
                    var todayConsumption = TodayConsumption(date = CommonUtils().getFormattedDateStringFromDate(System.currentTimeMillis()),exactItem.product.regimen.servingSize,null,null,CommonUtils().getFormattedTimeStringFromDate(System.currentTimeMillis()),null,null,exactItem.product.regimen.servingUnit)
                    exactItem.todayConsumption = todayConsumption
                } else {
                    exactItem.todayConsumption = null
                }
                // To Update the Dashboard Data
                repo.updateDashboardData(item,isPositive)
                repo.updateSupplement(exactItem,section).onEach {
                    it?.let {
                        _regimenResponse.value = RegimenDataResponse(it)
                        if (isPositive) {
                            showToast()
                        }
                    }
                }.launchIn(viewModelScope)
            }
        }
    }

    suspend fun showToast() {
        _showCustomToast.value = true
        delay(1500)
        _showCustomToast.value = false
    }

    fun updateConsumptionAnimatedView(flag: Boolean) {
        _showConsumedAnimationView.value = flag
    }

    fun updateCurrentItem(item: Item) {
        currentSupplementItem = item
    }

    fun showConsumedAnimationView(item: Item) {
        updateCurrentItem(item)
        _showConsumedAnimationView.value = true
    }

}

