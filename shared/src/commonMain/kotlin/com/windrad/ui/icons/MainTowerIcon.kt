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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview

val MainTowerIcon: ImageVector
    get() {
        val current = _mainTowerIcon
        if (current != null) return current

        return ImageVector.Builder(
            name = "com.windrad.ui.theme.WindradTheme.MainTowerIcon",
            defaultWidth = 31.6748.dp,
            defaultHeight = 41.3998.dp,
            viewportWidth = 31.6748f,
            viewportHeight = 41.3998f,
        ).apply {
            path(
                stroke = SolidColor(Color(0xFF1E1E1E)),
                strokeLineCap = StrokeCap.Round,
            ) {
                moveTo(x = 0.5f, y = 40.8998f)
                horizontalLineTo(x = 31.1748f)
            }
            path(
                stroke = SolidColor(Color(0xFF1E1E1E)),
            ) {
                moveTo(x = 15.5f, y = 40.5f)
                verticalLineTo(y = 11.5f)
                curveTo(
                    x1 = 15.5f,
                    y1 = 10.9477f,
                    x2 = 15.0523f,
                    y2 = 10.5f,
                    x3 = 14.5f,
                    y3 = 10.5f
                )
                curveTo(
                    x1 = 13.9477f,
                    y1 = 10.5f,
                    x2 = 13.5f,
                    y2 = 10.9477f,
                    x3 = 13.5f,
                    y3 = 11.5f
                )
                verticalLineTo(y = 40.5f)
            }
            path(
                stroke = SolidColor(Color(0xFF1E1E1E)),
            ) {
                moveTo(x = 13.5f, y = 40.5001f)
                verticalLineTo(y = 13.5001f)
                curveTo(
                    x1 = 13.5f,
                    y1 = 12.9478f,
                    x2 = 13.0523f,
                    y2 = 12.5001f,
                    x3 = 12.5f,
                    y3 = 12.5001f
                )
                horizontalLineTo(x = 6.49997f)
                curveTo(
                    x1 = 5.94768f,
                    y1 = 12.5001f,
                    x2 = 5.49997f,
                    y2 = 12.9478f,
                    x3 = 5.49997f,
                    y3 = 13.5001f
                )
                verticalLineTo(y = 40.5001f)
            }
            path(
                stroke = SolidColor(Color(0xFF1E1E1E)),
            ) {
                moveTo(x = 8.20876f, y = 15.861f)
                verticalLineTo(y = 37.77f)
            }
            path(
                stroke = SolidColor(Color(0xFF1E1E1E)),
            ) {
                moveTo(x = 11.2763f, y = 15.861f)
                verticalLineTo(y = 37.77f)
            }
            path(
                stroke = SolidColor(Color(0xFF1E1E1E)),
            ) {
                moveTo(x = 25.0399f, y = 40.8998f)
                verticalLineTo(y = 8.6687f)
                curveTo(
                    x1 = 25.0399f,
                    y1 = 8.11642f,
                    x2 = 24.5921f,
                    y2 = 7.6687f,
                    x3 = 24.0399f,
                    y3 = 7.6687f
                )
                horizontalLineTo(x = 16.8374f)
                curveTo(
                    x1 = 16.2851f,
                    y1 = 7.6687f,
                    x2 = 15.8374f,
                    y2 = 8.11642f,
                    x3 = 15.8374f,
                    y3 = 8.6687f
                )
                verticalLineTo(y = 40.8998f)
            }
            path(
                stroke = SolidColor(Color(0xFF1E1E1E)),
            ) {
                moveTo(x = 20.4386f, y = 7.6687f)
                verticalLineTo(y = 0.0f)
            }
            path(
                stroke = SolidColor(Color(0xFF1E1E1E)),
            ) {
                moveTo(x = 20.4386f, y = 11.2475f)
                verticalLineTo(y = 38.3435f)
            }
        }.build().also { _mainTowerIcon = it }
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
                imageVector = MainTowerIcon,
                contentDescription = null,
                modifier = Modifier
                    .width((31.6748).dp)
                    .height((41.3998).dp),
            )
        }
    }
}

@Suppress("ObjectPropertyName")
private var _mainTowerIcon: ImageVector? = null
