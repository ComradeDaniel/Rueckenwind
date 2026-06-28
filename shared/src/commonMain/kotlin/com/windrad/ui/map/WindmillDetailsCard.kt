package com.windrad.ui.map

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import com.windrad.util.SightFinder
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import org.jetbrains.compose.resources.painterResource
import windrad.shared.generated.resources.*
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
import com.windrad.ui.theme.AppColors

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
    val locationText: String = "An Land",
    val isOnshore: Boolean = true,
    val totalHeight: Double = 166.0
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
                    color = AppColors.DarkText,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = (-0.43).sp
                )
                Text(
                    text = details.commissioningDate,
                    color = AppColors.SubtitleText,
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
            color = AppColors.DarkText,
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
                color = AppColors.DarkText,
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
                    val sight = SightFinder.findNearestSight(details.totalHeight)
                    val heightText = "${details.totalHeight.toInt()}m ~ ${sight.name}"
                    val isVertical = details.technologyText == "Vertikalläufer"
                    val turbineIconRes = if (isVertical) {
                        Res.drawable.vertikallaeufer_icon
                    } else {
                        Res.drawable.wind_turbine_icon
                    }
                    Box(
                        modifier = Modifier.size(56.dp, 68.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(turbineIconRes),
                                contentDescription = null,
                                modifier = Modifier.size(18.dp, 36.dp)
                            )
                            Image(
                                painter = painterResource(sight.icon),
                                contentDescription = null,
                                modifier = Modifier.size(24.dp, 32.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = heightText,
                        color = AppColors.DarkText,
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
                            val isVertical = details.technologyText == "Vertikalläufer"
                            val rotorIconRes = if (isVertical) {
                                Res.drawable.vertikallaeufer_icon_rotordurchmesser
                            } else {
                                Res.drawable.wind_turbine_rotor_diameter
                            }
                            Image(
                                painter = painterResource(rotorIconRes),
                                contentDescription = null,
                                modifier = Modifier.size(22.dp, 36.dp)
                            )
                            Image(
                                painter = painterResource(Res.drawable.fussballfeld_icon),
                                contentDescription = null,
                                modifier = Modifier.size(26.dp, 20.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = details.rotorComparisonText,
                        color = AppColors.DarkText,
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
                color = AppColors.DarkText,
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
                        val isVertical = details.technologyText == "Vertikalläufer"
                        val techIconRes = if (isVertical) {
                            Res.drawable.vertikallaeufer_icon_vertikal
                        } else {
                            Res.drawable.wind_turbine_horizontal
                        }
                        Image(
                            painter = painterResource(techIconRes),
                            contentDescription = null,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = details.technologyText,
                        color = AppColors.DarkText,
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
                        val isVertical = details.technologyText == "Vertikalläufer"
                        val iconRes = when {
                            details.isOnshore && isVertical -> Res.drawable.vertikallaeufer_onshore
                            details.isOnshore && !isVertical -> Res.drawable.wind_turbine_onshore_icon
                            !details.isOnshore && isVertical -> Res.drawable.vertikallaeufer_offshore
                            else -> Res.drawable.wind_turbine_offshore_icon
                        }
                        Image(
                            painter = painterResource(iconRes),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp, 36.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = details.locationText,
                        color = AppColors.DarkText,
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
        val color = AppColors.Blue
        
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




