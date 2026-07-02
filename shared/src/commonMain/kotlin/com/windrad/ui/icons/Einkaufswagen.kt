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
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

val Einkaufswagen: ImageVector
    get() {
        val current = _einkaufswagen
        if (current != null) return current

        return ImageVector.Builder(
            name = "com.windrad.ui.theme.WindradTheme.Einkaufswagen",
            defaultWidth = 50.7202.dp,
            defaultHeight = 42.0541.dp,
            viewportWidth = 50.7202f,
            viewportHeight = 42.0541f,
        ).apply {
            path(
                stroke = SolidColor(Color.Black),
                strokeAlpha = 0.99f,
                strokeLineCap = StrokeCap.Round,
                strokeLineWidth = 2.0f,
            ) {
                moveTo(x = 1.0f, y = 1.0f)
                horizontalLineTo(x = 10.0979f)
                curveTo(
                    x1 = 10.5811f,
                    y1 = 1.0f,
                    x2 = 10.9953f,
                    y2 = 1.34562f,
                    x3 = 11.0817f,
                    y3 = 1.82111f
                )
                lineTo(x = 11.8956f, y = 6.2973f)
                moveTo(x = 46.027f, y = 30.1351f)
                horizontalLineTo(x = 17.0643f)
                curveTo(
                    x1 = 16.581f,
                    y1 = 30.1351f,
                    x2 = 16.1669f,
                    y2 = 29.7895f,
                    x3 = 16.0804f,
                    y3 = 29.314f
                )
                lineTo(x = 14.785f, y = 22.1892f)
                moveTo(x = 11.8956f, y = 6.2973f)
                horizontalLineTo(x = 48.7192f)
                curveTo(
                    x1 = 49.3698f,
                    y1 = 6.2973f,
                    x2 = 49.8472f,
                    y2 = 6.90869f,
                    x3 = 49.6894f,
                    y3 = 7.53983f
                )
                lineTo(x = 46.2164f, y = 21.4317f)
                curveTo(
                    x1 = 46.1051f,
                    y1 = 21.8769f,
                    x2 = 45.7051f,
                    y2 = 22.1892f,
                    x3 = 45.2463f,
                    y3 = 22.1892f
                )
                horizontalLineTo(x = 14.785f)
                moveTo(x = 11.8956f, y = 6.2973f)
                lineTo(x = 14.785f, y = 22.1892f)
            }
            path(
                fill = SolidColor(Color.Black),
            ) {
                moveTo(x = 20.2028f, y = 38.7432f)
                moveToRelative(dx = -3.31081f, dy = 0.0f)
                arcToRelative(
                    a = 3.31081f,
                    b = 3.31081f,
                    theta = 0.0f,
                    isMoreThanHalf = true,
                    isPositiveArc = true,
                    dx1 = 6.62162f,
                    dy1 = 0.0f
                )
                arcToRelative(
                    a = 3.31081f,
                    b = 3.31081f,
                    theta = 0.0f,
                    isMoreThanHalf = true,
                    isPositiveArc = true,
                    dx1 = -6.62162f,
                    dy1 = 0.0f
                )
                close()
            }
            path(
                fill = SolidColor(Color.Black),
            ) {
                moveTo(x = 41.3919f, y = 38.7432f)
                moveToRelative(dx = -3.31081f, dy = 0.0f)
                arcToRelative(
                    a = 3.31081f,
                    b = 3.31081f,
                    theta = 0.0f,
                    isMoreThanHalf = true,
                    isPositiveArc = true,
                    dx1 = 6.62162f,
                    dy1 = 0.0f
                )
                arcToRelative(
                    a = 3.31081f,
                    b = 3.31081f,
                    theta = 0.0f,
                    isMoreThanHalf = true,
                    isPositiveArc = true,
                    dx1 = -6.62162f,
                    dy1 = 0.0f
                )
                close()
            }
        }.build().also { _einkaufswagen = it }
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
                imageVector = Einkaufswagen,
                contentDescription = null,
                modifier = Modifier
                    .width((50.7202).dp)
                    .height((42.0541).dp),
            )
        }
    }
}

@Suppress("ObjectPropertyName")
private var _einkaufswagen: ImageVector? = null
