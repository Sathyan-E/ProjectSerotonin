package com.example.projectserotonin.data

import java.io.Serializable

data class DashboardResponse(
    var supplements: SupplementResponse,
    var userAddedSupplements: List<UserAddedSupplement>
): Serializable

data class SupplementResponse(
    var all: List<Supplement>
) : Serializable

data class Supplement(
    var active: Boolean,
    var brand: String,
    var consumed: Boolean,
    var consumption: List<Any>?,
    var consumptionWithType: List<Any>?,
    var foundation: Boolean,
    var id: String,
    var image: String,
    var lastSyncedAt: Any,
    var level: Int,
    var name: String,
    var productId: String,
    var regimen: Regimen,
    var status: String
) : Serializable


data class Product(
    var brand: String,
    var category: String,
    var id: String,
    var imageLink: String,
    var name: String,
    var netQuantity: Int,
    var netQuantityUnit: String,
    var servingSize: Int,
    var servingUnit: String,
    var source: String
) : Serializable

data class Regimen(
    var briefDesc: Any,
    var category: Any,
    var categoryIcon: Any,
    var description: String,
    var fillPct: Int,
    var foodCode: String,
    var name: String,
    var pausePct: Any,
    var required: Boolean,
    var servingSize: Int,
    var servingUnit: String,
    var split: Boolean,
    var status: String,
    var timeCode: String,
    var type: String,
    var typeDesc: String
) : Serializable


data class UserAddedSupplement(
    var canConsume: Boolean,
    var consumed: Boolean,
    var dataNotAvailable: Boolean,
    var id: String,
    var noDataResponse: Any,
    var product: Product,
    var regimen: Regimen,
    var showInfoIcon: Boolean
) : Serializable