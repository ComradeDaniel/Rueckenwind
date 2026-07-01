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

val PowerIcon: ImageVector
    get() {
        val current = _powerIcon
        if (current != null) return current

        return ImageVector.Builder(
            name = "com.windrad.ui.theme.WindradTheme.PowerIcon",
            defaultWidth = 20.0.dp,
            defaultHeight = 20.0.dp,
            viewportWidth = 20.0f,
            viewportHeight = 20.0f,
        ).apply {
            path(
                fill = SolidColor(Color(0xFF0088FF)),
            ) {
                moveTo(x = 10.0f, y = 18.4736f)
                curveTo(
                    x1 = 8.83236f,
                    y1 = 18.4736f,
                    x2 = 7.73665f,
                    y2 = 18.2523f,
                    x3 = 6.71289f,
                    y3 = 17.8096f
                )
                curveTo(
                    x1 = 5.68913f,
                    y1 = 17.3724f,
                    x2 = 4.78988f,
                    y2 = 16.7664f,
                    x3 = 4.01514f,
                    y3 = 15.9917f
                )
                curveTo(
                    x1 = 3.2404f,
                    y1 = 15.217f,
                    x2 = 2.63167f,
                    y2 = 14.3177f,
                    x3 = 2.18896f,
                    y3 = 13.2939f
                )
                curveTo(
                    x1 = 1.75179f,
                    y1 = 12.2702f,
                    x2 = 1.5332f,
                    y2 = 11.1745f,
                    x3 = 1.5332f,
                    y3 = 10.0068f
                )
                curveTo(
                    x1 = 1.5332f,
                    y1 = 9.16016f,
                    x2 = 1.64941f,
                    y2 = 8.35221f,
                    x3 = 1.88184f,
                    y3 = 7.58301f
                )
                curveTo(
                    x1 = 2.11979f,
                    y1 = 6.8138f,
                    x2 = 2.44629f,
                    y2 = 6.10824f,
                    x3 = 2.86133f,
                    y3 = 5.46631f
                )
                curveTo(
                    x1 = 3.27637f,
                    y1 = 4.82438f,
                    x2 = 3.75228f,
                    y2 = 4.26546f,
                    x3 = 4.28906f,
                    y3 = 3.78955f
                )
                curveTo(
                    x1 = 4.43848f,
                    y1 = 3.65674f,
                    x2 = 4.58789f,
                    y2 = 3.5848f,
                    x3 = 4.7373f,
                    y3 = 3.57373f
                )
                curveTo(
                    x1 = 4.89225f,
                    y1 = 3.56266f,
                    x2 = 5.0306f,
                    y2 = 3.5931f,
                    x3 = 5.15234f,
                    y3 = 3.66504f
                )
                curveTo(
                    x1 = 5.27409f,
                    y1 = 3.73698f,
                    x2 = 5.3654f,
                    y2 = 3.83659f,
                    x3 = 5.42627f,
                    y3 = 3.96387f
                )
                curveTo(
                    x1 = 5.49268f,
                    y1 = 4.08561f,
                    x2 = 5.51204f,
                    y2 = 4.22119f,
                    x3 = 5.48438f,
                    y3 = 4.37061f
                )
                curveTo(
                    x1 = 5.46224f,
                    y1 = 4.51449f,
                    x2 = 5.37646f,
                    y2 = 4.6556f,
                    x3 = 5.22705f,
                    y3 = 4.79395f
                )
                curveTo(
                    x1 = 4.52979f,
                    y1 = 5.44141f,
                    x2 = 3.97363f,
                    y2 = 6.21338f,
                    x3 = 3.55859f,
                    y3 = 7.10986f
                )
                curveTo(
                    x1 = 3.14909f,
                    y1 = 8.00081f,
                    x2 = 2.94434f,
                    y2 = 8.96647f,
                    x3 = 2.94434f,
                    y3 = 10.0068f
                )
                curveTo(
                    x1 = 2.94434f,
                    y1 = 10.9808f,
                    x2 = 3.12695f,
                    y2 = 11.8939f,
                    x3 = 3.49219f,
                    y3 = 12.7461f
                )
                curveTo(
                    x1 = 3.85742f,
                    y1 = 13.5983f,
                    x2 = 4.36377f,
                    y2 = 14.3481f,
                    x3 = 5.01123f,
                    y3 = 14.9956f
                )
                curveTo(
                    x1 = 5.65869f,
                    y1 = 15.6431f,
                    x2 = 6.40853f,
                    y2 = 16.1494f,
                    x3 = 7.26074f,
                    y3 = 16.5146f
                )
                curveTo(
                    x1 = 8.11296f,
                    y1 = 16.8799f,
                    x2 = 9.02604f,
                    y2 = 17.0625f,
                    x3 = 10.0f,
                    y3 = 17.0625f
                )
                curveTo(
                    x1 = 10.974f,
                    y1 = 17.0625f,
                    x2 = 11.887f,
                    y2 = 16.8799f,
                    x3 = 12.7393f,
                    y3 = 16.5146f
                )
                curveTo(
                    x1 = 13.5915f,
                    y1 = 16.1494f,
                    x2 = 14.3413f,
                    y2 = 15.6431f,
                    x3 = 14.9888f,
                    y3 = 14.9956f
                )
                curveTo(
                    x1 = 15.6362f,
                    y1 = 14.3481f,
                    x2 = 16.1426f,
                    y2 = 13.5983f,
                    x3 = 16.5078f,
                    y3 = 12.7461f
                )
                curveTo(
                    x1 = 16.873f,
                    y1 = 11.8939f,
                    x2 = 17.0557f,
                    y2 = 10.9808f,
                    x3 = 17.0557f,
                    y3 = 10.0068f
                )
                curveTo(
                    x1 = 17.0557f,
                    y1 = 8.97201f,
                    x2 = 16.8509f,
                    y2 = 8.00911f,
                    x3 = 16.4414f,
                    y3 = 7.11816f
                )
                curveTo(
                    x1 = 16.0319f,
                    y1 = 6.22168f,
                    x2 = 15.4757f,
                    y2 = 5.44971f,
                    x3 = 14.7729f,
                    y3 = 4.80225f
                )
                curveTo(
                    x1 = 14.6235f,
                    y1 = 4.66943f,
                    x2 = 14.5378f,
                    y2 = 4.52832f,
                    x3 = 14.5156f,
                    y3 = 4.37891f
                )
                curveTo(
                    x1 = 14.4935f,
                    y1 = 4.22949f,
                    x2 = 14.5129f,
                    y2 = 4.09115f,
                    x3 = 14.5737f,
                    y3 = 3.96387f
                )
                curveTo(
                    x1 = 14.6401f,
                    y1 = 3.83105f,
                    x2 = 14.7342f,
                    y2 = 3.72868f,
                    x3 = 14.856f,
                    y3 = 3.65674f
                )
                curveTo(
                    x1 = 14.9832f,
                    y1 = 3.57926f,
                    x2 = 15.1216f,
                    y2 = 3.54883f,
                    x3 = 15.271f,
                    y3 = 3.56543f
                )
                curveTo(
                    x1 = 15.4204f,
                    y1 = 3.5765f,
                    x2 = 15.5698f,
                    y2 = 3.64844f,
                    x3 = 15.7192f,
                    y3 = 3.78125f
                )
                curveTo(
                    x1 = 16.2505f,
                    y1 = 4.26823f,
                    x2 = 16.7236f,
                    y2 = 4.82992f,
                    x3 = 17.1387f,
                    y3 = 5.46631f
                )
                curveTo(
                    x1 = 17.5537f,
                    y1 = 6.09717f,
                    x2 = 17.8774f,
                    y2 = 6.79443f,
                    x3 = 18.1099f,
                    y3 = 7.55811f
                )
                curveTo(
                    x1 = 18.3478f,
                    y1 = 8.31624f,
                    x2 = 18.4668f,
                    y2 = 9.13249f,
                    x3 = 18.4668f,
                    y3 = 10.0068f
                )
                curveTo(
                    x1 = 18.4668f,
                    y1 = 11.1745f,
                    x2 = 18.2454f,
                    y2 = 12.2702f,
                    x3 = 17.8027f,
                    y3 = 13.2939f
                )
                curveTo(
                    x1 = 17.3656f,
                    y1 = 14.3177f,
                    x2 = 16.7596f,
                    y2 = 15.217f,
                    x3 = 15.9849f,
                    y3 = 15.9917f
                )
                curveTo(
                    x1 = 15.2101f,
                    y1 = 16.7664f,
                    x2 = 14.3109f,
                    y2 = 17.3724f,
                    x3 = 13.2871f,
                    y3 = 17.8096f
                )
                curveTo(
                    x1 = 12.2633f,
                    y1 = 18.2523f,
                    x2 = 11.1676f,
                    y2 = 18.4736f,
                    x3 = 10.0f,
                    y3 = 18.4736f
                )
                close()
                moveTo(x = 10.0f, y = 9.92383f)
                curveTo(
                    x1 = 9.80078f,
                    y1 = 9.92383f,
                    x2 = 9.6403f,
                    y2 = 9.86019f,
                    x3 = 9.51855f,
                    y3 = 9.73291f
                )
                curveTo(
                    x1 = 9.39681f,
                    y1 = 9.6001f,
                    x2 = 9.33594f,
                    y2 = 9.42855f,
                    x3 = 9.33594f,
                    y3 = 9.21826f
                )
                verticalLineTo(y = 1.76416f)
                curveTo(
                    x1 = 9.33594f,
                    y1 = 1.55387f,
                    x2 = 9.39681f,
                    y2 = 1.38509f,
                    x3 = 9.51855f,
                    y3 = 1.25781f
                )
                curveTo(
                    x1 = 9.6403f,
                    y1 = 1.125f,
                    x2 = 9.80078f,
                    y2 = 1.05859f,
                    x3 = 10.0f,
                    y3 = 1.05859f
                )
                curveTo(
                    x1 = 10.1992f,
                    y1 = 1.05859f,
                    x2 = 10.3597f,
                    y2 = 1.125f,
                    x3 = 10.4814f,
                    y3 = 1.25781f
                )
                curveTo(
                    x1 = 10.6087f,
                    y1 = 1.38509f,
                    x2 = 10.6724f,
                    y2 = 1.55387f,
                    x3 = 10.6724f,
                    y3 = 1.76416f
                )
                verticalLineTo(y = 9.21826f)
                curveTo(
                    x1 = 10.6724f,
                    y1 = 9.42855f,
                    x2 = 10.6087f,
                    y2 = 9.6001f,
                    x3 = 10.4814f,
                    y3 = 9.73291f
                )
                curveTo(
                    x1 = 10.3597f,
                    y1 = 9.86019f,
                    x2 = 10.1992f,
                    y2 = 9.92383f,
                    x3 = 10.0f,
                    y3 = 9.92383f
                )
                close()
            }
        }.build().also { _powerIcon = it }
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
                imageVector = PowerIcon,
                contentDescription = null,
                modifier = Modifier
                    .width((20.0).dp)
                    .height((20.0).dp),
            )
        }
    }
}

@Suppress("ObjectPropertyName")
private var _powerIcon: ImageVector? = null
