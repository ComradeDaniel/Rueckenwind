package com.windrad.ui.map

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.dp
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

/**
 * Gemeinsame, parametrisierte Zeichenfunktion für alle Windrad-Vektorsymbole.
 * Ermöglicht es, das Aussehen aller Windräder in der App an einer zentralen Stelle anzupassen.
 */
fun DrawScope.drawWindmill(
    centerX: Float,
    centerY: Float,
    bladeLength: Float,
    mastLength: Float,
    strokeWidth: Float,
    color: Color,
    gap: Float = 0f,
    hubRadius: Float = 1.8.dp.toPx()
) {
    val center = Offset(centerX, centerY)

    // 1. Nabe (Rotor-Mittelpunkt)
    if (hubRadius > 0f) {
        drawCircle(color = color, radius = hubRadius, center = center)
    }

    // 2. Mast (Kapselform mit gap von der Nabe nach unten gezeichnet)
    if (mastLength > 0f) {
        // Das sichtbare obere Ende der Mastkapsel soll bei centerY + hubRadius + gap liegen.
        // Da die abgerundete Kappe (StrokeCap.Round) um strokeWidth / 2f über die Startkoordinate
        // hinausragt, verschieben wir den Anfangspunkt der Linie nach unten.
        val mastStart = centerY + hubRadius + gap + strokeWidth / 2f
        val mastEnd = centerY + hubRadius + gap + mastLength - strokeWidth / 2f
        
        if (mastEnd > mastStart) {
            drawLine(
                color = color,
                start = Offset(centerX, mastStart),
                end = Offset(centerX, mastEnd),
                strokeWidth = strokeWidth,
                cap = StrokeCap.Round
            )
        }
    }

    // 3. Drei Rotorblätter (Kapselform, Winkel: 0°, 120°, 240°)
    // Das sichtbare innere Ende jeder Blade-Kapsel soll bei hubRadius + gap liegen.
    val bladeStartOffset = hubRadius + gap + strokeWidth / 2f
    val bladeEndOffset = maxOf(bladeStartOffset, hubRadius + gap + bladeLength - strokeWidth / 2f)

    for (angle in listOf(0f, 120f, 240f)) {
        val rad = angle * (PI.toFloat() / 180f)
        val startX = centerX + bladeStartOffset * sin(rad)
        val startY = centerY - bladeStartOffset * cos(rad)
        val endX = centerX + bladeEndOffset * sin(rad)
        val endY = centerY - bladeEndOffset * cos(rad)
        
        drawLine(
            color = color,
            start = Offset(startX, startY),
            end = Offset(endX, endY),
            strokeWidth = strokeWidth,
            cap = StrokeCap.Round
        )
    }
}
