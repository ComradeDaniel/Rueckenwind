package com.windrad.ui.faq

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.windrad.ui.components.InfoAlertDialog
import com.windrad.ui.components.StaticSearchBar
import com.windrad.ui.icons.LinkIcon
import com.windrad.ui.theme.AppColors
import com.windrad.ui.theme.WindradTheme
import kotlinx.coroutines.launch

private sealed class FaqListItem {
    data class Header(val title: String) : FaqListItem()
    data class Question(val faq: FaqQuestion) : FaqListItem()
}

private fun String.normalizeForSearch(): String =
    lowercase().replace(Regex("[^a-z0-9äöüß\\s]"), "")

private fun filterFaqContent(query: String): List<FaqCategory> {
    val normalizedQuery = query.normalizeForSearch()
    if (normalizedQuery.isEmpty()) return FAQ_CONTENT

    return FAQ_CONTENT.mapNotNull { category ->
        val matchingQuestions = category.questions.filter { question ->
            val normalizedQuestion = question.question.normalizeForSearch()
            val normalizedAnswer = question.answer.normalizeForSearch()
            normalizedQuestion.contains(normalizedQuery) || normalizedAnswer.contains(normalizedQuery)
        }
        if (matchingQuestions.isEmpty()) {
            null
        } else {
            category.copy(questions = matchingQuestions)
        }
    }
}

private fun buildFaqListItems(categories: List<FaqCategory>): List<FaqListItem> =
    buildList {
        categories.forEach { category ->
            add(FaqListItem.Header(category.title))
            category.questions.forEach { question ->
                add(FaqListItem.Question(question))
            }
        }
    }

@Composable
fun FaqScreen(modifier: Modifier = Modifier) {
    var searchQuery by remember { mutableStateOf("") }
    var expandedId by remember { mutableStateOf<String?>(null) }
    var showInfoDialog by remember { mutableStateOf(false) }

    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    val filteredCategories = remember(searchQuery) { filterFaqContent(searchQuery) }
    val listItems = remember(filteredCategories) { buildFaqListItems(filteredCategories) }

    LaunchedEffect(searchQuery, listItems) {
        if (expandedId != null && listItems.none { it is FaqListItem.Question && it.faq.id == expandedId }) {
            expandedId = null
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White),
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            FaqTopBar(onInfoClicked = { showInfoDialog = true })

            StaticSearchBar(
                query = searchQuery,
                onQueryChanged = { searchQuery = it },
                focusRequester = focusRequester,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            )

            if (listItems.isEmpty()) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = "Keine Fragen gefunden",
                        fontSize = 17.sp,
                        color = AppColors.SecondaryLabel,
                    )
                }
            } else {
                LazyColumn(
                    state = listState,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                ) {
                    itemsIndexed(
                        items = listItems,
                        key = { _, item ->
                            when (item) {
                                is FaqListItem.Header -> "h_${item.title}"
                                is FaqListItem.Question -> item.faq.id
                            }
                        },
                    ) { idx, item ->
                        when (item) {
                            is FaqListItem.Header -> FaqSectionHeader(title = item.title)
                            is FaqListItem.Question -> {
                                val isExpanded = expandedId == item.faq.id
                                FaqQuestionRow(
                                    question = item.faq,
                                    isExpanded = isExpanded,
                                    onClick = {
                                        focusManager.clearFocus()
                                        expandedId = if (isExpanded) null else item.faq.id
                                    },
                                    onExpandAnimationFinished = {
                                        coroutineScope.launch {
                                            listState.animateScrollToItem(idx)
                                        }
                                    },
                                )
                            }
                        }
                    }
                    item {
                        Spacer(modifier = Modifier.height(120.dp))
                    }
                }
            }
        }

        if (showInfoDialog) {
            InfoAlertDialog(
                title = "Über diese FAQ",
                message = "Hier findest du Antworten auf häufig gestellte Fragen rund um Windenergie und unsere App. Die Inhalte basieren auf öffentlich zugänglichen Quellen und dienen der allgemeinen Information.",
                confirmText = "Ok",
                dismissText = null,
                onConfirm = { showInfoDialog = false },
                onDismissRequest = { showInfoDialog = false },
            )
        }
    }
}

@Composable
private fun FaqTopBar(
    onInfoClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 50.dp, start = 20.dp, end = 20.dp),
    ) {
        Row(
            modifier = Modifier.align(Alignment.Center),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "Häufige Fragen",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
            )
            Spacer(modifier = Modifier.width(4.dp))
            Box(
                modifier = Modifier
                    .size(26.dp)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = onInfoClicked,
                    ),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "ⓘ",
                    fontSize = 18.sp,
                    color = Color(0xFF757575),
                )
            }
        }
    }
}

@Composable
private fun FaqSectionHeader(
    title: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = title,
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 16.dp, bottom = 8.dp),
        fontSize = 17.sp,
        fontWeight = FontWeight.Bold,
        color = AppColors.PureDark,
    )
}

@Composable
private fun FaqQuestionRow(
    question: FaqQuestion,
    isExpanded: Boolean,
    onClick: () -> Unit,
    onExpandAnimationFinished: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val uriHandler = LocalUriHandler.current
    val chevronRotation by animateFloatAsState(
        targetValue = if (isExpanded) 90f else 0f,
        label = "faqChevron",
    )
    val transitionState = remember { MutableTransitionState(false) }
    transitionState.targetState = isExpanded

    LaunchedEffect(transitionState.currentState, transitionState.isIdle) {
        if (transitionState.currentState && transitionState.isIdle) {
            onExpandAnimationFinished()
        }
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick,
            ),
    ) {
        HorizontalDivider(color = AppColors.LightGray)

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .background(AppColors.MapsBlue, CircleShape),
            )

            Spacer(modifier = Modifier.width(17.dp))

            Text(
                text = question.question,
                modifier = Modifier.weight(1f),
                fontSize = 17.sp,
                fontWeight = FontWeight.Normal,
                color = AppColors.PureDark,
                lineHeight = 22.sp,
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = "›",
                fontSize = 18.sp,
                color = AppColors.SecondaryLabel,
                modifier = Modifier.rotate(chevronRotation),
            )
        }

        AnimatedVisibility(visibleState = transitionState) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min)
                    .padding(start = 4.dp, bottom = 12.dp),
            ) {
                Box(
                    modifier = Modifier
                        .width(4.dp)
                        .fillMaxHeight()
                        .background(AppColors.MapsBlue),
                )
                Column(
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .weight(1f),
                ) {
                    Text(
                        text = question.answer,
                        fontSize = 15.sp,
                        color = AppColors.PureDark,
                        lineHeight = 20.sp,
                    )

                    if (question.urls.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(12.dp))
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            question.urls.forEach { url ->
                                WeiterlesenButton(
                                    onClick = { uriHandler.openUri(url) },
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun WeiterlesenButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Button(
        onClick = onClick,
        modifier = modifier.height(34.dp),
        shape = RoundedCornerShape(1000.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = AppColors.MapsBlue,
        ),
        contentPadding = PaddingValues(
            horizontal = 20.dp,
            vertical = 0.dp,
        ),
    ) {
        Icon(
            imageVector = LinkIcon,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(20.dp),
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = "Weiterlesen",
            fontSize = 15.sp,
            color = Color.White,
        )
    }
}

@Preview
@Composable
private fun FaqScreenPreview() {
    WindradTheme {
        FaqScreen()
    }
}
