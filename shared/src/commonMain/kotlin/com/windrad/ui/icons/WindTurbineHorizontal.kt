package com.windrad.ui.icons

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview

val WindTurbineHorizontal: ImageVector
    get() {
        val current = _windTurbineHorizontal
        if (current != null) return current

        return ImageVector.Builder(
            name = "com.windrad.ui.theme.WindradTheme.WindTurbineHorizontal",
            defaultWidth = 24.0.dp,
            defaultHeight = 35.0.dp,
            viewportWidth = 24.0f,
            viewportHeight = 35.0f,
        ).apply {
            path(
                stroke = SolidColor(Color(0xFF0088FF)),
                strokeLineWidth = 1.0f,
            ) {
                moveTo(x = 12.0f, y = 13.0f)
                moveToRelative(dx = -11.5f, dy = 0.0f)
                arcToRelative(
                    a = 11.5f,
                    b = 11.5f,
                    theta = 0.0f,
                    isMoreThanHalf = true,
                    isPositiveArc = true,
                    dx1 = 23.0f,
                    dy1 = 0.0f
                )
                arcToRelative(
                    a = 11.5f,
                    b = 11.5f,
                    theta = 0.0f,
                    isMoreThanHalf = true,
                    isPositiveArc = true,
                    dx1 = -23.0f,
                    dy1 = 0.0f
                )
                close()
            }
            path(
                fill = SolidColor(Color(0xFF1E1E1E)),
            ) {
                moveTo(x = 12.0391f, y = 10.9375f)
                arcTo(
                    horizontalEllipseRadius = 2.18896f,
                    verticalEllipseRadius = 2.1875f,
                    theta = 0.0f,
                    isMoreThanHalf = true,
                    isPositiveArc = false,
                    x1 = 12.0391f,
                    y1 = 15.3125f
                )
                arcTo(
                    horizontalEllipseRadius = 2.18896f,
                    verticalEllipseRadius = 2.1875f,
                    theta = 0.0f,
                    isMoreThanHalf = true,
                    isPositiveArc = false,
                    x1 = 12.0391f,
                    y1 = 10.9375f
                )
                close()
            }
            path(
                fill = SolidColor(Color(0xFF1E1E1E)),
            ) {
                moveTo(x = 9.8501f, y = 19.68896f)
                arcToRelative(
                    a = 2.18896f,
                    b = 2.18896f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 2.18896f,
                    dy1 = -2.18896f
                )
                horizontalLineToRelative(dx = -0.37792f)
                arcToRelative(
                    a = 2.18896f,
                    b = 2.18896f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 2.18896f,
                    dy1 = 2.18896f
                )
                verticalLineToRelative(dy = 13.62208f)
                arcToRelative(
                    a = 2.18896f,
                    b = 2.18896f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -2.18896f,
                    dy1 = 2.18896f
                )
                horizontalLineToRelative(dx = 0.37792f)
                arcToRelative(
                    a = 2.18896f,
                    b = 2.18896f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -2.18896f,
                    dy1 = -2.18896f
                )
                close()
            }
            path(
                fill = SolidColor(Color(0xFF1E1E1E)),
            ) {
                moveTo(x = 9.8501f, y = 2.18896f)
                arcToRelative(
                    a = 2.18896f,
                    b = 2.18896f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 2.18896f,
                    dy1 = -2.18896f
                )
                horizontalLineToRelative(dx = -0.37792f)
                arcToRelative(
                    a = 2.18896f,
                    b = 2.18896f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 2.18896f,
                    dy1 = 2.18896f
                )
                verticalLineToRelative(dy = 4.62208f)
                arcToRelative(
                    a = 2.18896f,
                    b = 2.18896f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -2.18896f,
                    dy1 = 2.18896f
                )
                horizontalLineToRelative(dx = 0.37792f)
                arcToRelative(
                    a = 2.18896f,
                    b = 2.18896f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -2.18896f,
                    dy1 = -2.18896f
                )
                close()
            }
            path(
                fill = SolidColor(Color(0xFF1E1E1E)),
            ) {
                moveTo(x = 4.0840273f, y = 20.195412f)
                arcToRelative(
                    a = 2.1872385f,
                    b = 2.1885018f,
                    theta = -75.013954f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -2.9895494f,
                    dy1 = -0.801047f
                )
                lineToRelative(dx = 0.18796393f, dy = 0.3253461f)
                arcToRelative(
                    a = 2.1872385f,
                    b = 2.1885018f,
                    theta = -75.013954f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 0.8005854f,
                    dy1 = -2.987823f
                )
                lineToRelative(dx = 4.0053954f, dy = -2.310974f)
                arcToRelative(
                    a = 2.1872385f,
                    b = 2.1885018f,
                    theta = -75.013954f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 2.9895494f,
                    dy1 = 0.801047f
                )
                lineToRelative(dx = -0.18796393f, dy = -0.3253461f)
                arcToRelative(
                    a = 2.1872385f,
                    b = 2.1885018f,
                    theta = -75.013954f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -0.8005854f,
                    dy1 = 2.987823f
                )
                close()
            }
            path(
                fill = SolidColor(Color(0xFF1E1E1E)),
            ) {
                moveTo(x = 22.104933f, y = 16.406612f)
                arcToRelative(
                    a = 2.1872385f,
                    b = 2.1885018f,
                    theta = 75.013954f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 0.8005854f,
                    dy1 = 2.987823f
                )
                lineToRelative(dx = 0.18796393f, dy = -0.3253461f)
                arcToRelative(
                    a = 2.1872385f,
                    b = 2.1885018f,
                    theta = 75.013954f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -2.9895494f,
                    dy1 = 0.801047f
                )
                lineToRelative(dx = -4.0053954f, dy = -2.310974f)
                arcToRelative(
                    a = 2.1872385f,
                    b = 2.1885018f,
                    theta = 75.013954f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -0.8005854f,
                    dy1 = -2.987823f
                )
                lineToRelative(dx = -0.18796393f, dy = 0.3253461f)
                arcToRelative(
                    a = 2.1872385f,
                    b = 2.1885018f,
                    theta = 75.013954f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 2.9895494f,
                    dy1 = -0.801047f
                )
                close()
            }
        }.build().also { _windTurbineHorizontal = it }
    }

@Preview
@Composable
private fun IconPreview() {
    com.windrad.ui.theme.WindradTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                imageVector = WindTurbineHorizontal,
                contentDescription = null,
                modifier = Modifier
                    .width((24.0).dp)
                    .height((35.0).dp),
            )
        }
    }
}

@Suppress("ObjectPropertyName")
private var _windTurbineHorizontal: ImageVector? = null
