package com.windrad.ui.collection.list

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.windrad.ui.collection.kpi.formatGermanNumber
import com.windrad.ui.theme.AppColors
import com.windrad.ui.theme.WindradTheme

private const val BUNDESWEIT = "Bundesweit"

@Composable
fun StateOverviewScreen(
    uiState: CollectionListUiState,
    onBackClicked: () -> Unit,
    onStateSelected: (String?) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White),
    ) {
        CollectionTopBar(
            title = "Deine Sammlung",
            onBackClicked = onBackClicked,
        )

        if (uiState.isLoading && uiState.overview == null) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                CircularProgressIndicator(color = AppColors.MapsBlue)
            }
        } else {
            val overview = uiState.overview
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(bottom = 120.dp),
            ) {
                if (overview != null && overview.totalCollected > 0) {
                    KpiHeader(
                        topStateName = overview.topStateName,
                        totalCollected = overview.totalCollected,
                        modifier = Modifier.padding(horizontal = 22.dp),
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }

                if (overview != null) {
                    StateListSection(
                        overview = overview,
                        onStateSelected = onStateSelected,
                        modifier = Modifier.padding(horizontal = 16.dp),
                    )
                }
            }
        }
    }
}

@Composable
private fun KpiHeader(
    topStateName: String?,
    totalCollected: Int,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "📈",
                fontSize = 40.sp,
            )
            Spacer(modifier = Modifier.height(4.dp))
            val annotated = buildAnnotatedString {
                append("Meiste entdeckt\nin ")
                withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                    append(topStateName ?: "–")
                }
            }
            Text(
                text = annotated,
                fontSize = 15.sp,
                color = Color.Black,
                lineHeight = 20.sp,
            )
        }

        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = formatGermanNumber(totalCollected.toDouble()),
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                color = AppColors.MapsBlue,
                lineHeight = 44.sp,
            )
            Text(
                text = "Windkraftwerke\ninsgesamt entdeckt",
                fontSize = 15.sp,
                color = AppColors.SecondaryLabel,
                lineHeight = 20.sp,
            )
        }
    }
}

@Composable
private fun StateListSection(
    overview: CollectionOverviewData,
    onStateSelected: (String?) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Text(
            text = "Nach Bundesländern",
            fontSize = 13.sp,
            color = AppColors.SecondaryLabel,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(start = 14.dp, bottom = 8.dp),
        )
        HorizontalDivider(color = AppColors.LightGray)

        StateRow(
            name = BUNDESWEIT,
            collectedCount = overview.totalCollected,
            totalCount = overview.totalCount,
            onClick = { onStateSelected(null) },
        )

        overview.states.forEach { state ->
            StateRow(
                name = state.name,
                collectedCount = state.collectedCount,
                totalCount = state.totalCount,
                onClick = { onStateSelected(state.name) },
            )
        }
    }
}

@Composable
private fun StateRow(
    name: String,
    collectedCount: Int,
    totalCount: Int,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick,
            )
            .padding(horizontal = 14.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = name,
            fontSize = 17.sp,
            color = Color.Black,
            modifier = Modifier.weight(1f),
        )
        CountRing(
            collected = collectedCount,
            total = totalCount,
            modifier = Modifier.size(33.dp),
        )
    }
    HorizontalDivider(color = AppColors.LightGray)
}

@Composable
fun CountRing(
    collected: Int,
    total: Int,
    modifier: Modifier = Modifier,
) {
    val progress = if (total > 0) {
        (collected.toFloat() / total.toFloat()).coerceIn(0f, 1f)
    } else {
        0f
    }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val strokeWidth = 3.dp.toPx()
            val radius = (size.minDimension - strokeWidth) / 2f

            drawCircle(
                color = AppColors.LightGray,
                radius = radius,
                style = Stroke(width = strokeWidth),
            )

            if (progress > 0f) {
                drawArc(
                    color = AppColors.MapsBlue,
                    startAngle = -90f,
                    sweepAngle = 360f * progress,
                    useCenter = false,
                    style = Stroke(width = strokeWidth, cap = StrokeCap.Round),
                )
            }
        }
        Text(
            text = collected.toString(),
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            color = AppColors.MapsBlue,
        )
    }
}

@Preview
@Composable
private fun StateOverviewScreenPreview() {
    WindradTheme {
        StateOverviewScreen(
            uiState = CollectionListUiState(
                overview = CollectionOverviewData(
                    totalCollected = 256,
                    totalCount = 40000,
                    topStateName = "Baden-Württemberg",
                    states = listOf(
                        FederalStateSummary("Baden-Württemberg", 62, 5000),
                        FederalStateSummary("Bayern", 48, 6000),
                        FederalStateSummary("Berlin", 0, 50),
                    ),
                ),
            ),
            onBackClicked = {},
            onStateSelected = {},
        )
    }
}
