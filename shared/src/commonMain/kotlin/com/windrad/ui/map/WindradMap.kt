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
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.withTransform
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

                    val left = x - 19.dp
                    val top = y - 41.dp

                    WindradPin(
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
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .width(38.dp)
            .height(41.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val circleRadius = 16.dp.toPx()
            val centerX = size.width / 2f
            val centerY = circleRadius + 1.dp.toPx() // Slight offset from top
            val tipY = size.height - 2.dp.toPx() // Bottom tip
            
            val path = Path().apply {
                addArc(
                    oval = Rect(
                        centerX - circleRadius,
                        centerY - circleRadius,
                        centerX + circleRadius,
                        centerY + circleRadius
                    ),
                    startAngleDegrees = 40f,
                    sweepAngleDegrees = -260f
                )
                // Curve down to the tip
                lineTo(centerX, tipY)
                close()
            }

            // Draw soft shadow under the droplet tip
            drawCircle(
                color = Color.Black.copy(alpha = 0.15f),
                radius = 3.dp.toPx(),
                center = androidx.compose.ui.geometry.Offset(centerX, tipY + 1.dp.toPx())
            )

            // Draw white fill (the outer droplet)
            drawPath(
                path = path,
                color = Color.White
            )

            // Draw inner primary blue circle
            drawCircle(
                color = Color(0xFF007AFF), // Bright iOS-like blue
                radius = 12.dp.toPx(),
                center = androidx.compose.ui.geometry.Offset(centerX, centerY)
            )

            // Draw Wind Turbine Icon (White)
            // The icon in Figma is exactly 10x15 dp.
            // We draw it centered within the blue circle.
            val iconWidth = 10.dp.toPx()
            val iconHeight = 15.dp.toPx()
            
            // The center of the wind turbine's rotor is about 37.5% down from its top
            val rotorCx = centerX
            val rotorCy = centerY - (iconHeight / 2f) + (iconHeight * 0.375f)
            val rotorCenter = androidx.compose.ui.geometry.Offset(rotorCx, rotorCy)

            val stroke = 1.8.dp.toPx()
            val gap = 1.0.dp.toPx()
            val dotRadius = 0.9.dp.toPx()
            val bladeLen = 3.5.dp.toPx()
            val poleLen = 7.0.dp.toPx()

            // Center dot
            drawCircle(
                color = Color.White,
                radius = dotRadius,
                center = rotorCenter
            )

            val bladeStartOffset = dotRadius + gap

            // Top blade
            drawLine(
                color = Color.White,
                start = androidx.compose.ui.geometry.Offset(rotorCx, rotorCy - bladeStartOffset),
                end = androidx.compose.ui.geometry.Offset(rotorCx, rotorCy - bladeStartOffset - bladeLen),
                strokeWidth = stroke,
                cap = StrokeCap.Round
            )

            // Pole (pointing down)
            drawLine(
                color = Color.White,
                start = androidx.compose.ui.geometry.Offset(rotorCx, rotorCy + bladeStartOffset),
                end = androidx.compose.ui.geometry.Offset(rotorCx, rotorCy + bladeStartOffset + poleLen),
                strokeWidth = stroke,
                cap = StrokeCap.Round
            )

            // Right blade (120 degrees)
            withTransform({
                rotate(120f, rotorCenter)
            }) {
                drawLine(
                    color = Color.White,
                    start = androidx.compose.ui.geometry.Offset(rotorCx, rotorCy - bladeStartOffset),
                    end = androidx.compose.ui.geometry.Offset(rotorCx, rotorCy - bladeStartOffset - bladeLen),
                    strokeWidth = stroke,
                    cap = StrokeCap.Round
                )
            }

            // Left blade (-120 degrees)
            withTransform({
                rotate(-120f, rotorCenter)
            }) {
                drawLine(
                    color = Color.White,
                    start = androidx.compose.ui.geometry.Offset(rotorCx, rotorCy - bladeStartOffset),
                    end = androidx.compose.ui.geometry.Offset(rotorCx, rotorCy - bladeStartOffset - bladeLen),
                    strokeWidth = stroke,
                    cap = StrokeCap.Round
                )
            }
        }
    }
}
