package com.windrad.ui.collection.kpi

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import com.windrad.ui.collection.CollectionUiState
import com.windrad.ui.theme.AppColors
import com.windrad.ui.theme.WindradTheme

@Composable
fun StromerzeugungSheet(
    uiState: CollectionUiState,
    onDismiss: () -> Unit,
    onInfoClicked: () -> Unit = {},
) {
    KpiBottomSheet(
        title = "Stromerzeugung",
        accentColor = AppColors.MapsBlue,
        accentEmoji = "⚡",
        onDismiss = onDismiss,
        onInfoClicked = onInfoClicked,
    ) {
        val energyDescription = buildAnnotatedString {
            append("reicht aus, um rund ")
            withStyle(SpanStyle(color = AppColors.MapsBlue)) {
                append(uiState.householdsCount.toString())
                append(" Haushalte")
            }
            append(" für ein ganzes Jahr mit grüner Energie zu versorgen.")
        }

        MetricRow(
            title = "Erzeugte Energie",
            value = uiState.mwhValue,
            unit = "MWh",
            pillColor = AppColors.Yellow,
            description = energyDescription,
            indicator = { MwhBarChart(value = uiState.mwhValue) },
        )

        KpiSectionDivider()

        MetricRow(
            title = "Versorgung E-Mobilität",
            value = uiState.eCarChargesValue,
            unit = "E-Autos",
            pillColor = AppColors.Green,
            description = "können pro Jahr mit deiner gesammelten Windenergie einmal komplett aufgeladen werden.",
            indicator = {
                BatteryIndicator(
                    filledCount = filledSlotCount(
                        uiState.eCarChargesValue,
                        E_CAR_CHARGES_MAX,
                        5,
                    ),
                )
            },
        )

        KpiSectionDivider()

        MetricRow(
            title = "Windpark-Auslastung",
            value = uiState.fullLoadHoursValue,
            unit = "h",
            pillColor = AppColors.KpiRed,
            description = "drehen sich deine gesammelten Windräder jährlich unter maximaler Auslastung für den Klimaschutz.",
            indicator = {
                WindmillIndicator(
                    filledCount = filledSlotCount(
                        uiState.fullLoadHoursValue,
                        FULL_LOAD_HOURS_MAX,
                        4,
                    ),
                )
            },
        )
    }
}

@Preview
@Composable
private fun StromerzeugungSheetPreview() {
    WindradTheme {
        StromerzeugungSheet(
            uiState = CollectionUiState(
                collectedCount = 46,
                mwhValue = 1240.0,
                eCarChargesValue = 16_848.0,
                fullLoadHoursValue = 80_776.0,
            ),
            onDismiss = {},
        )
    }
}
