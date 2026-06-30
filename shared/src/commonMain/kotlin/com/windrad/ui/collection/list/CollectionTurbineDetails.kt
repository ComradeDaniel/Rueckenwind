package com.windrad.ui.collection.list

data class CollectionTurbineDetails(
    val operatingStatus: String,
    val commissioningDate: String,
    val annualEnergyText: String,
    val householdsText: String,
    val hubHeightMeters: Int?,
    val heightComparisonName: String?,
    val rotorDiameterMeters: Int?,
    val rotorFieldsText: String?,
    val technologyText: String,
    val locationText: String,
    val isOnshore: Boolean,
    val isCollected: Boolean,
)
