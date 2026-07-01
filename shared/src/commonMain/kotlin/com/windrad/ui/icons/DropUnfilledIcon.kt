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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

val DropUnfilledIcon: ImageVector
    get() {
        val current = _dropUnfilledIcon
        if (current != null) return current

        return ImageVector.Builder(
            name = "com.windrad.ui.theme.WindradTheme.DropUnfilledIcon",
            defaultWidth = 27.0.dp,
            defaultHeight = 23.0.dp,
            viewportWidth = 27.0f,
            viewportHeight = 23.0f,
        ).apply {
            path(
                fill = SolidColor(Color(0xFF0088FF)),
            ) {
                moveTo(x = 13.5f, y = 22.1152f)
                curveTo(
                    x1 = 12.0104f,
                    y1 = 22.1152f,
                    x2 = 10.707f,
                    y2 = 21.8109f,
                    x3 = 9.58984f,
                    y3 = 21.2021f
                )
                curveTo(
                    x1 = 8.47266f,
                    y1 = 20.6006f,
                    x2 = 7.60254f,
                    y2 = 19.7555f,
                    x3 = 6.97949f,
                    y3 = 18.667f
                )
                curveTo(
                    x1 = 6.36361f,
                    y1 = 17.5713f,
                    x2 = 6.05566f,
                    y2 = 16.2965f,
                    x3 = 6.05566f,
                    y3 = 14.8428f
                )
                curveTo(
                    x1 = 6.05566f,
                    y1 = 14.1266f,
                    x2 = 6.13444f,
                    y2 = 13.4212f,
                    x3 = 6.29199f,
                    y3 = 12.7266f
                )
                curveTo(
                    x1 = 6.44954f,
                    y1 = 12.0319f,
                    x2 = 6.6429f,
                    y2 = 11.3766f,
                    x3 = 6.87207f,
                    y3 = 10.7607f
                )
                curveTo(
                    x1 = 7.10124f,
                    y1 = 10.1449f,
                    x2 = 7.31966f,
                    y2 = 9.60059f,
                    x3 = 7.52734f,
                    y3 = 9.12793f
                )
                curveTo(
                    x1 = 8.10026f,
                    y1 = 7.83171f,
                    x2 = 8.80208f,
                    y2 = 6.49967f,
                    x3 = 9.63281f,
                    y3 = 5.13184f
                )
                curveTo(
                    x1 = 10.4635f,
                    y1 = 3.764f,
                    x2 = 11.2692f,
                    y2 = 2.47493f,
                    x3 = 12.0498f,
                    y3 = 1.26465f
                )
                curveTo(
                    x1 = 12.2432f,
                    y1 = 0.97819f,
                    x2 = 12.4544f,
                    y2 = 0.756185f,
                    x3 = 12.6836f,
                    y3 = 0.598633f
                )
                curveTo(
                    x1 = 12.9128f,
                    y1 = 0.441081f,
                    x2 = 13.1849f,
                    y2 = 0.362305f,
                    x3 = 13.5f,
                    y3 = 0.362305f
                )
                curveTo(
                    x1 = 13.8151f,
                    y1 = 0.362305f,
                    x2 = 14.0872f,
                    y2 = 0.441081f,
                    x3 = 14.3164f,
                    y3 = 0.598633f
                )
                curveTo(
                    x1 = 14.5527f,
                    y1 = 0.756185f,
                    x2 = 14.764f,
                    y2 = 0.97819f,
                    x3 = 14.9502f,
                    y3 = 1.26465f
                )
                curveTo(
                    x1 = 15.738f,
                    y1 = 2.47493f,
                    x2 = 16.5436f,
                    y2 = 3.764f,
                    x3 = 17.3672f,
                    y3 = 5.13184f
                )
                curveTo(
                    x1 = 18.1979f,
                    y1 = 6.49967f,
                    x2 = 18.8997f,
                    y2 = 7.83171f,
                    x3 = 19.4727f,
                    y3 = 9.12793f
                )
                curveTo(
                    x1 = 19.6803f,
                    y1 = 9.60059f,
                    x2 = 19.8988f,
                    y2 = 10.1449f,
                    x3 = 20.1279f,
                    y3 = 10.7607f
                )
                curveTo(
                    x1 = 20.3571f,
                    y1 = 11.3766f,
                    x2 = 20.5505f,
                    y2 = 12.0319f,
                    x3 = 20.708f,
                    y3 = 12.7266f
                )
                curveTo(
                    x1 = 20.8656f,
                    y1 = 13.4212f,
                    x2 = 20.9443f,
                    y2 = 14.1266f,
                    x3 = 20.9443f,
                    y3 = 14.8428f
                )
                curveTo(
                    x1 = 20.9443f,
                    y1 = 16.2965f,
                    x2 = 20.6328f,
                    y2 = 17.5713f,
                    x3 = 20.0098f,
                    y3 = 18.667f
                )
                curveTo(
                    x1 = 19.3939f,
                    y1 = 19.7555f,
                    x2 = 18.5273f,
                    y2 = 20.6006f,
                    x3 = 17.4102f,
                    y3 = 21.2021f
                )
                curveTo(
                    x1 = 16.293f,
                    y1 = 21.8109f,
                    x2 = 14.9896f,
                    y2 = 22.1152f,
                    x3 = 13.5f,
                    y3 = 22.1152f
                )
                close()
                moveTo(x = 13.5f, y = 20.3535f)
                curveTo(
                    x1 = 14.6387f,
                    y1 = 20.3535f,
                    x2 = 15.6341f,
                    y2 = 20.1243f,
                    x3 = 16.4863f,
                    y3 = 19.666f
                )
                curveTo(
                    x1 = 17.3385f,
                    y1 = 19.2077f,
                    x2 = 18.001f,
                    y2 = 18.5667f,
                    x3 = 18.4736f,
                    y3 = 17.7432f
                )
                curveTo(
                    x1 = 18.9535f,
                    y1 = 16.9124f,
                    x2 = 19.1934f,
                    y2 = 15.9456f,
                    x3 = 19.1934f,
                    y3 = 14.8428f
                )
                curveTo(
                    x1 = 19.1934f,
                    y1 = 13.9619f,
                    x2 = 19.0501f,
                    y2 = 13.1061f,
                    x3 = 18.7637f,
                    y3 = 12.2754f
                )
                curveTo(
                    x1 = 18.4844f,
                    y1 = 11.4375f,
                    x2 = 18.1943f,
                    y2 = 10.6676f,
                    x3 = 17.8936f,
                    y3 = 9.96582f
                )
                curveTo(
                    x1 = 17.3063f,
                    y1 = 8.55501f,
                    x2 = 16.6331f,
                    y2 = 7.25163f,
                    x3 = 15.874f,
                    y3 = 6.05566f
                )
                curveTo(
                    x1 = 15.1149f,
                    y1 = 4.8597f,
                    x2 = 14.363f,
                    y2 = 3.69596f,
                    x3 = 13.6182f,
                    y3 = 2.56445f
                )
                curveTo(
                    x1 = 13.5895f,
                    y1 = 2.50716f,
                    x2 = 13.5537f,
                    y2 = 2.47852f,
                    x3 = 13.5107f,
                    y3 = 2.47852f
                )
                curveTo(
                    x1 = 13.4678f,
                    y1 = 2.47852f,
                    x2 = 13.4284f,
                    y2 = 2.50716f,
                    x3 = 13.3926f,
                    y3 = 2.56445f
                )
                curveTo(
                    x1 = 12.6406f,
                    y1 = 3.69596f,
                    x2 = 11.8851f,
                    y2 = 4.86328f,
                    x3 = 11.126f,
                    y3 = 6.06641f
                )
                curveTo(
                    x1 = 10.374f,
                    y1 = 7.26237f,
                    x2 = 9.69727f,
                    y2 = 8.56576f,
                    x3 = 9.0957f,
                    y3 = 9.97656f
                )
                curveTo(
                    x1 = 8.79492f,
                    y1 = 10.6712f,
                    x2 = 8.50488f,
                    y2 = 11.4375f,
                    x3 = 8.22559f,
                    y3 = 12.2754f
                )
                curveTo(
                    x1 = 7.95345f,
                    y1 = 13.1061f,
                    x2 = 7.81738f,
                    y2 = 13.9619f,
                    x3 = 7.81738f,
                    y3 = 14.8428f
                )
                curveTo(
                    x1 = 7.81738f,
                    y1 = 15.9456f,
                    x2 = 8.05371f,
                    y2 = 16.9124f,
                    x3 = 8.52637f,
                    y3 = 17.7432f
                )
                curveTo(
                    x1 = 8.99902f,
                    y1 = 18.5667f,
                    x2 = 9.66146f,
                    y2 = 19.2077f,
                    x3 = 10.5137f,
                    y3 = 19.666f
                )
                curveTo(
                    x1 = 11.3659f,
                    y1 = 20.1243f,
                    x2 = 12.3613f,
                    y2 = 20.3535f,
                    x3 = 13.5f,
                    y3 = 20.3535f
                )
                close()
            }
        }.build().also { _dropUnfilledIcon = it }
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
                imageVector = DropUnfilledIcon,
                contentDescription = null,
                modifier = Modifier
                    .width((27.0).dp)
                    .height((23.0).dp),
            )
        }
    }
}

@Suppress("ObjectPropertyName")
private var _dropUnfilledIcon: ImageVector? = null
