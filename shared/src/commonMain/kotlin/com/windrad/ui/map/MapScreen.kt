package com.windrad.ui.map

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.windrad.ui.navigation.WindradBottomBar
import com.windrad.ui.navigation.WindradDestination
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll

@Composable
fun MapScreen(
    uiState: MapUiState,
    onScanClicked: () -> Unit,
    onWindmillSelected: (String?) -> Unit,
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
        val sheetOffsetY = remember { Animatable(collapsedOffsetPx) }
        
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
            val target = when (sheetState) {
                SheetState.COLLAPSED -> collapsedOffsetPx
                SheetState.HALF_EXPANDED -> contentOffsetPx
                SheetState.FULLY_EXPANDED -> maxExpandedOffsetPx
            }
            sheetOffsetY.animateTo(target, animationSpec = tween(300))
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

        // 2. Floating Search Bar
        Surface(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .statusBarsPadding()
                .padding(top = 16.dp, start = 12.dp, end = 12.dp)
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
                    tint = Color.Gray,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "Suchen",
                    color = Color.Gray,
                    fontSize = 15.sp,
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    imageVector = MicIcon,
                    contentDescription = "Sprachsuche",
                    tint = Color.Gray,
                    modifier = Modifier.size(20.dp)
                )
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

        Surface(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset { IntOffset(0, sheetOffsetY.value.toInt()) }
                .padding(start = 4.dp, end = 4.dp)
                .fillMaxWidth()
                .height(sheetHeight),
            shape = RoundedCornerShape(40.dp),
            color = Color(0xF2FFFFFF), // 95% White Glass for uniform milky effect
            border = BorderStroke(1.dp, Color.White.copy(alpha = 0.5f)), // sharp white glass border edge
            shadowElevation = 12.dp // stronger shadow to create height hierarchy
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
                                onDragEnd = {
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
                                    val closest = targets.minByOrNull { kotlin.math.abs(sheetOffsetY.value - it.first) } 
                                        ?: (collapsedOffsetPx to SheetState.COLLAPSED)
                                    sheetState = closest.second
                                    coroutineScope.launch {
                                        sheetOffsetY.animateTo(closest.first, animationSpec = tween(250))
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
                                color = Color(0xFF1A1A1A),
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
                                    .background(Color(0xFFE5E5EA), CircleShape)
                                    .align(Alignment.CenterEnd)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Schließen",
                                    tint = Color(0xFF3C3C43).copy(alpha = 0.6f),
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
                                .background(Color.Gray.copy(alpha = 0.15f), RoundedCornerShape(3.dp))
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
                                    .background(Color(0xFF1D7AED), RoundedCornerShape(3.dp))
                            )
                        }
                    } else {
                        // Windpark count label
                        Text(
                            text = "${uiState.windparkCount} Windparks",
                            color = Color(0xFF1A1C1E),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold
                        )
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
                                .verticalScroll(rememberScrollState())
                        ) {
                            WindmillDetailsCard(
                                details = uiState.selectedWindmill,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .onGloballyPositioned { coordinates ->
                                        detailsCardHeightDp = with(density) { coordinates.size.height.toDp() }
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
                                color = Color(0xFF1A1C1E)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Scanne deine Umgebung, um Windparks in deiner Nähe freizuschalten.",
                                fontSize = 14.sp,
                                color = Color.Gray,
                                modifier = Modifier.padding(horizontal = 24.dp),
                                textAlign = androidx.compose.ui.text.style.TextAlign.Center
                            )
                        }
                    } else {
                        // List of Found Windparks
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 24.dp, vertical = 16.dp)
                        ) {
                            Text(
                                text = "In deiner Nähe",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF1A1C1E)
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "${uiState.windparkCount} Windparks gefunden",
                                fontSize = 14.sp,
                                color = Color.Gray
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            
                            LazyColumn(
                                modifier = Modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                items(listOf(
                                    WindparkItem("Windpark Erfurt", "1 Windkraftanlage", "Aktiv"),
                                    WindparkItem("Windpark Dessau-Halle", "2 Windkraftanlagen", "Aktiv")
                                )) { item ->
                                    Surface(
                                        modifier = Modifier.fillMaxWidth(),
                                        shape = RoundedCornerShape(16.dp),
                                        color = Color.White.copy(alpha = 0.8f),
                                        border = BorderStroke(1.dp, Color.White.copy(alpha = 0.5f))
                                    ) {
                                        Row(
                                            modifier = Modifier.padding(16.dp),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Surface(
                                                modifier = Modifier.size(40.dp),
                                                shape = RoundedCornerShape(8.dp),
                                                color = Color(0xFF1D7AED)
                                            ) {
                                                Box(contentAlignment = Alignment.Center) {
                                                    Text("🛩", fontSize = 18.sp, color = Color.White)
                                                }
                                            }
                                            Spacer(modifier = Modifier.width(16.dp))
                                            Column(modifier = Modifier.weight(1f)) {
                                                Text(
                                                    text = item.name,
                                                    fontSize = 15.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    color = Color(0xFF1A1C1E)
                                                )
                                                Text(
                                                    text = item.details,
                                                    fontSize = 13.sp,
                                                    color = Color.Gray
                                                )
                                            }
                                            Surface(
                                                color = Color(0xFFE2F0D9),
                                                shape = RoundedCornerShape(8.dp)
                                            ) {
                                                Text(
                                                    text = item.status,
                                                    color = Color(0xFF385723),
                                                    fontSize = 12.sp,
                                                    fontWeight = FontWeight.SemiBold,
                                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                                )
                                            }
                                        }
                                    }
                                }
                                // Extra spacer to ensure content is not covered by bottom bar
                                item {
                                    Spacer(modifier = Modifier.height(96.dp))
                                }
                            }
                        }
                    }
                }
                
                WindradBottomBar(
                    currentDestination = currentDestination,
                    onDestinationSelected = onDestinationSelected,
                    modifier = Modifier.fillMaxWidth()
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
                containerColor = Color(0xFF1D7AED) // Primary Blue
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
                    Text(
                        text = "•••",
                        fontSize = 18.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
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

