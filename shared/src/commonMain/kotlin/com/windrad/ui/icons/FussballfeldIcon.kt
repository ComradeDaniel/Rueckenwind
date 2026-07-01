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

val FussballfeldIcon: ImageVector
    get() {
        val current = _fussballfeldIcon
        if (current != null) return current

        return ImageVector.Builder(
            name = "com.windrad.ui.theme.WindradTheme.FussballfeldIcon",
            defaultWidth = 46.0.dp,
            defaultHeight = 34.0.dp,
            viewportWidth = 46.0f,
            viewportHeight = 34.0f,
        ).apply {
            path(
                stroke = SolidColor(Color.Black),
                strokeLineWidth = 2.0f,
            ) {
                moveTo(x = 23.0f, y = 1.0f)
                horizontalLineTo(x = 3.0f)
                curveTo(x1 = 1.89543f, y1 = 1.0f, x2 = 1.0f, y2 = 1.89543f, x3 = 1.0f, y3 = 3.0f)
                verticalLineTo(y = 10.0f)
                moveTo(x = 23.0f, y = 1.0f)
                horizontalLineTo(x = 43.0f)
                curveTo(x1 = 44.1046f, y1 = 1.0f, x2 = 45.0f, y2 = 1.89543f, x3 = 45.0f, y3 = 3.0f)
                verticalLineTo(y = 10.0f)
                moveTo(x = 23.0f, y = 1.0f)
                verticalLineTo(y = 10.0f)
                moveTo(x = 23.0f, y = 33.0f)
                horizontalLineTo(x = 3.0f)
                curveTo(x1 = 1.89543f, y1 = 33.0f, x2 = 1.0f, y2 = 32.1046f, x3 = 1.0f, y3 = 31.0f)
                verticalLineTo(y = 24.0f)
                moveTo(x = 23.0f, y = 33.0f)
                horizontalLineTo(x = 43.0f)
                curveTo(
                    x1 = 44.1046f,
                    y1 = 33.0f,
                    x2 = 45.0f,
                    y2 = 32.1046f,
                    x3 = 45.0f,
                    y3 = 31.0f
                )
                verticalLineTo(y = 24.0f)
                moveTo(x = 23.0f, y = 33.0f)
                verticalLineTo(y = 24.0f)
                moveTo(x = 45.0f, y = 10.0f)
                horizontalLineTo(x = 40.0f)
                curveTo(
                    x1 = 38.8954f,
                    y1 = 10.0f,
                    x2 = 38.0f,
                    y2 = 10.8954f,
                    x3 = 38.0f,
                    y3 = 12.0f
                )
                verticalLineTo(y = 22.0f)
                curveTo(
                    x1 = 38.0f,
                    y1 = 23.1046f,
                    x2 = 38.8954f,
                    y2 = 24.0f,
                    x3 = 40.0f,
                    y3 = 24.0f
                )
                horizontalLineTo(x = 45.0f)
                moveTo(x = 45.0f, y = 10.0f)
                verticalLineTo(y = 24.0f)
                moveTo(x = 1.0f, y = 10.0f)
                horizontalLineTo(x = 6.0f)
                curveTo(x1 = 7.10457f, y1 = 10.0f, x2 = 8.0f, y2 = 10.8954f, x3 = 8.0f, y3 = 12.0f)
                verticalLineTo(y = 22.0f)
                curveTo(x1 = 8.0f, y1 = 23.1046f, x2 = 7.10457f, y2 = 24.0f, x3 = 6.0f, y3 = 24.0f)
                horizontalLineTo(x = 1.0f)
                moveTo(x = 1.0f, y = 10.0f)
                verticalLineTo(y = 24.0f)
                moveTo(x = 23.0f, y = 10.0f)
                curveTo(x1 = 23.0f, y1 = 10.0f, x2 = 16.0f, y2 = 10.0f, x3 = 16.0f, y3 = 17.0f)
                curveTo(x1 = 16.0f, y1 = 24.0f, x2 = 23.0f, y2 = 24.0f, x3 = 23.0f, y3 = 24.0f)
                moveTo(x = 23.0f, y = 10.0f)
                verticalLineTo(y = 24.0f)
                moveTo(x = 23.0f, y = 10.0f)
                curveTo(x1 = 23.0f, y1 = 10.0f, x2 = 30.0f, y2 = 10.0f, x3 = 30.0f, y3 = 17.0f)
                curveTo(x1 = 30.0f, y1 = 24.0f, x2 = 23.0f, y2 = 24.0f, x3 = 23.0f, y3 = 24.0f)
            }
        }.build().also { _fussballfeldIcon = it }
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
                imageVector = FussballfeldIcon,
                contentDescription = null,
                modifier = Modifier
                    .width((46.0).dp)
                    .height((34.0).dp),
            )
        }
    }
}

@Suppress("ObjectPropertyName")
private var _fussballfeldIcon: ImageVector? = null
