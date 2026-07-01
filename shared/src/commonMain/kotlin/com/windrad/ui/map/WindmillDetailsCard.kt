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

import com.windrad.domain.WeatherInfo

/**
 * Reusable data model representing the details of a wind turbine.
 * Filled with Figma-specified default dummy values.
 */
data class WindmillDetails(
    val name: String = "44025",
    val windparkName: String? = "Seegebiet Mansfelder Land",
    val operatingStatus: String = "Aktiv: seit 6 Jahren",
    val commissioningDate: String = "Inbetriebnahme am 04.02.2020",
    val householdsFedText: String = "Versorgt ca. 2300 Haushalte (3450kW)",
    val heightComparisonText: String = "166m ~\nKölner Dom",
    val rotorComparisonText: String = "126m ~ 1,7\nFußballfelder",
    val technologyText: String = "Horizontalläufer",
    val locationText: String = "An Land",
    val isOnshore: Boolean = true,
    val totalHeight: Double = 166.0,
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val rotorDiameter: Double? = 126.0,
    val weatherInfo: WeatherInfo? = null
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
            .padding(horizontal = 16.dp)
    ) {
        HorizontalDivider(color = Color.Black.copy(alpha = 0.08f))

        // ── 1. Top Row: Status and Commissioning Date ────────────────────────
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(Res.drawable.power_icon),
                contentDescription = "Status",
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
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
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(Res.drawable.lightning_symbol),
                contentDescription = "Versorgte Haushalte",
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = details.householdsFedText,
                color = AppColors.DarkText,
                fontSize = 17.sp,
                fontWeight = FontWeight.Normal,
                letterSpacing = (-0.43).sp,
                modifier = Modifier.fillMaxWidth()
            )
        }

        HorizontalDivider(color = Color.Black.copy(alpha = 0.08f))

        // ── 3. Dimensions Section (Hub Height and Rotor Diameter) ──────────
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, bottom = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Abmessungen:",
                    color = AppColors.DarkText,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Normal,
                    letterSpacing = (-0.43).sp
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Left Column: Hub Height (Nabenhöhe)
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val sight = SightFinder.findNearestSight(details.totalHeight)
                    val heightText = if (details.totalHeight <= 0.0) {
                        "Wert leider nicht bekannt"
                    } else {
                        "${details.totalHeight.toInt()}m ~\n${sight.name}"
                    }
                    val isVertical = details.technologyText == "Vertikalläufer"
                    val turbineIconRes = if (isVertical) {
                        Res.drawable.vertikallaeufer_icon
                    } else {
                        Res.drawable.wind_turbine_icon
                    }
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(turbineIconRes),
                            contentDescription = null,
                            modifier = Modifier.size(46.dp)
                        )
                        Image(
                            painter = painterResource(sight.icon),
                            contentDescription = null,
                            modifier = Modifier.size(46.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = heightText,
                        color = Color.Black,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Normal,
                        lineHeight = 18.sp,
                        textAlign = TextAlign.Center
                    )
                }

                // Right Column: Rotor Diameter
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
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
                            modifier = Modifier.size(46.dp)
                        )
                        Image(
                            painter = painterResource(Res.drawable.fussballfeld_icon),
                            contentDescription = null,
                            modifier = Modifier.size(46.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = details.rotorComparisonText,
                        color = Color.Black,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Normal,
                        lineHeight = 18.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        HorizontalDivider(color = Color.Black.copy(alpha = 0.08f))

        // ── 4. Technology Section ────────────────────────────────────────────
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, bottom = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Technik:",
                    color = AppColors.DarkText,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Normal,
                    letterSpacing = (-0.43).sp
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Left Column: Technology
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
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
                        modifier = Modifier.size(46.dp)
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = details.technologyText,
                        color = Color.Black,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.Center
                    )
                }

                // Right Column: Onshore/Offshore Location
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
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
                        modifier = Modifier.size(46.dp)
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = details.locationText,
                        color = Color.Black,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

// ── END OF FILE ─────────────────────────────────────────────────────────────
