package com.example.projectserotonin.data

import java.io.Serializable

data class RegimenResponse(
    var `data`: Data
) :Serializable

data class Data(
    var config: Config,
    var itemsToConsume: List<ItemsToConsume>,
    var status: String,
    var statusInfo: Any
) :Serializable

data class Config(
    var dock: Boolean,
    var showVideo: Boolean,
    var videoUrl: String
) :Serializable


data class ItemsToConsume(
    var code: String,
    var completed: Boolean,
    var items: ArrayList<Item>,
    var regular: Boolean,
    var title: String
) : Serializable

 data class Item(
    var canPause: Boolean,
    var canReorder: Boolean,
    var consumptions: List<Consumption>,
    var dataNotAvailable: Boolean,
    var fillIcon: String,
    var fillPct: Int,
    var infoText: String,
    var interactions: List<Interaction>,
    var latestConsumption: LatestConsumption,
    var maxServingSize: Int,
    var minServingSize: Int,
    var noDataResponse: Any,
    var product: ProductX,
    var shippingInDays: Any,
    var showInfoIcon: Boolean,
    var todayConsumption: TodayConsumption?
) :Serializable




data class Consumption(
    var date: String,
    var dosage: Int,
    var qualifier: Any,
    var text: Any,
    var time: String,
    var timestamp: String,
    var type: String,
    var unit: String
) :Serializable

data class LatestConsumption(
    var date: String,
    var dosage: Int,
    var qualifier: Any,
    var text: Any,
    var time: String,
    var timestamp: String,
    var type: String,
    var unit: String
) :Serializable

data class Interaction(
    var count: Int,
    var icon: String,
    var text: String,
    var type: String
) :Serializable

data class TodayConsumption(
    var date: String?,
    var dosage: Int?,
    var qualifier: Any?,
    var text: Any?,
    var time: String?,
    var timestamp: String?,
    var type: String?,
    var unit: String?
) :Serializable

data class ProductX(
    var active: Boolean,
    var brand: String,
    var consumed: Boolean,
    var consumption: List<Any>,
    var consumptionWithType: List<Any>,
    var foundation: Boolean,
    var id: String,
    var image: String,
    var lastSyncedAt: Any,
    var level: Any,
    var name: String,
    var productId: String,
    var regimen: RegimenX,
    var status: String
) :Serializable

data class RegimenX(
    val briefDesc: Any,
    val category: Any,
    val categoryIcon: Any,
    val description: String,
    val fillPct: Int,
    val foodCode: String,
    val name: String,
    val pausePct: Int,
    val required: Boolean,
    val servingSize: Int,
    val servingUnit: String,
    val split: Boolean,
    val status: String,
    val timeCode: String,
    val type: String,
    val typeDesc: String
) :Serializable