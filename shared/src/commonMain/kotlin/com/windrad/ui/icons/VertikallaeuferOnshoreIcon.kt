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

val VertikallaeuferOnshoreIcon: ImageVector
    get() {
        val current = _vertikallaeuferOnshoreIcon
        if (current != null) return current

        return ImageVector.Builder(
            name = "com.windrad.ui.theme.WindradTheme.VertikallaeuferOnshoreIcon",
            defaultWidth = 24.0.dp,
            defaultHeight = 8.49068.dp,
            viewportWidth = 24.0f,
            viewportHeight = 8.49068f,
        ).apply {
            path(
                fill = SolidColor(Color(0xFF34C759)),
            ) {
                moveTo(x = 24.0f, y = 6.06881f)
                curveTo(
                    x1 = 24.0f,
                    y1 = 10.0909f,
                    x2 = 18.6274f,
                    y2 = 7.88946f,
                    x3 = 12.0f,
                    y3 = 7.88946f
                )
                curveTo(
                    x1 = 5.37258f,
                    y1 = 7.88946f,
                    x2 = 0.0f,
                    y2 = 9.48399f,
                    x3 = 0.0f,
                    y3 = 5.46193f
                )
                curveTo(x1 = 0.0f, y1 = 1.43988f, x2 = 5.37258f, y2 = 0.0f, x3 = 12.0f, y3 = 0.0f)
                curveTo(
                    x1 = 18.6274f,
                    y1 = 0.0f,
                    x2 = 24.0f,
                    y2 = 2.04676f,
                    x3 = 24.0f,
                    y3 = 6.06881f
                )
                close()
            }
        }.build().also { _vertikallaeuferOnshoreIcon = it }
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
                imageVector = VertikallaeuferOnshoreIcon,
                contentDescription = null,
                modifier = Modifier
                    .width((24.0).dp)
                    .height((8.49068).dp),
            )
        }
    }
}

@Suppress("ObjectPropertyName")
private var _vertikallaeuferOnshoreIcon: ImageVector? = null
