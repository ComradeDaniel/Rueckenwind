package com.windrad.ui.collection

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.windrad.ui.components.InfoAlertDialog
import com.windrad.ui.navigation.WindradDestination
import com.windrad.ui.theme.AppColors
import com.windrad.ui.theme.WindradTheme
import kotlin.math.roundToInt

// ── Bar chart constants ────────────────────────────────────────────────────────

private const val MWH_BAR_COUNT = 11
private const val CO2_BAR_COUNT = 12
private const val CO2_METRIC_MAX = 1000.0

private val mwhBarHeightFractions = listOf(
    0.298f, 0.383f, 0.447f, 0.511f, 0.574f, 0.660f, 0.724f, 0.808f, 0.872f, 0.937f, 1.0f,
)

private val co2BarHeightFractions = listOf(
    0.667f, 0.667f, 0.667f, 0.667f, 0.667f, 0.667f, 0.667f, 0.667f, 0.667f,
    0.741f, 0.852f, 1.0f,
)

// ── Screen ────────────────────────────────────────────────────────────────────

@Composable
fun CollectionScreen(
    uiState: CollectionUiState,
    onViewCollectionClicked: () -> Unit = {},
    onMwhCardClicked: () -> Unit = {},
    onCo2CardClicked: () -> Unit = {},
    onFaqClicked: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    var showInfoDialog by remember { mutableStateOf(false) }
    val isEmpty = uiState.collectedCount == 0

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState()),
    ) {
        // ── Title row ──────────────────────────────────────────────────────────
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 50.dp, start = 20.dp, end = 20.dp),
        ) {
            Text(
                text = "Persönliche Übersicht",
                modifier = Modifier.align(Alignment.Center),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
            )
            Box(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .size(26.dp)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                    ) { showInfoDialog = true },
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "ⓘ",
                    fontSize = 18.sp,
                    color = Color(0xFF757575),
                )
            }
        }

        // ── Stats row ─────────────────────────────────────────────────────────
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 23.dp),
            horizontalArrangement = Arrangement.spacedBy(0.dp),
        ) {
            Column(modifier = Modifier.width(150.dp)) {
                Text(
                    text = if (!isEmpty) formatGermanNumber(uiState.windparksCount.toDouble()) else "0",
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold,
                    color = AppColors.MapsBlue,
                    lineHeight = 44.sp,
                )
                Text(
                    text = "Windparks\nentdeckt",
                    fontSize = 20.sp,
                    color = AppColors.SecondaryLabel,
                    lineHeight = 22.sp,
                )
            }
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = if (!isEmpty) formatGermanNumber(uiState.householdsCount.toDouble()) else "0",
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold,
                    color = AppColors.MapsBlue,
                    lineHeight = 44.sp,
                )
                Text(
                    text = "Haushalte mit grüner Energie versorgt",
                    fontSize = 18.sp,
                    color = AppColors.SecondaryLabel,
                    lineHeight = 22.sp,
                )
            }
        }

        // ── Image + card overlap section ──────────────────────────────────────
        Box(modifier = Modifier.fillMaxWidth()) {
            // Placeholder image (edge-to-edge)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(218.dp)
                    .background(AppColors.LightGray),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "Netz-Wachstum",
                    fontSize = 16.sp,
                    color = AppColors.SubtitleText,
                )
            }

            // White card sliding up over the image (starts 19dp before image ends)
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 7.dp)
                    .padding(top = 199.dp),
                shape = RoundedCornerShape(20.dp),
                color = Color.White,
                shadowElevation = 8.dp,
                border = androidx.compose.foundation.BorderStroke(
                    width = 1.dp,
                    color = Color(0x808A8A8E),
                ),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .padding(top = 20.dp, bottom = 120.dp),
                ) {
                    // KPI cards
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                    ) {
                        KpiMetricCard(
                            modifier = Modifier.weight(1f),
                            accentColor = AppColors.MapsBlue,
                            iconEmoji = "⚡",
                            value = uiState.mwhValue,
                            unit = "MWh",
                            barChart = {
                                MwhBarChart(
                                    modifier = it,
                                    value = uiState.mwhValue,
                                )
                            },
                            onClick = onMwhCardClicked,
                        )
                        KpiMetricCard(
                            modifier = Modifier.weight(1f),
                            accentColor = AppColors.Co2Green,
                            iconEmoji = "🍃",
                            value = uiState.co2Value,
                            unit = "t CO₂",
                            barChart = {
                                Co2BarChart(
                                    modifier = it,
                                    value = uiState.co2Value,
                                )
                            },
                            onClick = onCo2CardClicked,
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // Description text
                    DescriptionText(isEmpty = isEmpty, householdsCount = uiState.householdsCount)

                    Spacer(modifier = Modifier.height(20.dp))

                    // "Zur gesamten Sammlung" button (centered)
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center,
                    ) {
                        Row(
                            modifier = Modifier
                                .clip(RoundedCornerShape(1000.dp))
                                .background(AppColors.MapsBlue)
                                .clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = null,
                                    onClick = onViewCollectionClicked,
                                )
                                .padding(horizontal = 20.dp, vertical = 14.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                        ) {
                            Icon(
                                imageVector = WindradDestination.COLLECTION.icon,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(24.dp),
                            )
                            Text(
                                text = "Zur gesamten Sammlung",
                                fontSize = 17.sp,
                                color = Color.White,
                                fontWeight = FontWeight.Normal,
                            )
                        }
                    }
                }
            }
        }
    }

    // ── Info dialog ────────────────────────────────────────────────────────────
    if (showInfoDialog) {
        InfoAlertDialog(
            title = "Hinweis zu den Zahlen",
            message = "Bei diesen Berechnungen arbeiten wir mit Näherungswerten. Weitere Infos findest Du in unserem FAQ.",
            confirmText = "Ok",
            dismissText = "Zum FAQ",
            onConfirm = { showInfoDialog = false },
            onDismissRequest = { showInfoDialog = false },
            onDismissButtonClick = {
                showInfoDialog = false
                onFaqClicked()
            },
        )
    }
}

// ── KPI card ──────────────────────────────────────────────────────────────────

@Composable
private fun KpiMetricCard(
    accentColor: Color,
    iconEmoji: String,
    value: Double,
    unit: String,
    barChart: @Composable (modifier: Modifier) -> Unit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val cardShape = RoundedCornerShape(12.dp)
    val accentCornerShape = RoundedCornerShape(topStart = 12.dp, bottomEnd = 12.dp)

    Box(
        modifier = modifier
            .height(121.dp)
            .shadow(elevation = 0.dp, shape = cardShape)
            .clip(cardShape)
            .border(width = 1.dp, color = accentColor, shape = cardShape)
            .background(Color.White)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick,
            ),
    ) {
        // Accent corner square with icon
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(accentCornerShape)
                .background(accentColor),
            contentAlignment = Alignment.Center,
        ) {
            Text(text = iconEmoji, fontSize = 16.sp)
        }

        // Chevron › top-right
        Text(
            text = "›",
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(end = 8.dp, top = 9.dp),
            fontSize = 15.sp,
            color = Color(0xFF727272),
        )

        // Value + unit stacked (starting to the right of accent square)
        Column(
            modifier = Modifier
                .padding(start = 48.dp, top = 13.dp),
        ) {
            Text(
                text = formatMetricValue(value),
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = accentColor,
                lineHeight = 32.sp,
            )
            Text(
                text = unit,
                fontSize = 20.sp,
                color = accentColor,
                lineHeight = 22.sp,
            )
        }

        // Bar chart at the bottom of the card
        barChart(
            Modifier
                .align(Alignment.BottomStart)
                .padding(start = 30.dp, end = 14.dp, bottom = 8.dp)
                .height(34.dp)
                .fillMaxWidth(),
        )
    }
}

// ── Bar charts ────────────────────────────────────────────────────────────────

@Composable
private fun MwhBarChart(value: Double, modifier: Modifier = Modifier) {
    val fillColor = mwhBarColor(value)
    val filledCount = mwhFilledBarCount(value)

    androidx.compose.foundation.Canvas(modifier = modifier) {
        val barWidth = 4.dp.toPx()
        val totalSpan = size.width
        val spacing = (totalSpan - barWidth * MWH_BAR_COUNT) / (MWH_BAR_COUNT - 1)
        val bottom = size.height

        mwhBarHeightFractions.forEachIndexed { i, fraction ->
            val barColor = if (i < filledCount) fillColor else AppColors.Gray
            val x = i * (barWidth + spacing) + barWidth / 2f
            val top = bottom - size.height * fraction
            drawLine(
                color = barColor,
                start = Offset(x, top),
                end = Offset(x, bottom),
                strokeWidth = barWidth,
                cap = androidx.compose.ui.graphics.StrokeCap.Round,
            )
        }
    }
}

@Composable
private fun Co2BarChart(value: Double, modifier: Modifier = Modifier) {
    val indicatorBarIndex = co2IndicatorBarIndex(value)

    androidx.compose.foundation.Canvas(modifier = modifier) {
        val triangleAreaHeight = 10.dp.toPx()
        val barsAreaHeight = size.height - triangleAreaHeight
        val barWidth = 4.dp.toPx()
        val totalSpan = size.width
        val spacing = (totalSpan - barWidth * CO2_BAR_COUNT) / (CO2_BAR_COUNT - 1)
        val bottom = size.height

        // Draw triangle indicator above target bar
        val targetX = indicatorBarIndex * (barWidth + spacing) + barWidth / 2f
        val triSize = 6.dp.toPx()
        val triTop = 0f
        val triBottom = triangleAreaHeight
        val triPath = Path().apply {
            moveTo(targetX - triSize / 2f, triBottom)
            lineTo(targetX + triSize / 2f, triBottom)
            lineTo(targetX, triTop)
            close()
        }
        drawPath(triPath, AppColors.Gray)

        // Draw bars
        co2BarHeightFractions.forEachIndexed { i, fraction ->
            val barColor = co2BarColor(i)
            val x = i * (barWidth + spacing) + barWidth / 2f
            val barHeight = barsAreaHeight * fraction
            val top = bottom - barHeight
            drawLine(
                color = barColor,
                start = Offset(x, top),
                end = Offset(x, bottom),
                strokeWidth = barWidth,
                cap = androidx.compose.ui.graphics.StrokeCap.Round,
            )
        }
    }
}

// ── Description text ──────────────────────────────────────────────────────────

@Composable
private fun DescriptionText(isEmpty: Boolean, householdsCount: Int) {
    if (isEmpty) {
        Text(
            text = "Oh! Hier ist noch nicht viel los. Sammle jetzt Windparks, um dein Netzwerk mit grünem Strom zu versorgen.",
            fontSize = 18.sp,
            color = Color.Black,
            lineHeight = 22.sp,
        )
    } else {
        val householdsFormatted = formatGermanNumber(householdsCount.toDouble())
        val annotated = buildAnnotatedString {
            append("Wow! Du versorgst aktuell mit deiner Sammlung bereits ")
            withStyle(SpanStyle(color = AppColors.MapsBlue)) {
                append("$householdsFormatted Haushalte")
            }
            append(" nur mit Wind-Energie! Sammle noch mehr um dein Netz zu vergrößern.")
        }
        Text(
            text = annotated,
            fontSize = 20.sp,
            color = Color.Black,
            lineHeight = 22.sp,
        )
    }
}

// ── Helpers ───────────────────────────────────────────────────────────────────

private fun mwhBarColor(value: Double): Color = when {
    value <= 0 -> AppColors.Gray
    value < 50 -> AppColors.MetricRed
    value < 500 -> AppColors.MetricOrange
    value < 1000 -> AppColors.Yellow
    else -> AppColors.Green
}

private fun mwhFilledBarCount(value: Double): Int {
    if (value <= 0) return 0
    return ((value / CO2_METRIC_MAX) * MWH_BAR_COUNT)
        .roundToInt()
        .coerceIn(0, MWH_BAR_COUNT)
}

private fun co2BarColor(index: Int): Color = when (index) {
    0 -> AppColors.MetricRed
    in 1..2 -> AppColors.MetricOrange
    in 3..5 -> AppColors.Yellow
    else -> AppColors.Co2Green
}

private fun co2IndicatorBarIndex(value: Double): Int {
    if (value <= 0) return 0
    return ((value / CO2_METRIC_MAX) * (CO2_BAR_COUNT - 1))
        .roundToInt()
        .coerceIn(0, CO2_BAR_COUNT - 1)
}

private fun formatGermanNumber(value: Double): String {
    val rounded = value.roundToInt()
    return rounded.toString().reversed().chunked(3).joinToString(".").reversed()
}

private fun formatMetricValue(value: Double): String =
    if (value <= 0) "0" else formatGermanNumber(value)

// ── Previews ──────────────────────────────────────────────────────────────────

@Preview
@Composable
private fun CollectionScreenEmptyPreview() {
    WindradTheme {
        CollectionScreen(
            uiState = CollectionUiState(
                collectedCount = 0,
                windparksCount = 0,
                householdsCount = 0,
                mwhValue = 0.0,
                co2Value = 0.0,
            ),
        )
    }
}

@Preview
@Composable
private fun CollectionScreenFilledPreview() {
    WindradTheme {
        CollectionScreen(
            uiState = CollectionUiState(
                collectedCount = 46,
                windparksCount = 46,
                householdsCount = CollectionViewModel.HOUSEHOLDS_PLACEHOLDER,
                mwhValue = CollectionViewModel.MWH_PLACEHOLDER,
                co2Value = CollectionViewModel.CO2_PLACEHOLDER,
            ),
        )
    }
}
