package com.windrad.ui.collection.kpi

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.windrad.ui.theme.AppColors
import kotlin.math.roundToInt

// ── Constants ─────────────────────────────────────────────────────────────────

internal const val MWH_BAR_COUNT = 11
internal const val CO2_BAR_COUNT = 12
internal const val CO2_METRIC_MAX = 1000.0
internal const val MWH_METRIC_MAX = 1000.0
internal const val COOLING_WATER_MAX = 1500.0
internal const val ECO_AREA_MAX = 120.0
internal const val E_CAR_CHARGES_MAX = 80_000.0
internal const val FULL_LOAD_HOURS_MAX = 3000.0

private val mwhBarHeightFractions = listOf(
    0.298f, 0.383f, 0.447f, 0.511f, 0.574f, 0.660f, 0.724f, 0.808f, 0.872f, 0.937f, 1.0f,
)

private val co2BarHeightFractions = listOf(
    0.667f, 0.667f, 0.667f, 0.667f, 0.667f, 0.667f, 0.667f, 0.667f, 0.667f,
    0.741f, 0.852f, 1.0f,
)

// ── Formatting ────────────────────────────────────────────────────────────────

internal fun formatGermanNumber(value: Double): String {
    val rounded = value.roundToInt()
    return rounded.toString().reversed().chunked(3).joinToString(".").reversed()
}

internal fun formatMetricValue(value: Double): String =
    if (value <= 0) "0" else formatGermanNumber(value)

internal fun filledSlotCount(value: Double, max: Double, slots: Int): Int {
    if (value <= 0 || max <= 0) return 0
    return ((value / max) * slots).roundToInt().coerceIn(0, slots)
}

// ── Sheet shell ───────────────────────────────────────────────────────────────

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KpiBottomSheet(
    title: String,
    accentColor: Color,
    accentEmoji: String,
    onDismiss: () -> Unit,
    onInfoClicked: () -> Unit = {},
    content: @Composable ColumnScope.() -> Unit,
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        dragHandle = null,
        shape = RoundedCornerShape(topStart = 27.dp, topEnd = 27.dp),
        containerColor = Color.White,
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .clip(RoundedCornerShape(topStart = 27.dp, bottomEnd = 27.dp))
                    .background(accentColor)
                    .align(Alignment.TopStart),
                contentAlignment = Alignment.Center,
            ) {
                Text(text = accentEmoji, fontSize = 28.sp)
            }

            Column(modifier = Modifier.fillMaxWidth()) {
                Spacer(modifier = Modifier.height(5.dp))
                Box(
                    modifier = Modifier
                        .width(36.dp)
                        .height(5.dp)
                        .clip(RoundedCornerShape(100.dp))
                        .background(Color(0xFFCCCCCC))
                        .align(Alignment.CenterHorizontally),
                )
                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Spacer(modifier = Modifier.width(44.dp))
                    Row(
                        modifier = Modifier.weight(1f),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = title,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = AppColors.PureDark,
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "ⓘ",
                            fontSize = 16.sp,
                            color = Color(0xFF757575),
                            modifier = Modifier.clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null,
                                onClick = onInfoClicked,
                            ),
                        )
                    }
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(RoundedCornerShape(50))
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null,
                                onClick = onDismiss,
                            ),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = "✕",
                            fontSize = 17.sp,
                            color = Color(0xFF727272),
                            fontWeight = FontWeight.Medium,
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 24.dp)
                        .padding(bottom = 120.dp),
                    content = content,
                )
            }
        }
    }
}

// ── Metric row ────────────────────────────────────────────────────────────────

@Composable
fun MetricRow(
    title: String,
    value: Double,
    unit: String,
    pillColor: Color,
    description: AnnotatedString,
    indicator: @Composable () -> Unit,
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = title,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
        )
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            ValuePill(value = value, unit = unit, color = pillColor)
            Spacer(modifier = Modifier.weight(1f))
            indicator()
        }
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = description,
            fontSize = 18.sp,
            color = Color.Black,
            lineHeight = 22.sp,
        )
    }
}

@Composable
fun MetricRow(
    title: String,
    value: Double,
    unit: String,
    pillColor: Color,
    description: String,
    indicator: @Composable () -> Unit,
) {
    MetricRow(
        title = title,
        value = value,
        unit = unit,
        pillColor = pillColor,
        description = AnnotatedString(description),
        indicator = indicator,
    )
}

@Composable
fun KpiSectionDivider() {
    Spacer(modifier = Modifier.height(18.dp))
    HorizontalDivider(color = Color(0xFFE6E6E6), thickness = 1.dp)
    Spacer(modifier = Modifier.height(18.dp))
}

@Composable
fun ValuePill(
    value: Double,
    unit: String,
    color: Color,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(1000.dp))
            .background(color)
            .padding(horizontal = 12.dp, vertical = 5.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(7.dp),
    ) {
        Text(
            text = formatMetricValue(value),
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
        )
        Text(
            text = unit,
            fontSize = 16.sp,
            color = Color.White,
        )
    }
}

// ── Bar charts ────────────────────────────────────────────────────────────────

@Composable
fun MwhBarChart(value: Double, modifier: Modifier = Modifier) {
    val fillColor = mwhBarColor(value)
    val filledCount = mwhFilledBarCount(value)

    Canvas(modifier = modifier.height(32.dp).width(99.dp)) {
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
                cap = StrokeCap.Round,
            )
        }
    }
}

@Composable
fun Co2BarChart(value: Double, modifier: Modifier = Modifier) {
    val indicatorBarIndex = co2IndicatorBarIndex(value)

    Canvas(modifier = modifier.height(48.dp).width(124.dp)) {
        val triangleAreaHeight = 10.dp.toPx()
        val barsAreaHeight = size.height - triangleAreaHeight
        val barWidth = 4.dp.toPx()
        val totalSpan = size.width
        val spacing = (totalSpan - barWidth * CO2_BAR_COUNT) / (CO2_BAR_COUNT - 1)
        val bottom = size.height

        val targetX = indicatorBarIndex * (barWidth + spacing) + barWidth / 2f
        val triSize = 6.dp.toPx()
        val triPath = Path().apply {
            moveTo(targetX - triSize / 2f, 0f)
            lineTo(targetX + triSize / 2f, 0f)
            lineTo(targetX, triangleAreaHeight)
            close()
        }
        drawPath(triPath, AppColors.Gray)

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
                cap = StrokeCap.Round,
            )
        }
    }
}

private fun mwhBarColor(value: Double): Color = when {
    value <= 0 -> AppColors.Gray
    value < 50 -> AppColors.MetricRed
    value < 500 -> AppColors.MetricOrange
    value < 1000 -> AppColors.Yellow
    else -> AppColors.Green
}

private fun mwhFilledBarCount(value: Double): Int {
    if (value <= 0) return 0
    return ((value / MWH_METRIC_MAX) * MWH_BAR_COUNT)
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

// ── Icon indicator bar (droplets, trees) ────────────────────────────────────

@Composable
fun IconIndicatorBar(
    filledCount: Int,
    totalSlots: Int,
    filledIcon: String,
    emptyIcon: String,
    accentColor: Color,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(100.dp))
            .background(AppColors.IndicatorBg)
            .padding(horizontal = 6.dp, vertical = 6.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        repeat(totalSlots) { index ->
            val filled = index < filledCount
            Text(
                text = if (filled) filledIcon else emptyIcon,
                fontSize = 18.sp,
                color = if (filled) accentColor else accentColor.copy(alpha = 0.35f),
            )
        }
    }
}

// ── Battery indicator ─────────────────────────────────────────────────────────

@Composable
fun BatteryIndicator(
    filledCount: Int,
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .width(107.dp)
                .height(32.dp),
        ) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .clip(RoundedCornerShape(7.dp))
                    .background(AppColors.IndicatorBg),
            )
            listOf(6, 25, 44, 63, 82).forEachIndexed { index, xDp ->
                if (index < filledCount) {
                    val cellColor = if (index <= 2) AppColors.Green else AppColors.MetricMint
                    val shape = when (index) {
                        0 -> RoundedCornerShape(topStart = 4.dp, bottomStart = 4.dp)
                        4 -> RoundedCornerShape(topEnd = 4.dp, bottomEnd = 4.dp)
                        else -> RoundedCornerShape(0.dp)
                    }
                    Box(
                        modifier = Modifier
                            .offset(x = xDp.dp, y = 5.dp)
                            .width(13.dp)
                            .height(22.dp)
                            .clip(shape)
                            .background(cellColor),
                    )
                }
            }
        }
        Box(
            modifier = Modifier
                .width(8.dp)
                .height(15.dp)
                .clip(RoundedCornerShape(topEnd = 4.dp, bottomEnd = 4.dp))
                .background(AppColors.IndicatorBg),
        )
    }
}

// ── Windmill indicator ────────────────────────────────────────────────────────

@Composable
fun WindmillIndicator(
    filledCount: Int,
    totalSlots: Int = 4,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(100.dp))
            .background(AppColors.IndicatorBg)
            .padding(horizontal = 8.dp, vertical = 5.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        repeat(totalSlots) { index ->
            WindmillIcon(filled = index < filledCount)
        }
    }
}

@Composable
private fun WindmillIcon(filled: Boolean, modifier: Modifier = Modifier) {
    val color = AppColors.KpiRed
    Canvas(modifier = modifier.size(15.dp, 22.dp)) {
        val cx = size.width / 2f
        val hubY = size.height * 0.38f
        val hubRadius = size.width * 0.12f
        val bladeLen = size.width * 0.45f

        if (filled) {
            drawCircle(color = color, radius = hubRadius, center = Offset(cx, hubY))
            drawLine(
                color = color,
                start = Offset(cx, hubY),
                end = Offset(cx, size.height),
                strokeWidth = 2.dp.toPx(),
                cap = StrokeCap.Round,
            )
            for (angle in listOf(-90f, 30f, 150f)) {
                val rad = angle * kotlin.math.PI / 180.0
                drawLine(
                    color = color,
                    start = Offset(cx, hubY),
                    end = Offset(
                        cx + (kotlin.math.cos(rad) * bladeLen).toFloat(),
                        hubY + (kotlin.math.sin(rad) * bladeLen).toFloat(),
                    ),
                    strokeWidth = 2.5.dp.toPx(),
                    cap = StrokeCap.Round,
                )
            }
        } else {
            drawCircle(
                color = color,
                radius = hubRadius,
                center = Offset(cx, hubY),
                style = Stroke(width = 1.dp.toPx()),
            )
            drawLine(
                color = color,
                start = Offset(cx, hubY),
                end = Offset(cx, size.height),
                strokeWidth = 1.dp.toPx(),
                cap = StrokeCap.Round,
            )
            for (angle in listOf(-90f, 30f, 150f)) {
                val rad = angle * kotlin.math.PI / 180.0
                drawLine(
                    color = color,
                    start = Offset(cx, hubY),
                    end = Offset(
                        cx + (kotlin.math.cos(rad) * bladeLen).toFloat(),
                        hubY + (kotlin.math.sin(rad) * bladeLen).toFloat(),
                    ),
                    strokeWidth = 1.dp.toPx(),
                    cap = StrokeCap.Round,
                )
            }
        }
    }
}
