package com.windrad.ui.map

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import org.maplibre.compose.camera.CameraPosition
import org.maplibre.compose.camera.rememberCameraState
import org.maplibre.compose.map.MapOptions
import org.maplibre.compose.map.MaplibreMap
import org.maplibre.compose.map.OrnamentOptions
import org.maplibre.compose.style.BaseStyle
import org.maplibre.spatialk.geojson.Position

@Composable
fun WindradMap(
    centerLatitude: Double,
    centerLongitude: Double,
    zoom: Double,
    windparkCount: Int,
    modifier: Modifier = Modifier
) {
    val cameraState = rememberCameraState(
        CameraPosition(
            target = Position(longitude = centerLongitude, latitude = centerLatitude),
            zoom = zoom
        )
    )

    LaunchedEffect(centerLatitude, centerLongitude, zoom) {
        cameraState.position = CameraPosition(
            target = Position(longitude = centerLongitude, latitude = centerLatitude),
            zoom = zoom
        )
    }

    var isMapLoaded by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(1500)
        isMapLoaded = true
    }

    Box(modifier = modifier.fillMaxSize()) {
        MaplibreMap(
            modifier = Modifier.fillMaxSize(),
            cameraState = cameraState,
            options = MapOptions(
                ornamentOptions = OrnamentOptions.AllDisabled
            ),
            baseStyle = BaseStyle.Uri("https://tiles.openfreemap.org/styles/liberty"),
            onMapLoadFinished = {
                println("WindradMap onMapLoadFinished callback fired")
                isMapLoaded = true
            }
        )

        // Read camera position state to react to pans and zooms
        val cameraPosition = cameraState.position
        val projection = cameraState.projection

        if (isMapLoaded && windparkCount > 0 && projection != null) {
            val pins = listOf(
                PinData(Position(longitude = 11.0328, latitude = 50.9787), 1),
                PinData(Position(longitude = 12.2000, latitude = 51.6000), 2)
            )

            pins.forEach { pin ->
                val screenCoord = projection.screenLocationFromPosition(pin.position)
                if (screenCoord != null) {
                    val x = screenCoord.x
                    val y = screenCoord.y

                    val left = x - 22.dp
                    val top = y - 52.dp

                    WindradPin(
                        count = pin.count,
                        modifier = Modifier.offset(x = left, y = top)
                    )
                }
            }
        }
    }
}

private data class PinData(
    val position: Position,
    val count: Int
)

@Composable
private fun WindradPin(
    count: Int,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .width(44.dp)
            .height(56.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        // 1. Droplet Canvas
        Canvas(modifier = Modifier.fillMaxSize()) {
            val circleRadius = 16.dp.toPx()
            val centerX = size.width / 2f
            val centerY = circleRadius + 2.dp.toPx() // offset from top for border
            val tipY = size.height - 4.dp.toPx() // offset from bottom for shadow/border
            
            val path = Path().apply {
                addArc(
                    oval = Rect(
                        centerX - circleRadius,
                        centerY - circleRadius,
                        centerX + circleRadius,
                        centerY + circleRadius
                    ),
                    startAngleDegrees = 45f,
                    sweepAngleDegrees = -270f
                )
                lineTo(centerX, tipY)
                close()
            }

            // Draw soft shadow under the droplet tip
            drawCircle(
                color = Color.Black.copy(alpha = 0.2f),
                radius = 4.dp.toPx(),
                center = androidx.compose.ui.geometry.Offset(centerX, tipY + 1.dp.toPx())
            )

            // Draw primary blue fill
            drawPath(
                path = path,
                color = Color(0xFF1D7AED)
            )

            // Draw white border
            drawPath(
                path = path,
                color = Color.White,
                style = Stroke(
                    width = 2.5.dp.toPx(),
                    join = StrokeJoin.Round
                )
            )
        }

        // 2. Wind Turbine Icon (❃)
        Box(
            modifier = Modifier
                .padding(top = 2.dp) // aligns inside the circle centerY
                .size(32.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "❃",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }

        // 3. Badge with Number at top-right
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(end = 2.dp, top = 2.dp)
                .size(18.dp)
                .background(Color.White, CircleShape)
                .border(BorderStroke(1.5.dp, Color(0xFF1D7AED)), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = count.toString(),
                color = Color(0xFF1D7AED),
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
