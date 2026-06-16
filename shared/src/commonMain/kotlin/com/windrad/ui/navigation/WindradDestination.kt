package com.windrad.ui.navigation

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.group
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

enum class WindradDestination(
    val label: String,
    val icon: ImageVector
) {
    MAP("Karte", MapIcon),
    COLLECTION("Sammlung", SammlungIcon),
    FAQ("FAQ", FaqIcon)
}

private val MapIcon: ImageVector by lazy {
    ImageVector.Builder(
        name = "MapIcon",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).path(
        stroke = SolidColor(Color.Black),
        strokeLineWidth = 2f,
        strokeLineCap = StrokeCap.Round,
        strokeLineJoin = StrokeJoin.Round
    ) {
        moveTo(3f, 5f)
        lineTo(9f, 2f)
        lineTo(15f, 5f)
        lineTo(21f, 2f)
        lineTo(21f, 19f)
        lineTo(15f, 22f)
        lineTo(9f, 19f)
        lineTo(3f, 22f)
        close()
        
        moveTo(9f, 2f)
        lineTo(9f, 19f)
        
        moveTo(15f, 5f)
        lineTo(15f, 22f)
    }.build()
}

private val SammlungIcon: ImageVector by lazy {
    ImageVector.Builder(
        name = "SammlungIcon",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).apply {
        // Card Left
        group(
            name = "CardLeft",
            rotate = -20f,
            pivotX = 12f,
            pivotY = 21f,
            translationX = -3.5f,
            translationY = 0.5f
        ) {
            path(
                stroke = SolidColor(Color.Black),
                strokeLineWidth = 2f,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round
            ) {
                moveTo(8f, 10.5f)
                lineTo(8f, 19.5f)
                quadTo(8f, 21f, 9.5f, 21f)
                lineTo(14.5f, 21f)
                quadTo(16f, 21f, 16f, 19.5f)
                lineTo(16f, 10.5f)
                quadTo(16f, 9f, 14.5f, 9f)
                lineTo(9.5f, 9f)
                quadTo(8f, 9f, 8f, 10.5f)
                close()
                
                moveTo(8f, 12.5f)
                lineTo(16f, 12.5f)
            }
        }
        
        // Card Middle
        group(
            name = "CardMiddle",
            rotate = 0f,
            pivotX = 12f,
            pivotY = 21f,
            translationX = 0f,
            translationY = -1.5f
        ) {
            path(
                stroke = SolidColor(Color.Black),
                strokeLineWidth = 2f,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round
            ) {
                moveTo(8f, 10.5f)
                lineTo(8f, 19.5f)
                quadTo(8f, 21f, 9.5f, 21f)
                lineTo(14.5f, 21f)
                quadTo(16f, 21f, 16f, 19.5f)
                lineTo(16f, 10.5f)
                quadTo(16f, 9f, 14.5f, 9f)
                lineTo(9.5f, 9f)
                quadTo(8f, 9f, 8f, 10.5f)
                close()
                
                moveTo(8f, 12.5f)
                lineTo(16f, 12.5f)
            }
        }
        
        // Card Right
        group(
            name = "CardRight",
            rotate = 20f,
            pivotX = 12f,
            pivotY = 21f,
            translationX = 3.5f,
            translationY = 0.5f
        ) {
            path(
                stroke = SolidColor(Color.Black),
                strokeLineWidth = 2f,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round
            ) {
                moveTo(8f, 10.5f)
                lineTo(8f, 19.5f)
                quadTo(8f, 21f, 9.5f, 21f)
                lineTo(14.5f, 21f)
                quadTo(16f, 21f, 16f, 19.5f)
                lineTo(16f, 10.5f)
                quadTo(16f, 9f, 14.5f, 9f)
                lineTo(9.5f, 9f)
                quadTo(8f, 9f, 8f, 10.5f)
                close()
                
                moveTo(8f, 12.5f)
                lineTo(16f, 12.5f)
            }
        }
    }.build()
}

private val FaqIcon: ImageVector by lazy {
    ImageVector.Builder(
        name = "FaqIcon",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).path(
        stroke = SolidColor(Color.Black),
        strokeLineWidth = 2f,
        strokeLineCap = StrokeCap.Round,
        strokeLineJoin = StrokeJoin.Round
    ) {
        moveTo(4f, 4f)
        lineTo(20f, 4f)
        curveTo(21.1f, 4f, 22f, 4.9f, 22f, 6f)
        lineTo(22f, 16f)
        curveTo(22f, 17.1f, 21.1f, 18f, 20f, 18f)
        lineTo(9f, 18f)
        lineTo(4f, 22f)
        lineTo(4f, 18f)
        curveTo(2.9f, 18f, 2f, 17.1f, 2f, 16f)
        lineTo(2f, 6f)
        curveTo(2f, 4.9f, 2.9f, 4f, 4f, 4f)
        close()
    }.build()
}
