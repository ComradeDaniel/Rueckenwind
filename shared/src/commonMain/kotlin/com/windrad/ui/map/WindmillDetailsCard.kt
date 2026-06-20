package com.windrad.ui.map

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.sin
import kotlin.math.cos
import kotlin.math.PI

/**
 * Reusable data model representing the details of a wind turbine.
 * Filled with Figma-specified default dummy values.
 */
data class WindmillDetails(
    val name: String = "44025",
    val operatingStatus: String = "Aktiv: seit 6 Jahren",
    val commissioningDate: String = "Inbetriebnahme am 04.02.2020",
    val householdsFedText: String = "Versorgt ca. 2300 Haushalte (3450kW)",
    val heightComparisonText: String = "166m ~ Kölner Dom",
    val rotorComparisonText: String = "126m ~ 1,7 Fußballfelder",
    val technologyText: String = "Horizontalläufer",
    val locationText: String = "An Land"
)

/**
 * A beautiful, reusable Compose details card for a single Wind Turbine (Windrad).
 * Visual design conforms strictly to the Figma "Wind Park Details" specification.
 */
@Composable
fun WindmillDetailsCard(
    details: WindmillDetails,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // ── 1. Top Row: Status and Commissioning Date ────────────────────────
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            LightningIcon(modifier = Modifier.size(36.dp))
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = details.operatingStatus,
                    color = Color(0xFF1A1C1E),
                    fontSize = 17.sp,
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = (-0.43).sp
                )
                Text(
                    text = details.commissioningDate,
                    color = Color(0xFF565E71),
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Normal,
                    letterSpacing = (-0.23).sp
                )
            }
        }

        HorizontalDivider(color = Color.Black.copy(alpha = 0.08f))

        // ── 2. Middle Row: Households and capacity ───────────────────────────
        Text(
            text = details.householdsFedText,
            color = Color(0xFF1A1C1E),
            fontSize = 17.sp,
            fontWeight = FontWeight.Normal,
            letterSpacing = (-0.43).sp,
            modifier = Modifier.fillMaxWidth()
        )

        HorizontalDivider(color = Color.Black.copy(alpha = 0.08f))

        // ── 3. Dimensions Section (Hub Height and Rotor Diameter) ──────────
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Abmessungen:",
                color = Color(0xFF1A1C1E),
                fontSize = 17.sp,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = (-0.43).sp
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Left Column: Hub Height (Nabenhöhe)
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier.size(56.dp, 68.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            WindTurbineIcon(modifier = Modifier.size(18.dp, 36.dp))
                            KölnerDomIcon(modifier = Modifier.size(24.dp, 32.dp))
                        }
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = details.heightComparisonText,
                        color = Color(0xFF1A1C1E),
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Normal,
                        lineHeight = 18.sp,
                        textAlign = TextAlign.Start
                    )
                }

                // Right Column: Rotor Diameter
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier.size(56.dp, 68.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            WindTurbineRotorIcon(modifier = Modifier.size(22.dp, 36.dp))
                            SoccerFieldIcon(modifier = Modifier.size(26.dp, 20.dp))
                        }
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = details.rotorComparisonText,
                        color = Color(0xFF1A1C1E),
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Normal,
                        lineHeight = 18.sp,
                        textAlign = TextAlign.Start
                    )
                }
            }
        }

        HorizontalDivider(color = Color.Black.copy(alpha = 0.08f))

        // ── 4. Technical Details Section (Technology and Location type) ──────
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Technik:",
                color = Color(0xFF1A1C1E),
                fontSize = 17.sp,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = (-0.43).sp
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Left Column: Technology
                Row(
                    modifier = Modifier.weight(1.3f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier.size(48.dp, 40.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        WindTurbineHorizontalIcon(modifier = Modifier.size(32.dp))
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = details.technologyText,
                        color = Color(0xFF1A1C1E),
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Normal
                    )
                }

                // Right Column: Onshore/Offshore Location
                Row(
                    modifier = Modifier.weight(0.7f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier.size(48.dp, 40.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        WindTurbineOnshoreIcon(modifier = Modifier.size(24.dp, 36.dp))
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = details.locationText,
                        color = Color(0xFF1A1C1E),
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Normal
                    )
                }
            }
        }
    }
}

// ── PROGRAMMATIC VECTOR ICONS ────────────────────────────────────────────────

@Composable
fun LightningIcon(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val radius = size.minDimension / 2f
        val color = Color(0xFF1D7AED)
        
        // Circular border & light blue background fill
        drawCircle(color = color.copy(alpha = 0.12f))
        drawCircle(color = color, style = Stroke(width = 1.8.dp.toPx()))
        
        // Lightning bolt path
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

@Composable
fun WindTurbineIcon(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val color = Color(0xFF565E71)
        val strokeWidth = 2.2.dp.toPx()
        val centerX = size.width / 2f
        val centerY = size.height * 0.35f
        val hubRadius = strokeWidth / 2f
        val gap = hubRadius
        
        drawWindmill(
            centerX = centerX,
            centerY = centerY,
            bladeLength = size.height * 0.30f,
            mastLength = size.height - centerY - hubRadius - gap,
            strokeWidth = strokeWidth,
            color = color,
            gap = gap,
            hubRadius = hubRadius
        )
    }
}

@Composable
fun WindTurbineRotorIcon(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val color = Color(0xFF565E71)
        val strokeWidth = 2.2.dp.toPx()
        val centerX = size.width / 2f
        val centerY = size.height * 0.35f
        val hubRadius = strokeWidth / 2f
        val gap = hubRadius
        val bladeLength = size.height * 0.30f
        
        // Shaded rotor disk area in the background
        drawCircle(
            color = color.copy(alpha = 0.08f),
            radius = hubRadius + gap + bladeLength,
            center = Offset(centerX, centerY)
        )
        
        drawWindmill(
            centerX = centerX,
            centerY = centerY,
            bladeLength = bladeLength,
            mastLength = size.height - centerY - hubRadius - gap,
            strokeWidth = strokeWidth,
            color = color,
            gap = gap,
            hubRadius = hubRadius
        )
    }
}

@Composable
fun KölnerDomIcon(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val color = Color(0xFF565E71)
        val strokeWidth = 1.5.dp.toPx()
        
        val leftTowerX = size.width * 0.3f
        val rightTowerX = size.width * 0.7f
        val spireY = size.height * 0.08f
        val bottomY = size.height * 0.92f
        
        val path = Path().apply {
            // Left Spire Tower
            moveTo(leftTowerX, spireY)
            lineTo(size.width * 0.12f, bottomY)
            lineTo(size.width * 0.48f, bottomY)
            close()
            
            // Right Spire Tower
            moveTo(rightTowerX, spireY)
            lineTo(size.width * 0.52f, bottomY)
            lineTo(size.width * 0.88f, bottomY)
            close()
            
            // Center Gable/Portal
            moveTo(size.width * 0.44f, bottomY)
            lineTo(size.width * 0.5f, size.height * 0.55f)
            lineTo(size.width * 0.56f, bottomY)
        }
        drawPath(path = path, color = color, style = Stroke(width = strokeWidth))
    }
}

@Composable
fun SoccerFieldIcon(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val color = Color(0xFF565E71)
        val strokeWidth = 1.5.dp.toPx()
        
        // Pitch border
        drawRect(color = color, style = Stroke(width = strokeWidth))
        
        // Midfield Line
        drawLine(
            color = color,
            start = Offset(size.width / 2f, 0f),
            end = Offset(size.width / 2f, size.height),
            strokeWidth = strokeWidth
        )
        
        // Midfield Circle
        drawCircle(
            color = color,
            radius = size.height * 0.25f,
            center = Offset(size.width / 2f, size.height / 2f),
            style = Stroke(width = strokeWidth)
        )
        
        // Left Goal Box
        drawRect(
            color = color,
            topLeft = Offset(0f, size.height * 0.28f),
            size = Size(size.width * 0.18f, size.height * 0.44f),
            style = Stroke(width = strokeWidth)
        )
        
        // Right Goal Box
        drawRect(
            color = color,
            topLeft = Offset(size.width * 0.82f, size.height * 0.28f),
            size = Size(size.width * 0.18f, size.height * 0.44f),
            style = Stroke(width = strokeWidth)
        )
    }
}

@Composable
fun WindTurbineHorizontalIcon(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val color = Color(0xFF565E71)
        val blue = Color(0xFF1D7AED)
        val strokeWidth = 1.5.dp.toPx()
        val centerX = size.width / 2f
        val centerY = size.height / 2f
        
        // Blue circle representing rotation direction
        drawCircle(
            color = blue,
            radius = size.minDimension / 2f - 3.dp.toPx(),
            style = Stroke(width = strokeWidth)
        )
        
        // Rotor Hub
        drawCircle(color = color, radius = 2.dp.toPx(), center = Offset(centerX, centerY))
        
        // Three Rotor Blades
        for (angle in listOf(0f, 120f, 240f)) {
            val rad = angle * (PI.toFloat() / 180f)
            val length = (size.minDimension / 2f) * 0.72f
            val endX = centerX + length * sin(rad)
            val endY = centerY - length * cos(rad)
            drawLine(
                color = color,
                start = Offset(centerX, centerY),
                end = Offset(endX, endY),
                strokeWidth = strokeWidth,
                cap = StrokeCap.Round
            )
        }
    }
}

@Composable
fun WindTurbineOnshoreIcon(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val color = Color(0xFF565E71)
        val strokeWidth = 2.2.dp.toPx()
        val centerX = size.width / 2f
        val centerY = size.height * 0.35f
        val hubRadius = strokeWidth / 2f
        val gap = hubRadius
        
        // Ground mound curve at the bottom
        val groundY = size.height - 4.dp.toPx()
        val path = Path().apply {
            moveTo(0f, size.height)
            quadraticTo(centerX, groundY, size.width, size.height)
        }
        drawPath(path = path, color = color, style = Stroke(width = strokeWidth * 1.5f))
        
        drawWindmill(
            centerX = centerX,
            centerY = centerY,
            bladeLength = size.height * 0.3f,
            mastLength = groundY - centerY - hubRadius - gap,
            strokeWidth = strokeWidth,
            color = color,
            gap = gap,
            hubRadius = hubRadius
        )
    }
}
