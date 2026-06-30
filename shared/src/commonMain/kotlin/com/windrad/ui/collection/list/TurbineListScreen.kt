package com.windrad.ui.collection.list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.windrad.ui.map.WindTurbineIcon
import com.windrad.ui.theme.AppColors
import com.windrad.ui.theme.WindradTheme
import kotlinx.coroutines.launch

private const val BUNDESWEIT = "Bundesweit"
private val INDEX_LETTERS = ('A'..'Z').toList() + '#'

@Composable
fun TurbineListScreen(
    uiState: CollectionListUiState,
    onBackClicked: () -> Unit,
    onFilterChanged: (CollectionFilter) -> Unit,
    onTurbineClicked: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val title = uiState.selectedState ?: BUNDESWEIT
    val isFound = uiState.filter == CollectionFilter.FOUND

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White),
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            CollectionTopBar(
                title = title,
                onBackClicked = onBackClicked,
            )

            StaticSearchBar(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            )

            Box(modifier = Modifier.weight(1f)) {
                if (uiState.isLoading) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center,
                    ) {
                        CircularProgressIndicator(color = AppColors.MapsBlue)
                    }
                } else if (uiState.turbines.isEmpty()) {
                    EmptyTurbineList(
                        isFound = isFound,
                        modifier = Modifier.fillMaxSize(),
                    )
                } else {
                    LazyColumn(
                        state = listState,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(start = 16.dp, end = 28.dp),
                    ) {
                        items(
                            items = uiState.turbines,
                            key = { it.id },
                        ) { turbine ->
                            TurbineRow(
                                turbine = turbine,
                                isFound = isFound,
                                isExpanded = uiState.expandedTurbineId == turbine.id,
                                onClick = { onTurbineClicked(turbine.id) },
                            )
                        }
                        item {
                            Spacer(modifier = Modifier.height(120.dp))
                        }
                    }

                    AlphabetIndexBar(
                        letterIndex = uiState.letterIndex,
                        onLetterSelected = { letter ->
                            val targetIndex = resolveScrollIndex(letter, uiState.letterIndex)
                            if (targetIndex != null) {
                                coroutineScope.launch {
                                    listState.scrollToItem(targetIndex)
                                }
                            }
                        },
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .padding(end = 4.dp)
                            .fillMaxHeight(0.85f),
                    )
                }
            }
        }

        FilterSegmentedControl(
            selectedFilter = uiState.filter,
            onFilterChanged = onFilterChanged,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 24.dp),
        )

        SearchFab(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 20.dp, bottom = 24.dp),
        )
    }
}

@Composable
private fun StaticSearchBar(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(44.dp),
        shape = RoundedCornerShape(22.dp),
        color = AppColors.LightGray.copy(alpha = 0.6f),
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                tint = AppColors.DarkIcon,
                modifier = Modifier.size(20.dp),
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = "Suchen",
                color = AppColors.DarkIcon.copy(alpha = 0.5f),
                fontSize = 15.sp,
                modifier = Modifier.weight(1f),
            )
            Text(
                text = "🎤",
                fontSize = 16.sp,
            )
        }
    }
}

@Composable
private fun TurbineRow(
    turbine: TurbineListItem,
    isFound: Boolean,
    isExpanded: Boolean,
    onClick: () -> Unit,
) {
    val chevronRotation by animateFloatAsState(
        targetValue = if (isExpanded) 90f else 0f,
        label = "chevron",
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick,
            ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (isFound) {
                WindTurbineIcon(
                    modifier = Modifier.size(24.dp, 35.dp),
                    color = AppColors.MapsBlue,
                )
            } else {
                Box(
                    modifier = Modifier
                        .size(33.dp)
                        .background(AppColors.MapsBlue, CircleShape),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = "?",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = turbine.municipality,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Normal,
                    color = AppColors.PureDark,
                )
                Text(
                    text = turbine.displayName,
                    fontSize = 15.sp,
                    color = AppColors.SecondaryLabel,
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                Text(
                    text = "Detail",
                    fontSize = 15.sp,
                    color = AppColors.SecondaryLabel,
                )
                Text(
                    text = "›",
                    fontSize = 18.sp,
                    color = AppColors.SecondaryLabel,
                    modifier = Modifier.rotate(chevronRotation),
                )
            }
        }

        AnimatedVisibility(visible = isExpanded) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 45.dp, bottom = 12.dp)
                    .height(80.dp)
                    .background(AppColors.IndicatorBg, RoundedCornerShape(12.dp)),
            )
        }

        HorizontalDivider(color = AppColors.LightGray)
    }
}

@Composable
private fun AlphabetIndexBar(
    letterIndex: Map<Char, Int>,
    onLetterSelected: (Char) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .width(16.dp)
            .pointerInput(letterIndex) {
                detectTapGestures { offset ->
                    val letterHeight = size.height.toFloat() / INDEX_LETTERS.size
                    val index =
                        (offset.y / letterHeight).toInt().coerceIn(0, INDEX_LETTERS.lastIndex)
                    onLetterSelected(INDEX_LETTERS[index])
                }
            },
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        INDEX_LETTERS.forEach { letter ->
            val isActive = letter in letterIndex
            Text(
                text = letter.toString(),
                fontSize = 9.sp,
                fontWeight = FontWeight.Medium,
                color = if (isActive) AppColors.MapsBlue else AppColors.LightGray,
                modifier = Modifier.clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    enabled = isActive,
                ) {
                    onLetterSelected(letter)
                },
            )
        }
    }
}

@Composable
private fun FilterSegmentedControl(
    selectedFilter: CollectionFilter,
    onFilterChanged: (CollectionFilter) -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier
            .shadow(elevation = 4.dp, shape = RoundedCornerShape(100.dp), clip = false),
        shape = RoundedCornerShape(100.dp),
        color = Color.White,
        border = BorderStroke(1.dp, AppColors.LightGray),
    ) {
        Row(
            modifier = Modifier.padding(4.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            FilterSegment(
                label = "Gefunden",
                icon = "✓",
                isSelected = selectedFilter == CollectionFilter.FOUND,
                onClick = { onFilterChanged(CollectionFilter.FOUND) },
            )
            FilterSegment(
                label = "Nicht Gefunden",
                icon = "?",
                isSelected = selectedFilter == CollectionFilter.NOT_FOUND,
                onClick = { onFilterChanged(CollectionFilter.NOT_FOUND) },
            )
        }
    }
}

@Composable
private fun FilterSegment(
    label: String,
    icon: String,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    val bgColor = if (isSelected) AppColors.IndicatorBg else Color.Transparent
    val contentColor = if (isSelected) AppColors.MapsBlue else AppColors.DarkIcon

    Column(
        modifier = Modifier
            .background(bgColor, RoundedCornerShape(100.dp))
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick,
            )
            .padding(horizontal = 20.dp, vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = icon,
            fontSize = 14.sp,
            color = contentColor,
            fontWeight = FontWeight.Bold,
        )
        Text(
            text = label,
            fontSize = 11.sp,
            color = contentColor,
            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
        )
    }
}

@Composable
private fun SearchFab(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .size(48.dp)
            .shadow(elevation = 4.dp, shape = CircleShape, clip = false)
            .background(Color.White, CircleShape),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = "Suchen",
            tint = AppColors.DarkIcon,
            modifier = Modifier.size(22.dp),
        )
    }
}

@Composable
private fun EmptyTurbineList(
    isFound: Boolean,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        if (isFound) {
            WindTurbineIcon(
                modifier = Modifier.size(48.dp, 70.dp),
                color = AppColors.MapsBlue,
            )
        } else {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .background(AppColors.MapsBlue, CircleShape),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "?",
                    color = Color.White,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Noch keine Windkraftwerke gefunden",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Scanne zuerst deine Umgebung nach Windkraftanlagen",
            fontSize = 15.sp,
            color = AppColors.SecondaryLabel,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
        )
    }
}

private fun resolveScrollIndex(letter: Char, letterIndex: Map<Char, Int>): Int? {
    if (letter in letterIndex) return letterIndex[letter]
    val letters = INDEX_LETTERS
    val targetPos = letters.indexOf(letter)
    if (targetPos < 0) return null
    for (i in targetPos downTo 0) {
        val l = letters[i]
        if (l in letterIndex) return letterIndex[l]
    }
    for (i in targetPos + 1..letters.lastIndex) {
        val l = letters[i]
        if (l in letterIndex) return letterIndex[l]
    }
    return null
}

@Preview
@Composable
private fun TurbineListScreenPreview() {
    WindradTheme {
        TurbineListScreen(
            uiState = CollectionListUiState(
                route = CollectionRoute.TURBINE_LIST,
                selectedState = "Sachsen",
                filter = CollectionFilter.FOUND,
                turbines = listOf(
                    TurbineListItem("1", "Großschirma", "Windkraftwerk"),
                    TurbineListItem("2", "Gersdorf", "Anzeigename"),
                    TurbineListItem("3", "Leisnig", "WEA S3"),
                ),
                letterIndex = mapOf('G' to 0, 'L' to 2),
            ),
            onBackClicked = {},
            onFilterChanged = {},
            onTurbineClicked = {},
        )
    }
}
