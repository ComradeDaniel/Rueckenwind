package com.windrad.ui.collection.kpi

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.windrad.ui.collection.CollectionUiState
import com.windrad.ui.collection.CollectionViewModel
import com.windrad.ui.theme.AppColors
import com.windrad.ui.theme.WindradTheme

@Composable
fun NachhaltigkeitSheet(
    uiState: CollectionUiState,
    onDismiss: () -> Unit,
    onInfoClicked: () -> Unit = {},
) {
    KpiBottomSheet(
        title = "Nachhaltigkeit",
        accentColor = AppColors.Co2Green,
        accentEmoji = "🍃",
        onDismiss = onDismiss,
        onInfoClicked = onInfoClicked,
    ) {
        MetricRow(
            title = "CO₂-Ausstoß",
            value = uiState.co2Value,
            unit = "t CO₂",
            pillColor = AppColors.Yellow,
            description = "weniger CO₂-Ausstoß - Dank deiner gesammelten Windparks.",
            indicator = { Co2BarChart(value = uiState.co2Value) },
        )

        KpiSectionDivider()

        MetricRow(
            title = "Eingespartes Kühlwasser",
            value = uiState.coolingWaterValue,
            unit = "t",
            pillColor = AppColors.MapsBlue,
            description = "eingespartes Wasser. Anders als bei der Kühlung konventioneller Kraftwerke.",
            indicator = {
                IconIndicatorBar(
                    filledCount = filledSlotCount(
                        uiState.coolingWaterValue,
                        COOLING_WATER_MAX,
                        5,
                    ),
                    totalSlots = 5,
                    filledIcon = "💧",
                    emptyIcon = "💧",
                    accentColor = AppColors.MapsBlue,
                )
            },
        )

        KpiSectionDivider()

        MetricRow(
            title = "Ökologische Ausgleichsfläche",
            value = uiState.ecoAreaValue,
            unit = "km²",
            pillColor = AppColors.Green,
            description = "für deine gesammelten Windräder, z. B. Aufforstung, Anlegen von Blühwiesen oder Renaturierung von Bächen.",
            indicator = {
                IconIndicatorBar(
                    filledCount = filledSlotCount(
                        uiState.ecoAreaValue,
                        ECO_AREA_MAX,
                        4,
                    ),
                    totalSlots = 4,
                    filledIcon = "🌲",
                    emptyIcon = "🌲",
                    accentColor = AppColors.Green,
                )
            },
        )
    }
}

@Preview
@Composable
private fun NachhaltigkeitSheetPreview() {
    WindradTheme {
        NachhaltigkeitSheet(
            uiState = CollectionUiState(
                collectedCount = 46,
                co2Value = CollectionViewModel.CO2_PLACEHOLDER,
                coolingWaterValue = CollectionViewModel.COOLING_WATER_PLACEHOLDER,
                ecoAreaValue = CollectionViewModel.ECO_AREA_PLACEHOLDER,
            ),
            onDismiss = {},
        )
    }
}
