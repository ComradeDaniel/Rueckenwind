package com.windrad.ui.collection

import androidx.compose.foundation.Image
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.windrad.ui.collection.kpi.Co2BarChart
import com.windrad.ui.collection.kpi.MwhBarChart
import com.windrad.ui.collection.kpi.NachhaltigkeitSheet
import com.windrad.ui.collection.kpi.StromerzeugungSheet
import com.windrad.ui.collection.kpi.formatGermanNumber
import com.windrad.ui.collection.kpi.formatMetricValue
import com.windrad.ui.components.InfoAlertDialog
import com.windrad.ui.icons.CollectCardsBase
import com.windrad.ui.theme.AppColors
import com.windrad.ui.theme.WindradTheme

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
    var showEnergySheet by remember { mutableStateOf(false) }
    var showSustainabilitySheet by remember { mutableStateOf(false) }
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
                    text = "Windkraftwerke\nentdeckt",
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
                            onClick = {
                                onMwhCardClicked()
                                showEnergySheet = true
                            },
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
                            onClick = {
                                onCo2CardClicked()
                                showSustainabilitySheet = true
                            },
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
                                imageVector = CollectCardsBase,
                                contentDescription = "SAMMLUNG",
                                tint = Color.White,
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

    if (showEnergySheet) {
        StromerzeugungSheet(
            uiState = uiState,
            onDismiss = { showEnergySheet = false },
            onInfoClicked = { showInfoDialog = true },
        )
    }

    if (showSustainabilitySheet) {
        NachhaltigkeitSheet(
            uiState = uiState,
            onDismiss = { showSustainabilitySheet = false },
            onInfoClicked = { showInfoDialog = true },
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

// ── Description text ──────────────────────────────────────────────────────────

@Composable
private fun DescriptionText(isEmpty: Boolean, householdsCount: Int) {
    if (isEmpty) {
        Text(
            text = "Oh! Hier ist noch nicht viel los. Sammle jetzt Windkraftwerke, um dein Netzwerk mit grünem Strom zu versorgen.",
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
                householdsCount = 4_000,
                mwhValue = 1240.0,
                co2Value = 845.0,
                coolingWaterValue = 2457.0,
                ecoAreaValue = 0.244,
                eCarChargesValue = 16_848.0,
                fullLoadHoursValue = 80_776.0,
            ),
        )
    }
}
