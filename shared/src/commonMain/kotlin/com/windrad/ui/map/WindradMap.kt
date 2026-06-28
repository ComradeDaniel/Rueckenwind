package com.windrad.ui.map

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.painter.Painter
import org.jetbrains.compose.resources.painterResource
import windrad.shared.generated.resources.*
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.windrad.ui.theme.AppColors
import org.maplibre.compose.camera.CameraPosition
import org.maplibre.compose.camera.rememberCameraState
import org.maplibre.compose.expressions.dsl.*
import org.maplibre.compose.layers.CircleLayer
import org.maplibre.compose.layers.SymbolLayer
import org.maplibre.compose.map.MapOptions
import org.maplibre.compose.map.MaplibreMap
import org.maplibre.compose.map.OrnamentOptions
import org.maplibre.compose.sources.GeoJsonData
import org.maplibre.compose.sources.GeoJsonOptions
import org.maplibre.compose.sources.rememberGeoJsonSource
import org.maplibre.compose.style.BaseStyle
import org.maplibre.compose.util.ClickResult
import org.maplibre.compose.expressions.value.TranslateAnchor
import org.maplibre.spatialk.geojson.Point
import org.maplibre.spatialk.geojson.Position

/**
 * Karten-Composable für die Windrad-App.
 *
 * Nutzt die native MapLibre C++-Clustering-Engine via GeoJsonSource (cluster = true).
 * Layer-Struktur:
 *   1. CircleLayer "windrad-clusters"       – farbige Kreise für Cluster
 *   2. SymbolLayer "windrad-cluster-count"  – Anzahl-Text über dem Kreis
 *   3. SymbolLayer "windrad-single"         – WindradPin-Icon für Einzelturbinen
 *
 * Cluster-Klick zoomt automatisch zum clusterExpansionZoom hinein.
 *
 * Das `collected`-Property in den GeoJSON-Features ist die Grundlage für spätere
 * farbliche Unterscheidung gesammelter Turbinen (collected == 1 → grün).
 * TODO: Für gesammelte Turbinen einen zweiten SymbolLayer "windrad-single-collected" mit
 *       filter = feature["collected"].asNumber() eq const(1f) und
 *       WindradPinPainter(isCollected = true) hinzufügen.
 */
@Composable
fun WindradMap(
    centerLatitude: Double,
    centerLongitude: Double,
    zoom: Double,
    windmillGeoJson: String?,
    onZoomChanged: (Double) -> Unit,
    onWindmillClicked: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val cameraState = rememberCameraState(
        CameraPosition(
            target = Position(longitude = centerLongitude, latitude = centerLatitude),
            zoom = zoom
        )
    )

    // Kamera bei externer Koordinaten- oder Zoomänderung nachführen
    LaunchedEffect(centerLatitude, centerLongitude, zoom) {
        val current = cameraState.position
        val latDiff = kotlin.math.abs(current.target.latitude - centerLatitude)
        val lonDiff = kotlin.math.abs(current.target.longitude - centerLongitude)
        val zoomDiff = kotlin.math.abs(current.zoom - zoom)
        if (latDiff > 0.0001 || lonDiff > 0.0001 || zoomDiff > 0.1) {
            cameraState.position = CameraPosition(
                target = Position(longitude = centerLongitude, latitude = centerLatitude),
                zoom = zoom
            )
        }
    }

    // Zoom-Änderungen nach oben melden
    LaunchedEffect(cameraState.position.zoom) {
        onZoomChanged(cameraState.position.zoom)
    }

    val turbinePainter = painterResource(Res.drawable.wind_turbine_icon_white)

    // WindradPin-Varianten als Painter-Objekte für die MapLibre-Sprite-Registrierung.
    val pinBlue = remember(turbinePainter) { WindradPinPainter(AppColors.MapsBlue, turbinePainter) }
    val pinGreen = remember(turbinePainter) { WindradPinPainter(AppColors.Green, turbinePainter) }
    val pinYellow = remember(turbinePainter) { WindradPinPainter(AppColors.Yellow, turbinePainter) }
    val pinGray = remember(turbinePainter) { WindradPinPainter(AppColors.Gray, turbinePainter) }

    if (windmillGeoJson == null) {
        androidx.compose.foundation.layout.Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = androidx.compose.ui.Alignment.Center
        ) {
            androidx.compose.material3.CircularProgressIndicator(
                color = AppColors.AccentBlue
            )
        }
    } else {
        var isMapLoaded by remember { mutableStateOf(false) }
        MaplibreMap(
            modifier = modifier.fillMaxSize(),
            cameraState = cameraState,
            options = MapOptions(ornamentOptions = OrnamentOptions.AllDisabled),
            baseStyle = BaseStyle.Uri("https://raw.githubusercontent.com/kalwinskidawid/openfreemap-translations/main/styles/liberty_de.json"),
            onMapLoadFinished = {
                println("WindradMap: onMapLoadFinished fired!")
                isMapLoaded = true
            },
            content = {
                if (isMapLoaded) {
                    val source = rememberGeoJsonSource(
                    data = GeoJsonData.JsonString(windmillGeoJson),
                    options = GeoJsonOptions(
                        cluster = true,
                        clusterRadius = 60,   // Pixel-Radius in 1/512-Tile-Einheiten
                        clusterMaxZoom = 13,  // Zoom ≥ 14: Einzelturbinen sichtbar
                        clusterMinPoints = 2,
                    )
                )

                // ── Anchoring custom layers at the top ─────────────────────────────
                org.maplibre.compose.layers.Anchor.Top {
                    val imgBlue = image(pinBlue, DpSize(38.dp, 41.dp))
                    val imgGreen = image(pinGreen, DpSize(38.dp, 41.dp))
                    val imgYellow = image(pinYellow, DpSize(38.dp, 41.dp))
                    val imgGray = image(pinGray, DpSize(38.dp, 41.dp))

                    // ── Layer 1: Cluster-Basis (Windrad-Pin) ────────────────────────────────
                    // Filter: nur Features mit point_count-Property (= vom Engine erzeugte Cluster)
                    // Zeigt das Standard-Windrad-Pin für alle Cluster
                    SymbolLayer(
                        id = "windrad-cluster-base",
                        source = source,
                        filter = feature.has("point_count"),
                        iconImage = imgBlue,
                        iconAllowOverlap = const(true),
                        onClick = { features ->
                            // Cluster-Klick: zur nächsten Zoom-Stufe zoomen, bei der der Cluster auflöst
                            val clickedFeature = features.firstOrNull()
                            if (clickedFeature != null && source.isCluster(clickedFeature)) {
                                val expansionZoom = source.getClusterExpansionZoom(clickedFeature)
                                val point = clickedFeature.geometry
                                val target = if (point is Point) point.coordinates else cameraState.position.target
                                cameraState.position = CameraPosition(
                                    target = target,
                                    zoom = expansionZoom,
                                    bearing = cameraState.position.bearing,
                                    tilt = cameraState.position.tilt
                                )
                            }
                            ClickResult.Consume
                        }
                    )

                    // ── Layer 2: Cluster-Badge (Weißer Kreis im Vordergrund) ────────────────
                    // Zeichnet einen weißen Kreis mit blauem Rand am oberen rechten Rand des Pins
                    CircleLayer(
                        id = "windrad-cluster-badge",
                        source = source,
                        filter = feature.has("point_count"),
                        radius = step(
                            input = feature["point_count"].asNumber(),
                            fallback = const(11.dp),
                            10 to const(13.dp),
                            100 to const(15.dp),
                            1000 to const(17.dp)
                        ),
                        color = const(Color.White),
                        strokeWidth = const(0.dp),
                        strokeColor = const(Color.Transparent),
                        opacity = const(1.0f),
                        translate = const(DpOffset(16.dp, -16.dp)),
                        translateAnchor = const(TranslateAnchor.Viewport),
                    )

                    val count = feature["point_count"].asNumber()
                    val scaledValue = round(count / const(100f))
                    val integerPart = floor(scaledValue / const(10f))
                    val remainder = scaledValue % const(10)

                    // ── Layer 3: Cluster-Badge-Zähler (Zahl im weißen Kreis) ───────────────
                    // Zeigt die Anzahl geclusterter Punkte über dem Badge-Kreis
                    SymbolLayer(
                        id = "windrad-cluster-badge-count",
                        source = source,
                        filter = feature.has("point_count"),
                        textField = format(
                            span(
                                switch(
                                    condition(
                                        test = count lt const(1000f),
                                        output = count.convertToString()
                                    ),
                                    condition(
                                        test = count lt const(10000f),
                                        output = switch(
                                            condition(
                                                test = remainder eq const(0),
                                                output = integerPart.convertToString() + const("k")
                                            ),
                                            fallback = integerPart.convertToString() + const(".") + remainder.convertToString() + const("k")
                                        )
                                    ),
                                    fallback = round(count / const(1000f)).convertToString() + const("k")
                                )
                            )
                        ),
                        textFont = const(listOf("Noto Sans Bold")),
                        textColor = const(AppColors.AccentBlue),
                        textSize = const(10.5.sp),
                        textHaloColor = const(Color.Transparent),
                        textAllowOverlap = const(true),
                        textIgnorePlacement = const(true),
                        iconAllowOverlap = const(true),
                        textTranslate = const(DpOffset(16.dp, -16.dp)),
                        textTranslateAnchor = const(TranslateAnchor.Viewport),
                    )

                    // ── Layer 3: Einzelne Windräder (WindradPin-Icon) ──────────────────────
                    // Filter: Features OHNE point_count (= nicht vom Engine geclustert)
                    // Zeigt den WindradPin-Painter als Symbol-Icon
                    SymbolLayer(
                        id = "windrad-single",
                        source = source,
                        filter = !feature.has("point_count"),
                        iconImage = switch(
                            condition(
                                test = feature["status"].asString() eq const("In Planung"),
                                output = imgYellow
                            ),
                            condition(
                                test = feature["status"].asString() eq const("In Betrieb"),
                                output = imgGreen
                            ),
                            fallback = imgGray
                        ),
                        iconAllowOverlap = const(true),
                        onClick = { features ->
                            val clickedFeature = features.firstOrNull()
                            if (clickedFeature != null) {
                                val id = clickedFeature.properties?.get("id")?.toString()?.removeSurrounding("\"")
                                if (id != null) {
                                    onWindmillClicked(id)
                                }
                            }
                            ClickResult.Consume
                        }
                    )
                }
                }
            }
        )
    }
}

/**
 * Painter-Implementierung des WindradPin für die Verwendung als MapLibre Sprite-Icon.
 *
 * Die maplibre-compose `image(Painter)`-API rendert diesen Painter zu einem Bitmap und
 * registriert ihn automatisch als Sprite im MapLibre-Style — keine plattformspezifische
 * Konvertierung nötig.
 *
 * @param isCollected true = grüner Pin (gesammelt), false = blauer Pin (noch nicht gesammelt)
 */
class WindradPinPainter(val pinColor: Color, val turbinePainter: Painter) : Painter() {

    // Intrinsische Größe des Pins in Pixeln (wird durch die DpSize in image() überschrieben)
    override val intrinsicSize = Size(38f, 41f)

    override fun DrawScope.onDraw() {
        val circleRadius = 16.dp.toPx()
        val centerX = size.width / 2f
        val centerY = circleRadius + 1.dp.toPx()
        val tipY = size.height - 2.dp.toPx()

        // ── Schatten-Punkt unter dem Pin-Tip ──────────────────────────────────
        drawCircle(
            color = Color.Black.copy(alpha = 0.15f),
            radius = 3.dp.toPx(),
            center = Offset(centerX, tipY + 1.dp.toPx())
        )

        // ── Pin-Form (Tear-Drop: Kreis + Spitze nach unten) ────────────────────
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
            lineTo(centerX, tipY)
            close()
        }
        drawPath(path = path, color = Color.White)

        // ── Pin-Farbkreis ────────────────────────────────────────────────────
        drawCircle(
            color = pinColor,
            radius = 12.dp.toPx(),
            center = Offset(centerX, centerY)
        )

        // ── Windrad-Icon (SVG Turbine in Weiß) ──────────────────────────────
        val hTarget = 18.dp.toPx()
        val wTarget = hTarget * (24f / 35f)
        val targetCx = centerX
        val targetCy = centerY - 1.dp.toPx()

        val left = targetCx - wTarget * (12.0391f / 24f)
        val top = targetCy - hTarget * (13.125f / 35f)

        translate(left = left, top = top) {
            with(turbinePainter) {
                draw(size = Size(wTarget, hTarget))
            }
        }
    }
}
