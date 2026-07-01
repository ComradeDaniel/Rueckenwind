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

val DropFilledIcon: ImageVector
    get() {
        val current = _dropFilledIcon
        if (current != null) return current

        return ImageVector.Builder(
            name = "com.windrad.ui.theme.WindradTheme.DropFilledIcon",
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
                    x1 = 7.91406f,
                    y1 = 8.26139f,
                    x2 = 8.3724f,
                    y2 = 7.37695f,
                    x3 = 8.90234f,
                    y3 = 6.47461f
                )
                curveTo(
                    x1 = 9.43945f,
                    y1 = 5.5651f,
                    x2 = 10.0052f,
                    y2 = 4.65918f,
                    x3 = 10.5996f,
                    y3 = 3.75684f
                )
                curveTo(
                    x1 = 11.194f,
                    y1 = 2.85449f,
                    x2 = 11.7669f,
                    y2 = 1.97363f,
                    x3 = 12.3184f,
                    y3 = 1.11426f
                )
                curveTo(
                    x1 = 12.4831f,
                    y1 = 0.870768f,
                    x2 = 12.6657f,
                    y2 = 0.68457f,
                    x3 = 12.8662f,
                    y3 = 0.555664f
                )
                curveTo(
                    x1 = 13.0739f,
                    y1 = 0.426758f,
                    x2 = 13.2852f,
                    y2 = 0.362305f,
                    x3 = 13.5f,
                    y3 = 0.362305f
                )
                curveTo(
                    x1 = 13.722f,
                    y1 = 0.362305f,
                    x2 = 13.9333f,
                    y2 = 0.426758f,
                    x3 = 14.1338f,
                    y3 = 0.555664f
                )
                curveTo(
                    x1 = 14.3343f,
                    y1 = 0.68457f,
                    x2 = 14.5169f,
                    y2 = 0.870768f,
                    x3 = 14.6816f,
                    y3 = 1.11426f
                )
                curveTo(
                    x1 = 15.2402f,
                    y1 = 1.97363f,
                    x2 = 15.8132f,
                    y2 = 2.85449f,
                    x3 = 16.4004f,
                    y3 = 3.75684f
                )
                curveTo(
                    x1 = 16.9948f,
                    y1 = 4.65918f,
                    x2 = 17.5605f,
                    y2 = 5.5651f,
                    x3 = 18.0977f,
                    y3 = 6.47461f
                )
                curveTo(
                    x1 = 18.6348f,
                    y1 = 7.37695f,
                    x2 = 19.0931f,
                    y2 = 8.26139f,
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
            }
        }.build().also { _dropFilledIcon = it }
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
                imageVector = DropFilledIcon,
                contentDescription = null,
                modifier = Modifier
                    .width((27.0).dp)
                    .height((23.0).dp),
            )
        }
    }
}

@Suppress("ObjectPropertyName")
private var _dropFilledIcon: ImageVector? = null
