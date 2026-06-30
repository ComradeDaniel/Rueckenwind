package com.windrad.ui.map

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.StartOffset
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.windrad.ui.navigation.WindradBottomBar
import com.windrad.ui.navigation.WindradDestination
import com.windrad.ui.theme.AppColors
import io.github.alexzhirkevich.compottie.LottieCompositionSpec
import io.github.alexzhirkevich.compottie.animateLottieCompositionAsState
import io.github.alexzhirkevich.compottie.rememberLottieComposition
import io.github.alexzhirkevich.compottie.rememberLottiePainter
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import windrad.shared.generated.resources.Res
import org.jetbrains.compose.resources.painterResource
import windrad.shared.generated.resources.wind_turbine_icon


@Composable
fun MapScreen(
    uiState: MapUiState,
    onScanClicked: () -> Unit,
    onWindmillSelected: (String?) -> Unit,
    onSearchQueryChanged: (String) -> Unit,
    onSearchResultSelected: (SearchSuggestion) -> Unit,
    onClearSearch: () -> Unit,
    onToggleFavorite: (String) -> Unit,
    onCollectAllClicked: () -> Unit,
    currentDestination: WindradDestination,
    onDestinationSelected: (WindradDestination) -> Unit,
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()

    BoxWithConstraints(modifier = modifier.fillMaxSize()) {
        val screenHeight = maxHeight
        val screenWidth = maxWidth
        val density = LocalDensity.current

        // Define bottom sheet heights and offsets
        val collapsedHeight = 166.dp
        val maxExpandedHeight = screenHeight - 100.dp

        val collapsedOffsetPx = with(density) { (screenHeight - collapsedHeight).toPx() }
        val maxExpandedOffsetPx = with(density) { 100.dp.toPx() }

        var detailsCardHeightDp by remember { mutableStateOf(300.dp) }
        val contentHeight = (detailsCardHeightDp + 193.dp).coerceAtMost(screenHeight - 100.dp)
        val contentOffsetPx = with(density) { (screenHeight - contentHeight).toPx() }

        // Draggable sheet offset state
        var sheetState by remember { mutableStateOf(SheetState.COLLAPSED) }
        var isDragging by remember { mutableStateOf(false) }
        val sheetOffsetY = remember { Animatable(collapsedOffsetPx) }

        // Calculate progress specifically from contentOffsetPx (HALF_EXPANDED) to maxExpandedOffsetPx (FULLY_EXPANDED)
        val fullyExpandedProgress = remember(
            contentOffsetPx,
            maxExpandedOffsetPx,
            sheetOffsetY.value,
            sheetState,
            isDragging
        ) {
            if (sheetState == SheetState.FULLY_EXPANDED || (isDragging && sheetOffsetY.value < contentOffsetPx)) {
                val range = contentOffsetPx - maxExpandedOffsetPx
                if (range > 0f) {
                    ((contentOffsetPx - sheetOffsetY.value) / range).coerceIn(0f, 1f)
                } else {
                    0f
                }
            } else {
                0f
            }
        }

        // Load Lottie File
        var lottieJsonString by remember { mutableStateOf<String?>(null) }
        val animationPath = remember(uiState.selectedWindmill) {
            val details = uiState.selectedWindmill
            if (details == null) {
                "files/new_hori_anim.json"
            } else {
                val isVertical = details.technologyText == "Vertikalläufer"
                if (isVertical) {
                    "files/animation_vertikallaufer.json"
                } else if (details.isOnshore) {
                    "files/new_hori_anim.json"
                } else {
                    "files/animation_windrad_wasser.json"
                }
            }
        }
        LaunchedEffect(animationPath) {
            try {
                lottieJsonString = Res.readBytes(animationPath).decodeToString()
            } catch (e: Exception) {
                println("Failed to load Lottie resource $animationPath: $e")
            }
        }

        val compositionSpec = if (lottieJsonString != null) {
            LottieCompositionSpec.JsonString(lottieJsonString!!)
        } else {
            LottieCompositionSpec.JsonString("")
        }

        val composition by rememberLottieComposition(compositionSpec)

        val lottieProgress by animateLottieCompositionAsState(
            composition = composition,
            iterations = Int.MAX_VALUE
        )

        // Calculate expansion progress (0f = collapsed, 1f = fully expanded)
        val progress = remember(collapsedOffsetPx, contentOffsetPx, maxExpandedOffsetPx, sheetOffsetY.value) {
            val targetOffset = if (uiState.selectedWindmill != null) contentOffsetPx else maxExpandedOffsetPx
            val range = collapsedOffsetPx - targetOffset
            if (range > 0f) {
                ((collapsedOffsetPx - sheetOffsetY.value) / range).coerceIn(0f, 1f)
            } else {
                0f
            }
        }
        
        // Sync offset when state or constraints change
        LaunchedEffect(sheetState, collapsedOffsetPx, contentOffsetPx, maxExpandedOffsetPx) {
            if (collapsedOffsetPx <= 0f) return@LaunchedEffect
            val target = when (sheetState) {
                SheetState.COLLAPSED -> collapsedOffsetPx
                SheetState.HALF_EXPANDED -> contentOffsetPx
                SheetState.FULLY_EXPANDED -> maxExpandedOffsetPx
            }
            // If offset is at 0 (was hidden), snap instantly to avoid unwanted slide-down animation.
            if (sheetOffsetY.value <= 0f) {
                sheetOffsetY.snapTo(target)
            } else {
                sheetOffsetY.animateTo(target, animationSpec = tween(300))
            }
        }


        // 1. Map Background
        WindradMap(
            centerLatitude = uiState.centerLatitude,
            centerLongitude = uiState.centerLongitude,
            zoom = uiState.zoom,
            windmillGeoJson = uiState.windmillGeoJson,
            onZoomChanged = { /* Zoom-Änderungen für zukünftige Features nutzbar */ },
            onWindmillClicked = { id ->
                onWindmillSelected(id)
                sheetState = SheetState.HALF_EXPANDED
            },
            modifier = Modifier.fillMaxSize()
        )

        // 2. Floating Search Bar & Suggestion List
        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .statusBarsPadding()
                .padding(top = 16.dp, start = 12.dp, end = 12.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(44.dp),
                shape = RoundedCornerShape(22.dp),
                color = Color.White.copy(alpha = 0.9f),
                shadowElevation = 6.dp,
                border = BorderStroke(1.dp, Color.White.copy(alpha = 0.5f))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Suchen",
                        tint = AppColors.DarkIcon,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))

                    val focusManager = androidx.compose.ui.platform.LocalFocusManager.current

                    BasicTextField(
                        value = uiState.searchQuery,
                        onValueChange = onSearchQueryChanged,
                        modifier = Modifier.weight(1f),
                        singleLine = true,
                        textStyle = androidx.compose.ui.text.TextStyle(
                            color = AppColors.DarkIcon,
                            fontSize = 15.sp
                        ),
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Search
                        ),
                        keyboardActions = KeyboardActions(
                            onSearch = {
                                if (uiState.searchSuggestions.isNotEmpty()) {
                                    onSearchResultSelected(uiState.searchSuggestions.first())
                                }
                                focusManager.clearFocus()
                            }
                        ),
                        decorationBox = { innerTextField ->
                            Box(
                                modifier = Modifier.fillMaxWidth(),
                                contentAlignment = Alignment.CenterStart
                            ) {
                                if (uiState.searchQuery.isEmpty()) {
                                    Text(
                                        text = "Suchen",
                                        color = AppColors.DarkIcon.copy(alpha = 0.5f),
                                        fontSize = 15.sp
                                    )
                                }
                                innerTextField()
                            }
                        }
                    )

                    if (uiState.searchQuery.isNotEmpty()) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Löschen",
                            tint = AppColors.DarkIcon,
                            modifier = Modifier
                                .size(20.dp)
                                .clickable {
                                    onClearSearch()
                                }
                        )
                    } else {
                        Icon(
                            imageVector = MicIcon,
                            contentDescription = "Sprachsuche",
                            tint = AppColors.DarkIcon,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }

            if (uiState.searchSuggestions.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .heightIn(max = 250.dp),
                    shape = RoundedCornerShape(16.dp),
                    color = Color.White.copy(alpha = 0.95f),
                    shadowElevation = 8.dp,
                    border = BorderStroke(1.dp, Color.White.copy(alpha = 0.5f))
                ) {
                    val focusManager = androidx.compose.ui.platform.LocalFocusManager.current
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    ) {
                        items(uiState.searchSuggestions) { suggestion ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        onSearchResultSelected(suggestion)
                                        focusManager.clearFocus()
                                    }
                                    .padding(horizontal = 16.dp, vertical = 12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                val (icon, label) = when (suggestion.type) {
                                    "city" -> "📍" to "Ort"
                                    "wind_plant_name" -> "💨" to "Windpark"
                                    "postal_code" -> "✉️" to "PLZ"
                                    else -> "📍" to "Ort"
                                }
                                Text(
                                    text = icon,
                                    fontSize = 16.sp,
                                    modifier = Modifier.padding(end = 12.dp)
                                )
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = suggestion.name,
                                        color = AppColors.DarkText,
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                    Text(
                                        text = suggestion.subtitle.ifEmpty { label },
                                        color = Color.Gray,
                                        fontSize = 12.sp,
                                        maxLines = 1,
                                        overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // 3. Draggable Bottom Sheet Card
        // Calculate dynamic height to ensure the card stops near the bottom edge
        val bottomMarginPx = with(density) {
            4.dp.toPx()
        }
        val sheetHeight = with(density) {
            (screenHeight.toPx() - sheetOffsetY.value - bottomMarginPx).toDp().coerceAtLeast(0.dp)
        }

        // On Android the sheet looks "missing" when collapsed at the bottom because
        // `progress` becomes ~0, which would make the glass background alpha ~0 as well.
        // Keep a small minimum opacity so the glass surface is always visible.
        val sheetBgAlpha = (0.4f + progress * 0.62f).coerceIn(0f, 0.85f)

        GlassySurface(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset { IntOffset(0, sheetOffsetY.value.toInt()) }
                .padding(start = 4.dp, end = 4.dp)
                .fillMaxWidth()
                .height(sheetHeight),
            shape = RoundedCornerShape(40.dp),
            cornerRadius = 40.dp,
            color = Color.White.copy(alpha = sheetBgAlpha),
            border = BorderStroke(1.dp, Color.White.copy(alpha = 0.5f))
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Drag handle area (also clickable to toggle sheet)
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .pointerInput(collapsedOffsetPx, maxExpandedOffsetPx, contentOffsetPx) {
                            detectDragGestures(
                                onDragStart = {
                                    isDragging = true
                                },
                                onDragCancel = {
                                    isDragging = false
                                },
                                onDragEnd = {
                                    isDragging = false
                                    val targets = if (uiState.selectedWindmill != null) {
                                        listOf(
                                            collapsedOffsetPx to SheetState.COLLAPSED,
                                            contentOffsetPx to SheetState.HALF_EXPANDED,
                                            maxExpandedOffsetPx to SheetState.FULLY_EXPANDED
                                        )
                                    } else {
                                        listOf(
                                            collapsedOffsetPx to SheetState.COLLAPSED,
                                            maxExpandedOffsetPx to SheetState.FULLY_EXPANDED
                                        )
                                    }
                                    val closest =
                                        targets.minByOrNull { kotlin.math.abs(sheetOffsetY.value - it.first) }
                                            ?: (collapsedOffsetPx to SheetState.COLLAPSED)
                                    sheetState = closest.second
                                    coroutineScope.launch {
                                        sheetOffsetY.animateTo(
                                            closest.first,
                                            animationSpec = tween(250)
                                        )
                                    }
                                },
                                onDrag = { change, dragAmount ->
                                    change.consume()
                                    val newOffset = (sheetOffsetY.value + dragAmount.y)
                                        .coerceIn(maxExpandedOffsetPx, collapsedOffsetPx)
                                    coroutineScope.launch {
                                        sheetOffsetY.snapTo(newOffset)
                                    }
                                }
                            )
                        }
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) {
                            sheetState = if (sheetState == SheetState.COLLAPSED) {
                                if (uiState.selectedWindmill != null) SheetState.HALF_EXPANDED else SheetState.FULLY_EXPANDED
                            } else {
                                SheetState.COLLAPSED
                            }
                        }
                        .padding(top = 10.dp, bottom = 12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Drag handle line
                    Box(
                        modifier = Modifier
                            .width(36.dp)
                            .height(4.dp)
                            .background(Color.Gray.copy(alpha = 0.4f), RoundedCornerShape(2.dp))
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    // Header state: selected windmill details or scanning or displaying count
                    if (uiState.selectedWindmill != null) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 24.dp, vertical = 4.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = uiState.selectedWindmill.name,
                                color = AppColors.PureDark,
                                fontSize = 17.sp,
                                fontWeight = FontWeight.SemiBold,
                                letterSpacing = (-0.43).sp,
                                textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                                modifier = Modifier.padding(horizontal = 44.dp)
                            )
                            IconButton(
                                onClick = {
                                    coroutineScope.launch {
                                        sheetState = SheetState.COLLAPSED
                                        delay(300)
                                        onWindmillSelected(null)
                                    }
                                },
                                modifier = Modifier
                                    .size(36.dp)
                                    .background(AppColors.LightGray, CircleShape)
                                    .align(Alignment.CenterEnd)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Schließen",
                                    tint = AppColors.DarkGray.copy(alpha = 0.6f),
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    } else if (uiState.isScanning) {
                        // Progress bar during scan
                        Box(
                            modifier = Modifier
                                .width(150.dp)
                                .height(6.dp)
                                .background(
                                    Color.Gray.copy(alpha = 0.15f),
                                    RoundedCornerShape(3.dp)
                                )
                        ) {
                            var progress by remember { mutableStateOf(0f) }
                            LaunchedEffect(uiState.isScanning) {
                                val animSpec = tween<Float>(durationMillis = 2500)
                                val animState = Animatable(0f)
                                animState.animateTo(1f, animationSpec = animSpec) {
                                    progress = this.value
                                }
                            }
                            Box(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .fillMaxWidth(progress)
                                    .background(AppColors.Blue, RoundedCornerShape(3.dp))
                            )
                        }
                    } else {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 24.dp, vertical = 4.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = if (sheetState != SheetState.COLLAPSED) "In deiner Nähe" else "${uiState.windparkCount} Windparks",
                                color = AppColors.PureDark,
                                fontSize = 17.sp,
                                fontWeight = FontWeight.SemiBold,
                                letterSpacing = (-0.43).sp,
                                textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                                modifier = Modifier.padding(horizontal = 44.dp)
                            )
                            if (sheetState != SheetState.COLLAPSED) {
                                IconButton(
                                    onClick = {
                                        coroutineScope.launch {
                                            sheetState = SheetState.COLLAPSED
                                        }
                                    },
                                    modifier = Modifier
                                        .size(36.dp)
                                        .background(AppColors.LightGray, CircleShape)
                                        .align(Alignment.CenterEnd)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Close,
                                        contentDescription = "Schließen",
                                        tint = AppColors.DarkGray.copy(alpha = 0.6f),
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                            }
                        }
                    }
                }

                // Sheet Body Content (revealed when expanded)
                Spacer(modifier = Modifier.height(8.dp))
                HorizontalDivider(color = Color.Black.copy(alpha = 0.05f * progress))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .alpha(progress)
                ) {
                    if (uiState.selectedWindmill != null) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .verticalScroll(rememberScrollState()),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            val hash = uiState.selectedWindmill.name.hashCode()
                            val windSpeed = 15 + (kotlin.math.abs(hash) % 36)
                            val maxPower = 1.5 + (kotlin.math.abs(hash) % 40) / 10.0
                            val currentPower =
                                (maxPower * (windSpeed / 50.0)).coerceAtMost(maxPower)
                            val powerStr = "${(currentPower * 10).toInt() / 10.0} MW"

                            if (fullyExpandedProgress > 0f) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height((200 * fullyExpandedProgress).dp)
                                        .alpha(fullyExpandedProgress),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(horizontal = 16.dp, vertical = 4.dp)
                                    ) {
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(48.dp),
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            Text(
                                                text = "$windSpeed km/h",
                                                color = AppColors.PureDark,
                                                fontSize = 17.sp,
                                                fontWeight = FontWeight.Normal,
                                                letterSpacing = (-0.43).sp
                                            )
                                            Text(
                                                text = powerStr,
                                                color = AppColors.PureDark,
                                                fontSize = 17.sp,
                                                fontWeight = FontWeight.Normal,
                                                letterSpacing = (-0.43).sp
                                            )
                                        }
                                        HorizontalDivider(color = Color.Black.copy(alpha = 0.08f))
                                    }

                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .weight(1f),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        if (lottieJsonString != null && composition != null) {
                                            Image(
                                                painter = rememberLottiePainter(
                                                    composition = composition,
                                                    progress = { lottieProgress }
                                                ),
                                                contentDescription = "Windrad Animation",
                                                modifier = Modifier.size(306.dp, 143.dp)
                                            )
                                        }
                                    }
                                }
                            }

                            WindmillDetailsCard(
                                details = uiState.selectedWindmill,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .onGloballyPositioned { coordinates ->
                                        detailsCardHeightDp = with(density) {
                                            coordinates.size.height.toDp()
                                        }
                                    }
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    } else if (uiState.windparkCount == 0) {
                        // Expanded Placeholder when 0 Windparks
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 24.dp, vertical = 16.dp)
                                .padding(top = 32.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "In deiner Nähe",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = AppColors.DarkText
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Scanne deine Umgebung, um Windparks in deiner Nähe freizuschalten.",
                                fontSize = 14.sp,
                                color = Color.Gray,
                                modifier = Modifier.padding(horizontal = 24.dp),
                                textAlign = androidx.compose.ui.text.style.TextAlign.Center
                            )
                            Spacer(modifier = Modifier.height(24.dp))
                            Button(
                                onClick = onScanClicked,
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = AppColors.Blue
                                ),
                                shape = RoundedCornerShape(25.dp),
                                modifier = Modifier
                                    .width(151.dp)
                                    .height(50.dp),
                                enabled = !uiState.isScanning,
                                elevation = ButtonDefaults.buttonElevation(
                                    defaultElevation = 6.dp,
                                    pressedElevation = 2.dp
                                ),
                                contentPadding = PaddingValues(0.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    if (uiState.isScanning) {
                                        ThreeDotsLoadingAnimation()
                                    } else {
                                        Icon(
                                            imageVector = SendIcon,
                                            contentDescription = null,
                                            tint = Color.White,
                                            modifier = Modifier
                                                .rotate(-45f)
                                                .size(18.dp)
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            text = "Scannen",
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.Normal,
                                            color = Color.White
                                        )
                                    }
                                }
                            }
                        }
                    } else {
                        // List of Found Windparks
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 24.dp, vertical = 8.dp)
                        ) {
                            Text(
                                text = "${uiState.windparkCount} Windparks gefunden",
                                fontSize = 15.sp,
                                color = Color.Gray,
                                letterSpacing = (-0.23).sp
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            HorizontalDivider(color = Color.Black.copy(alpha = 0.05f))
                            Spacer(modifier = Modifier.height(4.dp))

                            LazyColumn(
                                modifier = Modifier.weight(1f),
                                verticalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                items(uiState.scannedWindmills) { item ->
                                    Column(modifier = Modifier.fillMaxWidth()) {
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .clickable {
                                                    onWindmillSelected(item.id)
                                                    sheetState = SheetState.HALF_EXPANDED
                                                }
                                                .padding(vertical = 12.dp),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Box(
                                                modifier = Modifier.size(24.dp, 35.dp),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                Image(
                                                    painter = painterResource(Res.drawable.wind_turbine_icon),
                                                    contentDescription = null,
                                                    modifier = Modifier.fillMaxSize()
                                                )
                                            }
                                            Spacer(modifier = Modifier.width(16.dp))
                                            Column(modifier = Modifier.weight(1f)) {
                                                Text(
                                                    text = item.city,
                                                    fontSize = 17.sp,
                                                    fontWeight = FontWeight.Normal,
                                                    color = AppColors.PureDark,
                                                    letterSpacing = (-0.43).sp
                                                )
                                                Text(
                                                    text = item.name,
                                                    fontSize = 15.sp,
                                                    color = Color.Gray,
                                                    letterSpacing = (-0.23).sp
                                                )
                                                Text(
                                                    text = item.distanceText,
                                                    fontSize = 13.sp,
                                                    color = Color.Gray,
                                                    letterSpacing = (-0.23).sp
                                                )
                                            }
                                            IconButton(
                                                onClick = { onToggleFavorite(item.id) },
                                                modifier = Modifier.size(36.dp)
                                            ) {
                                                StarIcon(
                                                    isFilled = item.isFavorite,
                                                    modifier = Modifier.size(24.dp),
                                                    outlineColor = Color.Black
                                                )
                                            }
                                        }
                                        HorizontalDivider(color = Color.Black.copy(alpha = 0.05f))
                                    }
                                }
                                item {
                                    Spacer(modifier = Modifier.height(16.dp))
                                }
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            Box(
                                modifier = Modifier.fillMaxWidth(),
                                contentAlignment = Alignment.Center
                            ) {
                                Button(
                                    onClick = onCollectAllClicked,
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = AppColors.Blue
                                    ),
                                    shape = RoundedCornerShape(25.dp),
                                    modifier = Modifier
                                        .width(151.dp)
                                        .height(50.dp),
                                    contentPadding = PaddingValues(0.dp)
                                ) {
                                    Text(
                                        text = "Alle einsammeln",
                                        color = Color.White,
                                        fontSize = 17.sp,
                                        fontWeight = FontWeight.Normal,
                                        letterSpacing = (-0.43).sp
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }
                }

                WindradBottomBar(
                    currentDestination = currentDestination,
                    onDestinationSelected = onDestinationSelected,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .padding(bottom = 16.dp)
                )
            }
        }

        // 4. Floating Scan FAB Button (Anchored dynamically above the bottom sheet)
        val fabOffset = with(density) {
            val sheetHeightAboveBottom = screenHeight - (sheetOffsetY.value / density.density).dp
            // collapsed offset is roughly maxOffset, which places it at 146.dp.
            // We float 16.dp above the top edge of the bottom sheet
            -(sheetHeightAboveBottom + 16.dp)
        }

        Button(
            onClick = onScanClicked,
            colors = ButtonDefaults.buttonColors(
                containerColor = AppColors.Blue
            ),
            shape = RoundedCornerShape(25.dp),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .offset(y = fabOffset)
                .width(151.dp)
                .height(50.dp)
                .alpha(1f - progress),
            enabled = progress < 0.5f,
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 6.dp,
                pressedElevation = 2.dp
            ),
            contentPadding = PaddingValues(0.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                if (uiState.isScanning) {
                    // Scanning animated 3 dots
                    ThreeDotsLoadingAnimation()
                } else {
                    Icon(
                        imageVector = SendIcon,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier
                            .rotate(-45f)
                            .size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Scannen",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color.White
                    )
                }
            }
        }
    }
}

data class WindparkItem(
    val name: String,
    val details: String,
    val status: String
)

private val SendIcon: ImageVector
    get() = ImageVector.Builder(
        name = "Send",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).path(
        fill = SolidColor(Color.White),
        fillAlpha = 1.0f,
        strokeAlpha = 1.0f,
        strokeLineWidth = 0.0f,
        strokeLineCap = StrokeCap.Butt,
        pathFillType = PathFillType.NonZero
    ) {
        moveTo(2.01f, 21f)
        lineTo(23f, 12f)
        lineTo(2.01f, 3f)
        lineTo(2f, 10f)
        lineTo(17f, 12f)
        lineTo(2f, 14f)
        lineTo(2.01f, 21f)
        close()
    }.build()

private val MicIcon: ImageVector
    get() = ImageVector.Builder(
        name = "Mic",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).path(
        fill = SolidColor(Color.Gray),
        fillAlpha = 1.0f,
        strokeAlpha = 1.0f,
        strokeLineWidth = 0.0f,
        strokeLineCap = StrokeCap.Butt,
        pathFillType = PathFillType.NonZero
    ) {
        moveTo(12f, 2f)
        curveTo(10.34f, 2f, 9f, 3.34f, 9f, 5f)
        lineTo(9f, 11f)
        curveTo(9f, 12.66f, 10.34f, 14f, 12f, 14f)
        curveTo(13.66f, 14f, 15f, 12.66f, 15f, 11f)
        lineTo(15f, 5f)
        curveTo(15f, 3.34f, 13.66f, 2f, 12f, 2f)
        close()
        moveTo(17.3f, 11f)
        curveTo(17.3f, 14f, 14.76f, 16.1f, 12f, 16.1f)
        curveTo(9.24f, 16.1f, 6.7f, 14f, 6.7f, 11f)
        lineTo(5f, 11f)
        curveTo(5f, 14.42f, 7.72f, 17.23f, 11f, 17.72f)
        lineTo(11f, 21f)
        lineTo(13f, 21f)
        lineTo(13f, 17.72f)
        curveTo(16.28f, 17.23f, 19f, 14.42f, 19f, 11f)
        lineTo(17.3f, 11f)
        close()
    }.build()

enum class SheetState {
    COLLAPSED,
    HALF_EXPANDED,
    FULLY_EXPANDED
}

@Composable
fun ThreeDotsLoadingAnimation(
    modifier: Modifier = Modifier,
    dotSize: Dp = 6.dp,
    dotColor: Color = Color.White,
    gap: Dp = 4.dp
) {
    val infiniteTransition = rememberInfiniteTransition()

    val offset1 by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = -6f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 600
                0f at 0 with androidx.compose.animation.core.FastOutSlowInEasing
                -6f at 150 with androidx.compose.animation.core.FastOutSlowInEasing
                0f at 300 with androidx.compose.animation.core.FastOutSlowInEasing
                0f at 600
            },
            repeatMode = RepeatMode.Restart,
            initialStartOffset = StartOffset(0)
        )
    )

    val offset2 by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = -6f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 600
                0f at 0 with androidx.compose.animation.core.FastOutSlowInEasing
                -6f at 150 with androidx.compose.animation.core.FastOutSlowInEasing
                0f at 300 with androidx.compose.animation.core.FastOutSlowInEasing
                0f at 600
            },
            repeatMode = RepeatMode.Restart,
            initialStartOffset = StartOffset(150)
        )
    )

    val offset3 by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = -6f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 600
                0f at 0 with androidx.compose.animation.core.FastOutSlowInEasing
                -6f at 150 with androidx.compose.animation.core.FastOutSlowInEasing
                0f at 300 with androidx.compose.animation.core.FastOutSlowInEasing
                0f at 600
            },
            repeatMode = RepeatMode.Restart,
            initialStartOffset = StartOffset(300)
        )
    )

    Row(
        modifier = modifier.height(18.dp),
        horizontalArrangement = Arrangement.spacedBy(gap),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(dotSize)
                .offset(y = offset1.dp)
                .background(dotColor, CircleShape)
        )
        Box(
            modifier = Modifier
                .size(dotSize)
                .offset(y = offset2.dp)
                .background(dotColor, CircleShape)
        )
        Box(
            modifier = Modifier
                .size(dotSize)
                .offset(y = offset3.dp)
                .background(dotColor, CircleShape)
        )
    }
}

@Composable
fun StarIcon(
    isFilled: Boolean,
    modifier: Modifier = Modifier,
    filledColor: Color = AppColors.Blue,
    outlineColor: Color = Color(0xFF1E1E1E)
) {
    androidx.compose.foundation.Canvas(modifier = modifier) {
        val sizeMin = size.minDimension
        val center = androidx.compose.ui.geometry.Offset(size.width / 2f, size.height / 2f)
        val outerRadius = sizeMin / 2f
        val innerRadius = outerRadius * 0.4f

        val path = androidx.compose.ui.graphics.Path().apply {
            for (i in 0 until 10) {
                val angle = i * kotlin.math.PI / 5 - kotlin.math.PI / 2
                val radius = if (i % 2 == 0) outerRadius else innerRadius
                val x = (center.x + kotlin.math.cos(angle) * radius).toFloat()
                val y = (center.y + kotlin.math.sin(angle) * radius).toFloat()
                if (i == 0) {
                    moveTo(x, y)
                } else {
                    lineTo(x, y)
                }
            }
            close()
        }

        if (isFilled) {
            drawPath(path, color = filledColor)
            drawPath(
                path,
                color = filledColor,
                style = androidx.compose.ui.graphics.drawscope.Stroke(
                    width = 1.5.dp.toPx(),
                    cap = StrokeCap.Round,
                    join = androidx.compose.ui.graphics.StrokeJoin.Round
                )
            )
        } else {
            drawPath(
                path,
                color = outlineColor,
                style = androidx.compose.ui.graphics.drawscope.Stroke(
                    width = 1.5.dp.toPx(),
                    cap = StrokeCap.Round,
                    join = androidx.compose.ui.graphics.StrokeJoin.Round
                )
            )
        }
    }
}



