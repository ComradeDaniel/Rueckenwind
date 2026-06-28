package com.windrad.ui.collection

data class CollectionUiState(
    val collectedCount: Int = 0,
    val windparksCount: Int = 0,
    val householdsCount: Int = 0,
    val mwhValue: Double = 0.0,
    val co2Value: Double = 0.0,
    val coolingWaterValue: Double = 0.0,
    val ecoAreaValue: Double = 0.0,
    val eCarChargesValue: Double = 0.0,
    val fullLoadHoursValue: Double = 0.0,
)
