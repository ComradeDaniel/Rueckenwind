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

val Windradboden: ImageVector
    get() {
        val current = _windradboden
        if (current != null) return current

        return ImageVector.Builder(
            name = "com.windrad.ui.theme.WindradTheme.Windradboden",
            defaultWidth = 24.0.dp,
            defaultHeight = 6.50215.dp,
            viewportWidth = 24.0f,
            viewportHeight = 6.50215f,
        ).apply {
            path(
                fill = SolidColor(Color(0xFF34C759)),
            ) {
                moveTo(x = 24.0f, y = 4.08028f)
                curveTo(
                    x1 = 24.0f,
                    y1 = 8.10233f,
                    x2 = 18.6274f,
                    y2 = 5.90092f,
                    x3 = 12.0f,
                    y3 = 5.90092f
                )
                curveTo(
                    x1 = 5.37258f,
                    y1 = 5.90092f,
                    x2 = 0.0f,
                    y2 = 7.49545f,
                    x3 = 0.0f,
                    y3 = 3.4734f
                )
                curveTo(
                    x1 = 0.0f,
                    y1 = 1.98443f,
                    x2 = 0.736305f,
                    y2 = 2.85063f,
                    x3 = 2.0f,
                    y3 = 2.01147f
                )
                curveTo(
                    x1 = 2.80431f,
                    y1 = 1.47737f,
                    x2 = 3.82227f,
                    y2 = 2.32084f,
                    x3 = 5.0f,
                    y3 = 2.01156f
                )
                curveTo(
                    x1 = 6.97024f,
                    y1 = 1.49416f,
                    x2 = 9.38763f,
                    y2 = 0.0113764f,
                    x3 = 12.0f,
                    y3 = 0.0113764f
                )
                curveTo(
                    x1 = 14.6124f,
                    y1 = 0.0113764f,
                    x2 = 17.0298f,
                    y2 = -0.120183f,
                    x3 = 19.0f,
                    y3 = 0.511468f
                )
                curveTo(
                    x1 = 20.2311f,
                    y1 = 0.90615f,
                    x2 = 21.6796f,
                    y2 = -0.127052f,
                    x3 = 22.5f,
                    y3 = 0.511468f
                )
                curveTo(
                    x1 = 23.6976f,
                    y1 = 1.44353f,
                    x2 = 24.0f,
                    y2 = 2.63424f,
                    x3 = 24.0f,
                    y3 = 4.08028f
                )
                close()
            }
        }.build().also { _windradboden = it }
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
                imageVector = Windradboden,
                contentDescription = null,
                modifier = Modifier
                    .width((24.0).dp)
                    .height((6.50215).dp),
            )
        }
    }
}

@Suppress("ObjectPropertyName")
private var _windradboden: ImageVector? = null
