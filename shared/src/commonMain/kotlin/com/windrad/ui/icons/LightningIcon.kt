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

val LightningIcon: ImageVector
    get() {
        val current = _lightningIcon
        if (current != null) return current

        return ImageVector.Builder(
            name = "com.windrad.ui.theme.WindradTheme.LightningIcon",
            defaultWidth = 22.0.dp,
            defaultHeight = 25.0.dp,
            viewportWidth = 22.0f,
            viewportHeight = 25.0f,
        ).apply {
            path(
                fill = SolidColor(Color(0xFFFFCC00)),
            ) {
                moveTo(x = 3.42676f, y = 13.374f)
                curveTo(
                    x1 = 3.42676f,
                    y1 = 13.1592f,
                    x2 = 3.51986f,
                    y2 = 12.93f,
                    x3 = 3.70605f,
                    y3 = 12.6865f
                )
                lineTo(x = 13.4707f, y = 0.494141f)
                curveTo(
                    x1 = 13.6497f,
                    y1 = 0.264974f,
                    x2 = 13.8431f,
                    y2 = 0.125326f,
                    x3 = 14.0508f,
                    y3 = 0.0751953f
                )
                curveTo(
                    x1 = 14.2656f,
                    y1 = 0.0250651f,
                    x2 = 14.4554f,
                    y2 = 0.0465495f,
                    x3 = 14.6201f,
                    y3 = 0.139648f
                )
                curveTo(
                    x1 = 14.792f,
                    y1 = 0.225586f,
                    x2 = 14.9102f,
                    y2 = 0.368815f,
                    x3 = 14.9746f,
                    y3 = 0.569336f
                )
                curveTo(
                    x1 = 15.0462f,
                    y1 = 0.769857f,
                    x2 = 15.0283f,
                    y2 = 1.00618f,
                    x3 = 14.9209f,
                    y3 = 1.27832f
                )
                lineTo(x = 11.7197f, y = 9.97949f)
                horizontalLineTo(x = 17.7891f)
                curveTo(
                    x1 = 18.0182f,
                    y1 = 9.97949f,
                    x2 = 18.2044f,
                    y2 = 10.0511f,
                    x3 = 18.3477f,
                    y3 = 10.1943f
                )
                curveTo(
                    x1 = 18.4909f,
                    y1 = 10.3304f,
                    x2 = 18.5625f,
                    y2 = 10.5023f,
                    x3 = 18.5625f,
                    y3 = 10.71f
                )
                curveTo(
                    x1 = 18.5625f,
                    y1 = 10.932f,
                    x2 = 18.4694f,
                    y2 = 11.1611f,
                    x3 = 18.2832f,
                    y3 = 11.3975f
                )
                lineTo(x = 8.51855f, y = 23.6006f)
                curveTo(
                    x1 = 8.33952f,
                    y1 = 23.8226f,
                    x2 = 8.14258f,
                    y2 = 23.9587f,
                    x3 = 7.92773f,
                    y3 = 24.0088f
                )
                curveTo(
                    x1 = 7.72005f,
                    y1 = 24.0661f,
                    x2 = 7.53027f,
                    y2 = 24.0482f,
                    x3 = 7.3584f,
                    y3 = 23.9551f
                )
                curveTo(
                    x1 = 7.19368f,
                    y1 = 23.8691f,
                    x2 = 7.07552f,
                    y2 = 23.7259f,
                    x3 = 7.00391f,
                    y3 = 23.5254f
                )
                curveTo(
                    x1 = 6.93945f,
                    y1 = 23.3249f,
                    x2 = 6.96094f,
                    y2 = 23.085f,
                    x3 = 7.06836f,
                    y3 = 22.8057f
                )
                lineTo(x = 10.2695f, y = 14.1045f)
                horizontalLineTo(x = 4.2002f)
                curveTo(
                    x1 = 3.97103f,
                    y1 = 14.1045f,
                    x2 = 3.78483f,
                    y2 = 14.0365f,
                    x3 = 3.6416f,
                    y3 = 13.9004f
                )
                curveTo(
                    x1 = 3.49837f,
                    y1 = 13.7572f,
                    x2 = 3.42676f,
                    y2 = 13.5817f,
                    x3 = 3.42676f,
                    y3 = 13.374f
                )
                close()
            }
        }.build().also { _lightningIcon = it }
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
                imageVector = LightningIcon,
                contentDescription = null,
                modifier = Modifier
                    .width((22.0).dp)
                    .height((25.0).dp),
            )
        }
    }
}

@Suppress("ObjectPropertyName")
private var _lightningIcon: ImageVector? = null
