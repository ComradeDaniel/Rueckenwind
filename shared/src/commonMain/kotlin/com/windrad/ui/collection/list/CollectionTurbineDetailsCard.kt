package com.windrad.ui.collection.list

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.windrad.ui.map.KölnerDomIcon
import com.windrad.ui.map.SoccerFieldIcon
import com.windrad.ui.map.WindTurbineHorizontalIcon
import com.windrad.ui.map.WindTurbineIcon
import com.windrad.ui.map.WindTurbineLocationIcon
import com.windrad.ui.map.WindTurbineRotorIcon
import com.windrad.ui.theme.AppColors
import com.windrad.ui.theme.WindradTheme

@Composable
fun CollectionTurbineDetailsCard(
    details: CollectionTurbineDetails,
    modifier: Modifier = Modifier,
) {
    val accent = if (details.isCollected) AppColors.MapsBlue else AppColors.SubtitleText

    Column(
        modifier = modifier
            .fillMaxWidth()
            .drawBehind {
                drawLine(
                    color = accent.copy(alpha = 0.35f),
                    start = Offset(0f, 0f),
                    end = Offset(0f, size.height),
                    strokeWidth = 2.dp.toPx(),
                )
            }
            .padding(start = 14.dp, end = 8.dp, bottom = 8.dp),
    ) {
        DetailInfoRow(
            icon = { CollectionPowerIcon(color = accent) },
            title = details.operatingStatus,
            subtitle = details.commissioningDate,
        )

        HorizontalDivider(color = AppColors.LightGray)

        DetailInfoRow(
            icon = { CollectionLightningIcon(color = accent) },
            title = details.annualEnergyText,
            subtitle = details.householdsText,
        )

        HorizontalDivider(
            modifier = Modifier.padding(vertical = 4.dp),
            thickness = 2.dp,
            color = AppColors.LightGray,
        )

        Text(
            text = "Abmessungen",
            fontSize = 17.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.Black,
        )

        Spacer(modifier = Modifier.height(12.dp))

        DimensionRow(
            label = "Höhe:",
            valueText = details.hubHeightMeters?.let { "$it m" } ?: "–",
            accent = accent,
            isVertical = true,
            comparisonName = details.heightComparisonName,
            turbineIcon = {
                WindTurbineIcon(modifier = Modifier.size(28.dp, 42.dp))
            },
            showDomIcon = details.heightComparisonName == "Kölner Dom",
        )

        Spacer(modifier = Modifier.height(16.dp))

        DimensionRow(
            label = "Durchmesser:",
            valueText = details.rotorDiameterMeters?.let { "$it m" } ?: "–",
            accent = accent,
            isVertical = false,
            comparisonName = details.rotorFieldsTextByLength,
            turbineIcon = {
                WindTurbineRotorIcon(modifier = Modifier.size(30.dp, 46.dp))
            },
            showDomIcon = false,
            showSoccerIcon = true,
        )

        HorizontalDivider(
            modifier = Modifier.padding(vertical = 12.dp),
            thickness = 2.dp,
            color = AppColors.LightGray,
        )

        Text(
            text = "Technik",
            fontSize = 17.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.Black,
        )

        Spacer(modifier = Modifier.height(12.dp))

        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            TechItem(
                icon = {
                    WindTurbineHorizontalIcon(modifier = Modifier.size(32.dp))
                },
                label = details.technologyText,
                accent = accent,
            )
            TechItem(
                icon = {
                    WindTurbineLocationIcon(
                        isOnshore = details.isOnshore,
                        modifier = Modifier.size(24.dp, 36.dp),
                    )
                },
                label = details.locationText,
                accent = accent,
            )
        }
    }
}

@Composable
fun CollectionTurbineDetailsLoading(
    isCollected: Boolean,
    modifier: Modifier = Modifier,
) {
    val accent = if (isCollected) AppColors.MapsBlue else AppColors.SubtitleText
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(120.dp)
            .padding(start = 16.dp),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator(
            color = accent,
            modifier = Modifier.size(28.dp),
        )
    }
}

@Composable
private fun DetailInfoRow(
    icon: @Composable () -> Unit,
    title: String,
    subtitle: String,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(modifier = Modifier.size(36.dp), contentAlignment = Alignment.Center) {
            icon()
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                fontSize = 17.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black,
            )
            Text(
                text = subtitle,
                fontSize = 15.sp,
                color = AppColors.SecondaryLabel,
            )
        }
    }
}

@Composable
private fun DimensionRow(
    label: String,
    valueText: String,
    accent: Color,
    isVertical: Boolean,
    comparisonName: String?,
    turbineIcon: @Composable () -> Unit,
    showDomIcon: Boolean,
    showSoccerIcon: Boolean = false,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(modifier = Modifier.width(90.dp)) {
            Text(
                text = label,
                fontSize = 17.sp,
                color = Color.Black,
            )
            Text(
                text = valueText,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                fontStyle = FontStyle.Italic,
                color = accent,
            )
        }

        Box(
            modifier = Modifier
                .weight(1f)
                .height(68.dp),
            contentAlignment = Alignment.Center,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
            ) {
                DimensionArrow(accent = accent, isVertical = isVertical)
                Spacer(modifier = Modifier.width(4.dp))
                turbineIcon()
            }
        }

        Column(
            modifier = Modifier.width(100.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "≈",
                fontSize = 22.sp,
                color = Color.Black,
            )
            if (showDomIcon) {
                KölnerDomIcon(modifier = Modifier.size(28.dp, 36.dp))
            } else if (showSoccerIcon) {
                SoccerFieldIcon(modifier = Modifier.size(36.dp, 24.dp))
            }
            if (!comparisonName.isNullOrBlank()) {
                Text(
                    text = comparisonName,
                    fontSize = 13.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    lineHeight = 16.sp,
                )
            }
        }
    }
}

@Composable
private fun DimensionArrow(
    accent: Color,
    isVertical: Boolean,
) {
    Canvas(
        modifier = Modifier.size(
            if (isVertical) 12.dp else 36.dp,
            if (isVertical) 48.dp else 12.dp
        )
    ) {
        val pad = 4.dp.toPx()
        if (isVertical) {
            val centerX = size.width / 2f
            drawLine(
                color = accent,
                start = Offset(centerX, pad),
                end = Offset(centerX, size.height - pad),
                strokeWidth = 2.dp.toPx(),
                cap = StrokeCap.Round,
            )
            drawLine(
                color = accent,
                start = Offset(centerX, pad),
                end = Offset(centerX - 4.dp.toPx(), pad + 6.dp.toPx()),
                strokeWidth = 2.dp.toPx(),
                cap = StrokeCap.Round,
            )
            drawLine(
                color = accent,
                start = Offset(centerX, pad),
                end = Offset(centerX + 4.dp.toPx(), pad + 6.dp.toPx()),
                strokeWidth = 2.dp.toPx(),
                cap = StrokeCap.Round,
            )
            drawLine(
                color = accent,
                start = Offset(centerX, size.height - pad),
                end = Offset(centerX - 4.dp.toPx(), size.height - pad - 6.dp.toPx()),
                strokeWidth = 2.dp.toPx(),
                cap = StrokeCap.Round,
            )
            drawLine(
                color = accent,
                start = Offset(centerX, size.height - pad),
                end = Offset(centerX + 4.dp.toPx(), size.height - pad - 6.dp.toPx()),
                strokeWidth = 2.dp.toPx(),
                cap = StrokeCap.Round,
            )
        } else {
            val centerY = size.height / 2f
            drawLine(
                color = accent,
                start = Offset(pad, centerY),
                end = Offset(size.width - pad, centerY),
                strokeWidth = 2.dp.toPx(),
                cap = StrokeCap.Round,
            )
            drawLine(
                color = accent,
                start = Offset(pad, centerY),
                end = Offset(pad + 6.dp.toPx(), centerY - 4.dp.toPx()),
                strokeWidth = 2.dp.toPx(),
                cap = StrokeCap.Round,
            )
            drawLine(
                color = accent,
                start = Offset(pad, centerY),
                end = Offset(pad + 6.dp.toPx(), centerY + 4.dp.toPx()),
                strokeWidth = 2.dp.toPx(),
                cap = StrokeCap.Round,
            )
            drawLine(
                color = accent,
                start = Offset(size.width - pad, centerY),
                end = Offset(size.width - pad - 6.dp.toPx(), centerY - 4.dp.toPx()),
                strokeWidth = 2.dp.toPx(),
                cap = StrokeCap.Round,
            )
            drawLine(
                color = accent,
                start = Offset(size.width - pad, centerY),
                end = Offset(size.width - pad - 6.dp.toPx(), centerY + 4.dp.toPx()),
                strokeWidth = 2.dp.toPx(),
                cap = StrokeCap.Round,
            )
        }
    }
}

@Composable
private fun TechItem(
    icon: @Composable () -> Unit,
    label: String,
    accent: Color,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Box(
            modifier = Modifier
                .size(8.dp)
                .background(accent, CircleShape),
        )
        Box(
            modifier = Modifier.size(48.dp, 40.dp),
            contentAlignment = Alignment.Center,
        ) {
            icon()
        }
        Text(
            text = label,
            fontSize = 17.sp,
            color = Color.Black,
        )
    }
}

@Composable
private fun CollectionPowerIcon(
    color: Color,
    modifier: Modifier = Modifier,
) {
    Canvas(modifier = modifier.size(24.dp)) {
        val strokeWidth = 2.dp.toPx()
        drawCircle(color = color, style = Stroke(width = strokeWidth))
        val path = Path().apply {
            moveTo(size.width * 0.5f, size.height * 0.28f)
            lineTo(size.width * 0.38f, size.height * 0.52f)
            lineTo(size.width * 0.5f, size.height * 0.52f)
            lineTo(size.width * 0.46f, size.height * 0.74f)
            lineTo(size.width * 0.64f, size.height * 0.46f)
            lineTo(size.width * 0.52f, size.height * 0.46f)
            close()
        }
        drawPath(path, color = color)
    }
}

@Composable
private fun CollectionLightningIcon(
    color: Color,
    modifier: Modifier = Modifier,
) {
    Canvas(modifier = modifier.size(36.dp)) {
        drawCircle(color = color.copy(alpha = 0.12f))
        drawCircle(color = color, style = Stroke(width = 1.8.dp.toPx()))
        val path = Path().apply {
            moveTo(size.width * 0.55f, size.height * 0.22f)
            lineTo(size.width * 0.35f, size.height * 0.56f)
            lineTo(size.width * 0.50f, size.height * 0.56f)
            lineTo(size.width * 0.45f, size.height * 0.78f)
            lineTo(size.width * 0.65f, size.height * 0.44f)
            lineTo(size.width * 0.50f, size.height * 0.44f)
            close()
        }
        drawPath(path = path, color = color)
    }
}

@Preview
@Composable
private fun CollectionTurbineDetailsCardCollectedPreview() {
    WindradTheme {
        CollectionTurbineDetailsCard(
            details = CollectionTurbineDetails(
                operatingStatus = "Aktiv: seit 6 Jahren",
                commissioningDate = "Inbetriebnahme am 04.02.2020",
                annualEnergyText = "2,3 Millionen kWh (pro Jahr)",
                householdsText = "Versorgt ca. 1.150 Haushalte",
                hubHeightMeters = 166,
                heightComparisonName = "Kölner Dom",
                rotorDiameterMeters = 126,
                rotorFieldsTextByArea = "1,7 Fußballfelder",
                rotorFieldsTextByLength = "0,7 Fußballfelder lang",
                technologyText = "Horizontalläufer",
                locationText = "An Land",
                isOnshore = true,
                isCollected = true,
            ),
            modifier = Modifier.background(Color.White),
        )
    }
}

@Preview
@Composable
private fun CollectionTurbineDetailsCardNotCollectedPreview() {
    WindradTheme {
        CollectionTurbineDetailsCard(
            details = CollectionTurbineDetails(
                operatingStatus = "Aktiv: seit 6 Jahren",
                commissioningDate = "Inbetriebnahme am 04.02.2020",
                annualEnergyText = "2,3 Millionen kWh (pro Jahr)",
                householdsText = "Versorgt ca. 1.150 Haushalte",
                hubHeightMeters = 166,
                heightComparisonName = "Kölner Dom",
                rotorDiameterMeters = 126,
                rotorFieldsTextByArea = "1,7 Fußballfelder",
                rotorFieldsTextByLength = "0,7 Fußballfelder lang",
                technologyText = "Horizontalläufer",
                locationText = "An Land",
                isOnshore = true,
                isCollected = false,
            ),
            modifier = Modifier.background(Color.White),
        )
    }
}
