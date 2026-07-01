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
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

val CollectCardsBase: ImageVector
    get() {
        val current = _collectCardsBase
        if (current != null) return current

        return ImageVector.Builder(
            name = "com.windrad.ui.theme.WindradTheme.CollectCardsBase",
            defaultWidth = 36.4608.dp,
            defaultHeight = 22.9995.dp,
            viewportWidth = 36.4608f,
            viewportHeight = 22.9995f,
        ).apply {
            path(
                fill = SolidColor(Color(0xFF1E1E1E)),
                pathFillType = PathFillType.EvenOdd,
            ) {
                moveTo(x = 0.566858f, y = 6.76878f)
                curveTo(
                    x1 = 0.0652033f,
                    y1 = 7.01002f,
                    x2 = -0.142905f,
                    y2 = 7.61446f,
                    x3 = 0.103909f,
                    y3 = 8.11339f
                )
                lineTo(x = 7.1923f, y = 22.4426f)
                curveTo(
                    x1 = 7.43525f,
                    y1 = 22.9337f,
                    x2 = 8.0282f,
                    y2 = 23.1379f,
                    x3 = 8.522f,
                    y3 = 22.9004f
                )
                lineTo(x = 18.2303f, y = 18.2319f)
                lineTo(x = 18.8428f, y = 17.9373f)
                lineTo(x = 20.1167f, y = 17.3247f)
                curveTo(
                    x1 = 20.199f,
                    y1 = 17.2852f,
                    x2 = 20.2331f,
                    y2 = 17.186f,
                    x3 = 20.1926f,
                    y3 = 17.1042f
                )
                lineTo(x = 19.7981f, y = 16.3067f)
                lineTo(x = 19.1505f, y = 14.9975f)
                lineTo(x = 18.2304f, y = 13.1376f)
                lineTo(x = 14.5548f, y = 5.70733f)
                lineTo(x = 13.7481f, y = 4.07666f)
                lineTo(x = 12.8552f, y = 2.27168f)
                lineTo(x = 12.7294f, y = 2.01729f)
                curveTo(
                    x1 = 12.4864f,
                    y1 = 1.52616f,
                    x2 = 11.8935f,
                    y2 = 1.32201f,
                    x3 = 11.3997f,
                    y3 = 1.55948f
                )
                lineTo(x = 11.2013f, y = 1.65486f)
                lineTo(x = 0.566858f, y = 6.76878f)
                close()
                moveTo(x = 12.617f, y = 5.50982f)
                lineTo(x = 11.5294f, y = 3.31137f)
                lineTo(x = 11.2013f, y = 3.46914f)
                lineTo(x = 1.86708f, y = 7.95781f)
                lineTo(x = 2.95461f, y = 10.1563f)
                lineTo(x = 11.2013f, y = 6.19056f)
                lineTo(x = 12.617f, y = 5.50982f)
                close()
                moveTo(x = 8.39228f, y = 21.1485f)
                lineTo(x = 3.67963f, y = 11.6219f)
                lineTo(x = 11.2013f, y = 8.00485f)
                lineTo(x = 12.8552f, y = 7.20952f)
                lineTo(x = 13.342f, y = 6.97545f)
                lineTo(x = 17.3104f, y = 14.9975f)
                lineTo(x = 17.958f, y = 16.3067f)
                lineTo(x = 18.0546f, y = 16.5021f)
                lineTo(x = 15.07f, y = 17.9373f)
                lineTo(x = 8.39228f, y = 21.1485f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFF1E1E1E)),
            ) {
                moveTo(x = 12.8552f, y = 1.63067f)
                horizontalLineTo(x = 23.6056f)
                verticalLineTo(y = 2.27174f)
                verticalLineTo(y = 4.07666f)
                horizontalLineTo(x = 22.7127f)
                horizontalLineTo(x = 13.7481f)
                lineTo(x = 14.5548f, y = 5.70733f)
                horizontalLineTo(x = 23.6056f)
                verticalLineTo(y = 7.20966f)
                verticalLineTo(y = 16.3067f)
                horizontalLineTo(x = 19.7981f)
                lineTo(x = 20.1926f, y = 17.1042f)
                curveTo(
                    x1 = 20.2331f,
                    y1 = 17.186f,
                    x2 = 20.199f,
                    y2 = 17.2852f,
                    x3 = 20.1167f,
                    y3 = 17.3247f
                )
                lineTo(x = 18.8428f, y = 17.9373f)
                horizontalLineTo(x = 21.3905f)
                horizontalLineTo(x = 24.2595f)
                curveTo(
                    x1 = 24.8118f,
                    y1 = 17.9373f,
                    x2 = 25.2595f,
                    y2 = 17.4896f,
                    x3 = 25.2595f,
                    y3 = 16.9373f
                )
                verticalLineTo(y = 8.00499f)
                verticalLineTo(y = 6.19071f)
                verticalLineTo(y = 3.46929f)
                verticalLineTo(y = 1.65501f)
                verticalLineTo(y = 1.0f)
                curveTo(
                    x1 = 25.2595f,
                    y1 = 0.447715f,
                    x2 = 24.8118f,
                    y2 = 0.0f,
                    x3 = 24.2595f,
                    y3 = 0.0f
                )
                horizontalLineTo(x = 12.2013f)
                curveTo(
                    x1 = 11.6491f,
                    y1 = 0.0f,
                    x2 = 11.2013f,
                    y2 = 0.447715f,
                    x3 = 11.2013f,
                    y3 = 1.0f
                )
                verticalLineTo(y = 1.65486f)
                lineTo(x = 11.3997f, y = 1.55948f)
                curveTo(
                    x1 = 11.8935f,
                    y1 = 1.32201f,
                    x2 = 12.4864f,
                    y2 = 1.52616f,
                    x3 = 12.7294f,
                    y3 = 2.01729f
                )
                lineTo(x = 12.8552f, y = 2.27168f)
                verticalLineTo(y = 1.63067f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFF1E1E1E)),
            ) {
                moveTo(x = 34.5937f, y = 7.95794f)
                lineTo(x = 33.5062f, y = 10.1564f)
                lineTo(x = 25.2595f, y = 6.19071f)
                verticalLineTo(y = 8.00499f)
                lineTo(x = 32.7812f, y = 11.622f)
                lineTo(x = 28.0685f, y = 21.1486f)
                lineTo(x = 21.3905f, y = 17.9373f)
                horizontalLineTo(x = 18.8428f)
                lineTo(x = 18.2303f, y = 18.2319f)
                lineTo(x = 27.9388f, y = 22.9005f)
                curveTo(
                    x1 = 28.4326f,
                    y1 = 23.138f,
                    x2 = 29.0255f,
                    y2 = 22.9338f,
                    x3 = 29.2685f,
                    y3 = 22.4427f
                )
                lineTo(x = 36.3569f, y = 8.11352f)
                curveTo(
                    x1 = 36.6037f,
                    y1 = 7.61459f,
                    x2 = 36.3956f,
                    y2 = 7.01015f,
                    x3 = 35.8939f,
                    y3 = 6.76891f
                )
                lineTo(x = 25.2595f, y = 1.65501f)
                verticalLineTo(y = 3.46929f)
                lineTo(x = 34.5937f, y = 7.95794f)
                close()
            }
        }.build().also { _collectCardsBase = it }
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
                imageVector = CollectCardsBase,
                contentDescription = null,
                modifier = Modifier
                    .width((36.4608).dp)
                    .height((22.9995).dp),
            )
        }
    }
}

@Suppress("ObjectPropertyName")
private var _collectCardsBase: ImageVector? = null
