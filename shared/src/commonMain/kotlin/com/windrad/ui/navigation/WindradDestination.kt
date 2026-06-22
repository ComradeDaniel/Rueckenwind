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
        defaultWidth = 47.dp,
        defaultHeight = 30.dp,
        viewportWidth = 47f,
        viewportHeight = 30f
    ).apply {
        // Path 1 (fill = #1E1E1E)
        path(fill = SolidColor(Color(0xFF1E1E1E))) {
            moveTo(37.3118f, 27.617f)
            lineTo(37.2169f, 27.798f)
            curveTo(36.7347f, 28.6144f, 35.7391f, 28.982f, 34.8421f, 28.6751f)
            lineTo(34.6521f, 28.5997f)
            lineTo(23.0267f, 23.2579f)
            lineTo(27.7613f, 23.228f)
            lineTo(37.3118f, 27.617f)
            close()
            moveTo(37.3118f, 27.617f)
            lineTo(35.492f, 26.7803f)
            lineTo(43.8896f, 8.59096f)
            lineTo(45.7107f, 9.42723f)
            lineTo(37.3118f, 27.617f)
            close()
            moveTo(44.7304f, 6.77196f)
            curveTo(45.6727f, 7.20515f, 46.1164f, 8.27725f, 45.786f, 9.23723f)
            lineTo(45.7107f, 9.42723f)
            lineTo(32.0583f, 3.15463f)
            lineTo(30.0193f, 7.57199f)
            lineTo(29.9881f, 2.86161f)
            lineTo(30.2381f, 2.31877f)
            curveTo(30.7019f, 1.31429f, 31.8925f, 0.874392f, 32.8978f, 1.3361f)
            lineTo(44.7304f, 6.77196f)
            close()
        }
        
        // Path 2 (stroke = #1E1E1E, stroke-width = 2)
        path(
            stroke = SolidColor(Color(0xFF1E1E1E)),
            strokeLineWidth = 2f,
            strokeLineCap = StrokeCap.Round,
            strokeLineJoin = StrokeJoin.Round
        ) {
            moveTo(30.6689f, 7.45093f)
            lineTo(42.4437f, 12.9416f)
        }
        
        // Path 3 (fill = #1E1E1E)
        path(fill = SolidColor(Color(0xFF1E1E1E))) {
            moveTo(22.4512f, 22f)
            horizontalLineTo(29.5f)
            verticalLineTo(2f)
            horizontalLineTo(16.5f)
            verticalLineTo(4.84668f)
            lineTo(14.7354f, 1.06055f)
            curveTo(15.0718f, 0.429837f, 15.7352f, 0f, 16.5f, 0f)
            horizontalLineTo(29.5f)
            curveTo(30.5357f, 0f, 31.387f, 0.787223f, 31.4893f, 1.7959f)
            lineTo(31.5f, 2f)
            verticalLineTo(22f)
            lineTo(31.4893f, 22.2041f)
            curveTo(31.3938f, 23.1457f, 30.6457f, 23.8938f, 29.7041f, 23.9893f)
            lineTo(29.5f, 24f)
            horizontalLineTo(18.1621f)
            lineTo(22.4512f, 22f)
            close()
        }
        
        // Line 4 (stroke = #1E1E1E, stroke-width = 2)
        path(
            stroke = SolidColor(Color(0xFF1E1E1E)),
            strokeLineWidth = 2f,
            strokeLineCap = StrokeCap.Round,
            strokeLineJoin = StrokeJoin.Round
        ) {
            moveTo(16f, 6.5f)
            lineTo(30f, 6.5f)
        }
        
        // Path 5 (fill = #1E1E1E)
        path(fill = SolidColor(Color(0xFF1E1E1E))) {
            moveTo(14.4172f, 3.15858f)
            lineTo(16.2265f, 2.31049f)
            lineTo(24.7025f, 20.4165f)
            lineTo(22.8932f, 21.2646f)
            lineTo(14.4172f, 3.15858f)
            close()
            moveTo(2.65688f, 8.67112f)
            lineTo(11.1328f, 26.7771f)
            lineTo(24.7025f, 20.4165f)
            lineTo(24.7793f, 20.6058f)
            curveTo(25.0919f, 21.4987f, 24.7322f, 22.4932f, 23.9209f, 22.9789f)
            lineTo(23.7408f, 23.0752f)
            lineTo(11.9804f, 28.5877f)
            curveTo(10.9812f, 29.0561f, 9.79166f, 28.6252f, 9.32355f, 27.6252f)
            lineTo(0.84759f, 9.51921f)
            curveTo(0.379476f, 8.51924f, 0.810039f, 7.32891f, 1.80928f, 6.86052f)
            lineTo(13.5696f, 1.34798f)
            curveTo(14.5065f, 0.908817f, 15.6103f, 1.26048f, 16.1303f, 2.13027f)
            lineTo(16.2265f, 2.31049f)
            lineTo(2.65688f, 8.67112f)
            close()
        }
        
        // Line 6 (stroke = #1E1E1E, stroke-width = 2)
        path(
            stroke = SolidColor(Color(0xFF1E1E1E)),
            strokeLineWidth = 2f,
            strokeLineCap = StrokeCap.Round,
            strokeLineJoin = StrokeJoin.Round
        ) {
            moveTo(4.10643f, 12.9416f)
            lineTo(16.7947f, 7.02494f)
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
